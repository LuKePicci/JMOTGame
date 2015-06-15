package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.List;
import java.util.Set;

/**
 * The Class Move.
 */
public class Move extends ActionController {

    /**
     * Instance of DrawCard action needed if human ends his movement on a
     * dangerous sector
     */
    protected DrawCard forcedDraw = new DrawCard();

    /** The target. */
    private Sector target;

    @Override
    public void initAction(MatchController matchController,
            ActionRequest request) {
        super.initAction(matchController, request);
        this.target = matchController.getZoneController().getCurrentZone()
                .getMap().get(request.getActionTarget());
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if the movement is valid
     */
    @Override
    public boolean isValid() {
        if (this.matchController.getTurnController().getTurn().getMustMove()) {
            int maxSteps = this.matchController.getTurnController().getTurn()
                    .getMaxSteps();
            Set<Sector> reachableSectors = this.matchController
                    .getZoneController()
                    .getCurrentZone()
                    .reachableTargets(
                            this.matchController.getZoneController()
                                    .getCurrentZone().getCell(player), maxSteps);
            if (reachableSectors.contains(this.target)) {
                // even if a sector is reachable I must be sure it is not human
                // start or alien start (or hatch)
                return ((!SectorType.ALIENS_START.equals(this.target.getType()))
                        && (!SectorType.HUMANS_START.equals(this.target
                                .getType())) && (PlayerRace.HUMAN
                        .equals(this.player.getIdentity().getRace()) || !SectorType.ESCAPE_HATCH
                        .equals(this.target.getType())));
                // humans can go to the hatches
            }
        }
        return false;
    }

    /**
     * Executes the action.
     * 
     * @throws DisconnectedException
     */
    @Override
    public void processAction() throws DisconnectedException {
        // move the player
        this.matchController.getZoneController().getCurrentZone()
                .movePlayer(player, this.target);
        // take note that the player has been moved
        this.matchController.getTurnController().getTurn().setMustMove();
        if (SectorType.ESCAPE_HATCH.equals(this.target.getType())) {
            this.notifyInChatByCurrentPlayer("I am in "
                    + this.target.getPoint().toString());
            HatchCard drawnCard = this.matchController.getMatch()
                    .getHatchesDeck().pickAndThrow();
            // send the card
            showCardToParty(drawnCard);
            if (HatchChance.FREE.equals(drawnCard.getChance())) {
                this.matchController.getMatch().getRescuedPlayer().add(player);
                this.notifyInChatByServer("GREEN HATCH CAR");
                this.notifyCurrentPlayerByServer("YOU ARE SAFE NOW");
                List<Player> others = obtainPartyPlayers();
                others.remove(this.matchController.getTurnController()
                        .getTurn().getCurrentPlayer());
                for (Player otherPlayer : others) {
                    this.notifyAPlayerAbout(otherPlayer, this.matchController
                            .getTurnController().getTurn().getCurrentPlayer()
                            .getName()
                            + " HAS ESCAPED");
                }
                this.matchController.checkEndGame();
            } else {
                this.notifyInChatByServer("RED HATCH CARD");
                this.notifyCurrentPlayerByServer("YOU CAN'T USE THIS HATCH");
                List<Player> others = obtainPartyPlayers();
                others.remove(this.matchController.getTurnController()
                        .getTurn().getCurrentPlayer());
                for (Player otherPlayer : others) {
                    this.notifyAPlayerAbout(otherPlayer, this.matchController
                            .getTurnController().getTurn().getCurrentPlayer()
                            .getName()
                            + " HAS NOT ESCAPED");
                }
                this.matchController.checkEndGame();
            }
            try {
                this.matchController.getZoneController().lockHatch(
                        this.target.getPoint());
            } catch (NotAnHatchException e) {
                // technically I have already checked the sector, so an
                // exception should never be thrown
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // update the map
            this.updateMapToPartyPlayers();
        } else if (SectorType.DANGEROUS.equals(this.target.getType())) {
            this.matchController.getTurnController().getTurn()
                    .setIsSecDangerous(true);// an alien can attack or draw a
                                             // card
            if (PlayerRace.HUMAN.equals(player.getIdentity().getRace())) {
                if (this.matchController.getTurnController().getTurn()
                        .getSilenceForced() == false) {
                    // a human must draw (except if he used a sedatives card)
                    ActionRequest forcedRequest = new ActionRequest(
                            ActionType.DRAW_CARD, null, null);
                    forcedDraw.initAction(this.matchController, forcedRequest);
                    forcedDraw.processAction();
                } else
                    this.matchController.getTurnController().getTurn()
                            .setIsSecDangerous(false);
            }
        }
        // the sector is secure (white), nothing have to be done
    }
}

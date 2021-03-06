package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorHighlight;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

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

    /**
     * Initializes the action.
     *
     * @param matchController
     *            the match controller
     * @param request
     *            the request
     */
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
                return !SectorType.ALIENS_START.equals(this.target.getType())
                        && !SectorType.HUMANS_START.equals(this.target
                                .getType())
                        && (PlayerRace.HUMAN.equals(this.player.getIdentity()
                                .getRace()) || !SectorType.ESCAPE_HATCH
                                .equals(this.target.getType()));
                // humans can go to the hatches
            }
        }
        return false;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        // move the player
        this.matchController.getZoneController().getCurrentZone()
                .movePlayer(player, this.target);
        // take note that the player has been moved
        this.matchController.getTurnController().getTurn().setMustMove(false);
        if (SectorType.ESCAPE_HATCH.equals(this.target.getType())) {
            this.notifyInChatByCurrentPlayer("I am in "
                    + this.getStringFromHexPoint(this.target.getPoint()));
            HatchCard drawnCard = this.matchController.getMatch()
                    .getHatchesDeck().pickAndThrow();
            // send the card
            this.matchController.showCardToParty(drawnCard);
            if (HatchChance.FREE.equals(drawnCard.getChance())) {
                this.matchController.getMatch().getRescuedPlayer().add(player);
                // player is removed from the map
                this.matchController.getZoneController().getCurrentZone()
                        .movePlayer(player, matchController.endingSector);
                this.notifyInChatByServer("GREEN HATCH CARD");
                try {
                    this.notifyCurrentPlayerByServer("YOU ARE SAFE NOW");
                } catch (DisconnectedException e) {
                    LoggerMethods
                            .disconnectedException(e,
                                    "the player will be told about his escape as soon as he reconnects");
                }
                this.notifyOtherPlayers(this.matchController
                        .getTurnController().getTurn().getCurrentPlayer()
                        .getName()
                        + " HAS ESCAPED");
                // remove player's cards
                this.matchController.cardsRemoval(player);
            } else {
                this.notifyInChatByServer("RED HATCH CARD");
                try {
                    this.notifyCurrentPlayerByServer("YOU CAN'T USE THIS HATCH");
                } catch (DisconnectedException e) {
                    LoggerMethods
                            .disconnectedException(
                                    e,
                                    "the player will be told about his failure attempt to escape as soon as he reconnects: he will be told that he is either alive or dead (because someone killed him while he was offline)");
                }
                this.notifyOtherPlayers(this.matchController
                        .getTurnController().getTurn().getCurrentPlayer()
                        .getName()
                        + " HAS NOT ESCAPED");

            }
            try {
                this.matchController.getZoneController().lockHatch(
                        this.target.getPoint());
            } catch (NotAnHatchException e) {
                LoggerMethods
                        .notAnHatchException(
                                e,
                                "Technically I have already checked the sector, so an exception should never be thrown");
            }
            // update the map
            this.sendMapVariationToParty(this.target,
                    SectorHighlight.HATCH_LOCKED);
            this.sendNewPosition();
            this.matchController.checkEndGame();
        } else if (SectorType.DANGEROUS.equals(this.target.getType())) {
            this.matchController.getTurnController().getTurn()
                    .setIsSecDangerous(true);// an alien can attack or draw a
                                             // card
            this.sendNewPosition();
            if (PlayerRace.HUMAN.equals(this.player.getIdentity().getRace())) {
                if (!this.matchController.getTurnController().getTurn()
                        .getSilenceForced()) {
                    // a human must draw (except if he used a sedatives card)
                    ActionRequest forcedRequest = new ActionRequest(
                            ActionType.DRAW_CARD, null, null);
                    forcedDraw.initAction(this.matchController, forcedRequest);
                    forcedDraw.processAction();
                } else
                    this.matchController.getTurnController().getTurn()
                            .setIsSecDangerous(false);
            }
        } else {
            // the sector is secure (white), nothing have to be done
            this.sendNewPosition();
        }
        if (PlayerRace.ALIEN.equals(this.player.getIdentity().getRace())) {
            this.matchController.getTurnController().getTurn()
                    .setCanAttack(true);
        }
        this.matchController.sendTurnViewModel();
    }

    /**
     * Sends to current player his new position.
     */
    private void sendNewPosition() {
        try {
            this.matchController.sendMapVariationToPlayer(this.player,
                    this.target, SectorHighlight.PLAYER_LOCATION);
            this.notifyCurrentPlayerByServer("You are in "
                    + this.getStringFromSector(this.target));
        } catch (DisconnectedException e) {
            LoggerMethods
                    .disconnectedException(
                            e,
                            "player's location will be updated as soon as the player comes back thanks to modelSender(Player returningPlayer) in MatchController");
        }
    }

}

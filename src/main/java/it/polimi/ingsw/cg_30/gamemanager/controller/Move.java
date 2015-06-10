package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;

import java.util.List;
import java.util.Set;

/**
 * The Class Move.
 */
public class Move extends ActionController {

    /** The target. */
    private Sector target;

    @Override
    public void initAction(MatchController matchController,
            ActionRequest request) {
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
        // TODO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        if (matchController.getTurnController().getTurn().getMustMove()) {
            int maxSteps = matchController.getTurnController().getTurn()
                    .getMaxSteps();
            Set<Sector> reachableSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(target, maxSteps);
            if (reachableSectors.contains(target)) {
                // anche se un settore è raggiungibile devo assicurarmi che non
                // sia (scialuppa/)settore umani/settore alieni
                // TODO è possibile che i seguenti test non siano necessari a
                // seconda che reachableTargets ritorni o meno i settori start e
                // sciluppa
                return ((!SectorType.ALIENS_START.equals(target.getType()))
                        && (!SectorType.HUMANS_START.equals(target.getType())) && (PlayerRace.HUMAN
                        .equals(player.getIdentity()) || !SectorType.ESCAPE_HATCH
                        .equals(target.getType())));
                // gli umani posso andare sulle scialuppe
            }
        }
        return false;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        // sposto il giocatore
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player, target);
        // segno che il giocatore ha effettuato uno spostamento
        matchController.getTurnController().getTurn().setMustMove();
        if (SectorType.ESCAPE_HATCH.equals(target.getType())) {
            HatchCard drawnCard = matchController.getMatch().getHatchesDeck()
                    .pickAndThrow();
            if (HatchChance.FREE.equals(drawnCard.getChance())) {
                matchController.getMatch().getRescuedPlayer().add(player);
                matchController.getPartyController().sendMessageToParty(
                        new ChatMessage(new ChatViewModel("GREEN HATCH CARD",
                                "Server", ChatVisibility.PARTY)));
                // TODO eventuale invio del viewModel della carta pescata
                MessageController
                        .getPlayerHandler(
                                matchController
                                        .getPartyController()
                                        .getCurrentParty()
                                        .getPlayerUUID(
                                                matchController
                                                        .getTurnController()
                                                        .getTurn()
                                                        .getCurrentPlayer()))
                        .getAcceptPlayer()
                        .sendMessage(
                                new ChatMessage(new ChatViewModel(
                                        "YOU ARE SAVED NOW", "Server",
                                        ChatVisibility.PLAYER)));
                List<Player> others = obtainPartyPlayers();
                others.remove(matchController.getTurnController().getTurn()
                        .getCurrentPlayer());
                for (Player otherPlayer : others) {
                    MessageController
                            .getPlayerHandler(
                                    matchController.getPartyController()
                                            .getCurrentParty()
                                            .getPlayerUUID(otherPlayer))
                            .getAcceptPlayer()
                            .sendMessage(
                                    new ChatMessage(new ChatViewModel(
                                            matchController.getTurnController()
                                                    .getTurn()
                                                    .getCurrentPlayer()
                                                    + " HAS ESCAPED", "Server",
                                            ChatVisibility.PLAYER)));
                }
                matchController.checkEndGame();
            } else {
                matchController.getPartyController().sendMessageToParty(
                        new ChatMessage(new ChatViewModel("RED HATCH CARD",
                                "Server", ChatVisibility.PARTY)));
                // TODO eventuale invio del viewModel della carta pescata
                MessageController
                        .getPlayerHandler(
                                matchController
                                        .getPartyController()
                                        .getCurrentParty()
                                        .getPlayerUUID(
                                                matchController
                                                        .getTurnController()
                                                        .getTurn()
                                                        .getCurrentPlayer()))
                        .getAcceptPlayer()
                        .sendMessage(
                                new ChatMessage(new ChatViewModel(
                                        "YOU CAN'T USE THIS HATCH", "Server",
                                        ChatVisibility.PLAYER)));
                List<Player> others = obtainPartyPlayers();
                others.remove(matchController.getTurnController().getTurn()
                        .getCurrentPlayer());
                for (Player otherPlayer : others) {
                    MessageController
                            .getPlayerHandler(
                                    matchController.getPartyController()
                                            .getCurrentParty()
                                            .getPlayerUUID(otherPlayer))
                            .getAcceptPlayer()
                            .sendMessage(
                                    new ChatMessage(new ChatViewModel(
                                            matchController.getTurnController()
                                                    .getTurn()
                                                    .getCurrentPlayer()
                                                    + " HAS NOT ESCAPED",
                                            "Server", ChatVisibility.PLAYER)));
                }
                matchController.checkEndGame();
            }
            try {
                matchController.getZoneController()
                        .lockHatch(target.getPoint());
            } catch (NotAnHatchException e) {
                // in teoria ho già verificato il tipo di settore e quindi non
                // dovrebbe mai verificarsi un'eccezione
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // aggiorno la mappa
            matchController.getPartyController().sendMessageToParty(
                    new Message(matchController.getZoneController()
                            .getCurrentZone().getViewModel()));
        } else if (SectorType.DANGEROUS.equals(target.getType())) {
            matchController.getTurnController().getTurn()
                    .setIsSecDangerous(true);// l'alieno dovrà o pescare o
                                             // attaccare
            if ((PlayerRace.HUMAN.equals(player.getIdentity().getRace()))
                    && (matchController.getTurnController().getTurn()
                            .getSilenceForced() == false)) {
                // l'umano deve pescare (salvo uso di sedativi)
                DrawCard forcedDraw = new DrawCard(matchController);
                forcedDraw.processAction();
            }
        } else
            // settore non pericoloso (bianco), non faccio nulla
            ;
    }
}

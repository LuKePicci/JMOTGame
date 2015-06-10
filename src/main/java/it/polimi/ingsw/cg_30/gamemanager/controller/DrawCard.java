package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;

/**
 * The Class DrawCard.
 */
public class DrawCard extends ActionController {

    // costruttore usato da Move
    public DrawCard(MatchController matchController) {
        this.matchController = matchController;
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if the player can draw a card
     */
    @Override
    public boolean isValid() {// funzione utile solo per l'alieno
        return ((SectorType.DANGEROUS
                .equals(matchController.getZoneController().getCurrentZone()
                        .getCell(player).getType())) && (matchController
                .getTurnController().getTurn().getIsSecDangerous()));
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        {
            SectorCard drawnCard = matchController.getMatch().getSectorsDeck()
                    .pickAndThrow();
            matchController.getTurnController().getTurn()
                    .setIsSecDangerous(false);
            matchController.getTurnController().getTurn().setCanAttack(false);
            if (SectorEvent.SILENCE.equals(drawnCard.getEvent())) {
                // TODO eventuale invio del viewModel della carta pescata
                matchController.getPartyController().sendMessageToParty(
                        new ChatMessage(new ChatViewModel("SILENCE",
                                matchController.getTurnController().getTurn()
                                        .getCurrentPlayer().getName(),
                                ChatVisibility.PARTY)));
            } else {
                if (SectorEvent.NOISE_YOUR.equals(drawnCard.getEvent())) {
                    // TODO eventuale invio del viewModel della carta pescata
                    matchController
                            .getPartyController()
                            .sendMessageToParty(
                                    new ChatMessage(
                                            new ChatViewModel(
                                                    "NOISE in sector "
                                                            + matchController
                                                                    .getZoneController()
                                                                    .getCurrentZone()
                                                                    .getCell(
                                                                            player)
                                                                    .toString(),
                                                    matchController
                                                            .getTurnController()
                                                            .getTurn()
                                                            .getCurrentPlayer()
                                                            .getName(),
                                                    ChatVisibility.PARTY)));
                    hasObject(drawnCard);
                } else if (SectorEvent.NOISE_ANY.equals(drawnCard.getEvent())) {
                    // salvo in turno la carta pescata in modo da portela avere
                    // anche nell'action NoiseAny
                    matchController.getTurnController().getTurn()
                            .setDrawnCard(drawnCard);
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
                                            "CHOOSE WHERE TO MAKE THE NOISE",
                                            "Server", ChatVisibility.PLAYER)));
                }
            }
        }
    }

}

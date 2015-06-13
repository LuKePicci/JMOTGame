package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

/**
 * The Class DrawCard.
 */
public class DrawCard extends ActionController {

    /**
     * Checks the legality of this action.
     * 
     * @return true if the player can draw a card
     */
    @Override
    public boolean isValid() {// funzione utile solo per l'alieno
        return SectorType.DANGEROUS.equals(matchController.getZoneController()
                .getCurrentZone().getCell(player).getType())
                && matchController.getTurnController().getTurn()
                        .getIsSecDangerous();
    }

    /**
     * Executes the action.
     * 
     * @throws DisconnectedException
     */
    @Override
    public void processAction() throws DisconnectedException {
        SectorCard drawnCard = matchController.getMatch().getSectorsDeck()
                .pickAndThrow();
        matchController.getTurnController().getTurn().setIsSecDangerous(false);
        matchController.getTurnController().getTurn().setCanAttack(false);
        showCardToCurrentPlayer(drawnCard);
        if (SectorEvent.SILENCE.equals(drawnCard.getEvent())) {
            notifyInChatByCurrentPlayer("SILENCE");
        } else if (SectorEvent.NOISE_YOUR.equals(drawnCard.getEvent())) {
            notifyInChatByCurrentPlayer("NOISE in sector "
                    + matchController
                            .getZoneController()
                            .getCurrentZone()
                            .getCell(
                                    matchController.getTurnController()
                                            .getTurn().getCurrentPlayer())
                            .toString());
            hasObject(drawnCard);
        } else {// caso NoiseAny
                // salvo in turno la carta pescata in modo da portela avere
                // anche nell'action NoiseAny
            matchController.getTurnController().getTurn()
                    .setDrawnCard(drawnCard);
            notifyCurrentPlayerByServer("CHOOSE WHERE TO MAKE THE NOISE");
        }
    }

}

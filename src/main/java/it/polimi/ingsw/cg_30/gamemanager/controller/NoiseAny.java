package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;

/**
 * The Class NoiseAny.
 */
public class NoiseAny extends ActionController {

    /**
     * Checks the legality of this action.
     * 
     * @return true if the sector target is in the map
     */
    @Override
    public boolean isValid() {
        return (matchController.getZoneController().getCurrentZone().getMap()
                .containsKey(req.getActionTarget()) && (matchController
                .getTurnController().getTurn().getDrawnCard() != null));
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getMap().get(this.req.getActionTarget());
        matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel("NOISE in sector "
                        + sec.toString(), matchController.getTurnController()
                        .getTurn().getCurrentPlayer().getName(),
                        ChatVisibility.PARTY)));
        hasObject(matchController.getTurnController().getTurn().getDrawnCard());
        // dopo aver usato la carta la rimuovo da turno così da sbloccare
        // UseCard e TurnOver
        matchController.getTurnController().getTurn().setDrawnCard(null);
    }
}

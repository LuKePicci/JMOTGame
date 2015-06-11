package it.polimi.ingsw.cg_30.gamemanager.controller;

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
        notifyInChatByCurrentPlayer("NOISE in sector " + sec.toString());
        hasObject(matchController.getTurnController().getTurn().getDrawnCard());
        // dopo aver usato la carta la rimuovo da turno così da sbloccare
        // UseCard e TurnOver
        matchController.getTurnController().getTurn().setDrawnCard(null);
    }
}

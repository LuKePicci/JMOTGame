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
        return this.matchController.getTurnController().getTurn()
                .getDrawnCard() != null
                && this.matchController.getZoneController().getCurrentZone()
                        .getMap().containsKey(this.req.getActionTarget());
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        Sector sec = this.matchController.getZoneController().getCurrentZone()
                .getMap().get(this.req.getActionTarget());
        this.notifyInChatByCurrentPlayer("NOISE in sector " + sec.toString());
        this.hasObject(this.matchController.getTurnController().getTurn()
                .getDrawnCard());
        // after using it, the card have to be removed, so UseCard and TurnOver
        // will be unlocked
        this.matchController.getTurnController().getTurn().setDrawnCard(null);
    }
}

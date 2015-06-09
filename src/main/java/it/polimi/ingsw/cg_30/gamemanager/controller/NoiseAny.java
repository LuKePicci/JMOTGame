package it.polimi.ingsw.cg_30.gamemanager.controller;

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
                .containsKey(req.getActionTarget()));
        // TODO controllare che dranbwcard sia !=null
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        // TODO notifica il rumore nel settore target
        hasObject(matchController.getTurnController().getTurn().getDrawnCard());
        // dopo aver usato la carta la rimuovo da turno così da sbloccare
        // UseCard e TurnOver
        matchController.getTurnController().getTurn().setDrawnCard(null);
    }
}

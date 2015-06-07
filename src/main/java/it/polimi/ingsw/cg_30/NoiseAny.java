package it.polimi.ingsw.cg_30;

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
        if (matchController.getZoneController().getCurrentZone().getMap()
                .containsKey(req.getActionTarget())) {
            return true;
        } else
            return false;
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

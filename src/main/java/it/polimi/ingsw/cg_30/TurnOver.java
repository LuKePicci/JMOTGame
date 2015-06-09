package it.polimi.ingsw.cg_30;

/**
 * The Class TurnOver.
 */
public class TurnOver extends ActionController {

    /**
     * Checks the legality of this action.
     * 
     * @return true if the turn can end
     */
    @Override
    public boolean isValid() {
        // player deve essersi mosso, non deve avere 4 carte in mano, deve aver
        // risolto gli effetti legati al settore pericoloso (rumore ed eventuale
        // pesca della carta oggetto)
        return (!(matchController.getTurnController().getTurn().getMustMove())
                && !(matchController.getTurnController().getTurn()
                        .getMustDiscard())
                && !(matchController.getTurnController().getTurn()
                        .getIsSecDangerous()) && (matchController
                .getTurnController().getTurn().getDrawnCard() == null));
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        matchController.getTurnController().nextTurn(matchController);
    }
}

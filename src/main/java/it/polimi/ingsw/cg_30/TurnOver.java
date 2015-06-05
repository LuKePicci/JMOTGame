package it.polimi.ingsw.cg_30;

/**
 * The Class TurnOver.
 */
public class TurnOver extends ActionController {

    /** The match controller. */
    private MatchController matchController;

    /**
     * Instantiates a new turn over action.
     *
     * @param matchController
     *            the match controller
     */
    public TurnOver(MatchController matchController) {
        this.matchController = matchController;
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if the turn can end
     */
    @Override
    public boolean isValid() {
        // player deve essersi mosso, non deve avere 4 carte in mano, deve aver
        // risolto gli effetti legati al settore pericoloso
        if ((matchController.getTurnController().getTurn().getMustMove() == true)
                || (matchController.getTurnController().getTurn()
                        .getMustDiscard() == true)
                || (matchController.getTurnController().getTurn()
                        .getIsSecDangerous() == true)) {
            return false;
        } else
            return true;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        matchController.getTurnController().nextTurn(matchController);
    }
}

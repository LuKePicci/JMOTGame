package it.polimi.ingsw.cg_30.gamemanager.controller;

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
        // player must have been moved, player must not have four cards in his
        // hand, dangerous sector effects must have been solved (noise and, if
        // necessary, drawing an item card)
        return !this.matchController.getTurnController().getTurn()
                .getMustMove()
                && !this.matchController.getTurnController().getTurn()
                        .getMustDiscard()
                && !this.matchController.getTurnController().getTurn()
                        .getIsSecDangerous()
                && this.matchController.getTurnController().getTurn()
                        .getDrawnCard() == null;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        this.matchController.getTurnController().nextTurn();
    }
}

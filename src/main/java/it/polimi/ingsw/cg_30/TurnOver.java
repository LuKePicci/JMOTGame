package it.polimi.ingsw.cg_30;

public class TurnOver extends ActionController {

    private MatchController matchController;

    public TurnOver(MatchController matchController) {

    }

    @Override
    public boolean isValid() {
        // player deve essersi mosso
        if (matchController.getCurrentTurn().getTurn().getMustMove() == true) {
            return false;
        }
        // player non deve avere 4 carte in mano
        if (matchController.getCurrentTurn().getTurn().getMustDiscard() == true) {
            return false;
        }
        return true;
    }

    @Override
    public void processAction() {

    }

}

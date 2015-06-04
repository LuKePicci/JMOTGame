package it.polimi.ingsw.cg_30;

public class TurnOver extends ActionController {

    private MatchController matchController;

    public TurnOver(MatchController matchController) {

    }

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

    @Override
    public void processAction() {
        matchController.getTurnController().nextTurn(matchController);
    }
}

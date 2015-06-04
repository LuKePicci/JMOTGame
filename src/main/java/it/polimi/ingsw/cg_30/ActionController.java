package it.polimi.ingsw.cg_30;

public abstract class ActionController {

    protected MatchController currentMatch;

    protected ActionRequest req;

    public void initAction(MatchController match, ActionRequest request) {
        this.req = request;
        this.currentMatch = match;
    }

    public static ActionController getStrategy(ActionRequest request)
            throws InstantiationException, IllegalAccessException {

        return request.getActionType().getController();
    }

    public abstract boolean isValid();

    public abstract ActionMessage processAction();

}

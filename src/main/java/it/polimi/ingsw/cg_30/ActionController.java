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

    /*
     * LEGGIMI NB: l'alieno che attacca non deve pescare la carta settore,
     * quindi in caso di attacco l'rodine delle operazioni da eseguire è:
     * isValid di move; settare l'attributo silencedForse di turn a true;
     * processAction di move; isValid di Attack(inutile in teoria);
     * processAction di Attack.
     */

    public abstract boolean isValid();

    public abstract ActionMessage processAction();

}

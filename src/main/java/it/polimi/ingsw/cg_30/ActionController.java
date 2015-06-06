package it.polimi.ingsw.cg_30;

public abstract class ActionController {

    protected MatchController matchController;
    protected ActionRequest req;
    protected Player player;

    public void initAction(MatchController match, ActionRequest request) {
        this.req = request;
        this.matchController = match;
        this.player = match.getTurnController().getTurn().getCurrentPlayer();
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

    public abstract void processAction();

    // cerco tra le carte in mano a player se c'è quella del tipo richiesto
    // la ritorno se c'è; ritorno null altrimenti
    protected ItemCard findItemCardByItem(Item item) {
        for (Card card : matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck().getCards()) {
            ItemCard icard = (ItemCard) card;
            if (item.equals(icard.getItem())) {
                return icard;
            }
        }
        return null;
    }

}

package it.polimi.ingsw.cg_30;

/**
 * The Class DiscardCard.
 */
public class DiscardCard extends ActionController {

    private SpareDeck<ItemCard> spareDeck;
    private Item item;

    @Override
    public void initAction(MatchController matchController,
            ActionRequest request) {
        super.initAction(matchController, request);
        this.spareDeck = matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck();
        this.item = request.getActionItem();
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if player can discard the card
     */
    @Override
    public boolean isValid() {// ipotizzo che si possa scartare una carta solo
                              // se si hanno più di 3 carte in mano
        if ((matchController.getTurnController().getTurn().getMustDiscard() == true)
                && (findItemCardByItem(item) != null)) {
            return true;
        }
        return false;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        ItemCard card = findItemCardByItem(item);
        // scarto la carta oggetto
        matchController.getMatch().getItemsDeck().putIntoBucket(card);
        spareDeck.getCards().remove(card);
        // elimino l'obbligo di scartare
        matchController.getTurnController().getTurn().setMustDiscard(false);

    }

}

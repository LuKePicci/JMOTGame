package it.polimi.ingsw.cg_30;

public class DiscardCard extends ActionController {

    private MatchController matchController;
    private SpareDeck spareDeck;
    private Player player;
    private ItemCard card;

    public DiscardCard(MatchController matchController, ItemCard card) {
        this.matchController = matchController;
        this.card = card;
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
        this.spareDeck = matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck();
    }

    @Override
    public boolean isValid() {// ipotizzo che si possa scartare una carta solo
                              // se si hanno più di 3 carte in mano
        if (matchController.getTurnController().getTurn().getMustDiscard()) {
            return true;
        }
        return false;
    }

    @Override
    public void processAction() {
        // scarto la carta oggetto
        matchController.getMatch().getItemsDeck().putIntoBucket(card);
        spareDeck.getCards().remove(card);
        // elimino l'obbligo di scartare
        matchController.getTurnController().getTurn().setMustDiscard(false);

    }

}

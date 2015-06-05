package it.polimi.ingsw.cg_30;

/**
 * The Class DiscardCard.
 */
public class DiscardCard extends ActionController {

    /** The match controller. */
    private MatchController matchController;

    /** The spare deck. */
    private SpareDeck spareDeck;

    /** The player. */
    private Player player;

    /** The card. */
    private ItemCard card;

    /**
     * Instantiates a new discard card action.
     *
     * @param matchController
     *            the match controller
     * @param card
     *            the card the player is going to discard
     */
    public DiscardCard(MatchController matchController, ItemCard card) {
        this.matchController = matchController;
        this.card = card;
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
        this.spareDeck = matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck();
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if player can discard the card
     */
    @Override
    public boolean isValid() {// ipotizzo che si possa scartare una carta solo
                              // se si hanno più di 3 carte in mano
        if (matchController.getTurnController().getTurn().getMustDiscard()) {
            return true;
        }
        return false;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        // scarto la carta oggetto
        matchController.getMatch().getItemsDeck().putIntoBucket(card);
        spareDeck.getCards().remove(card);
        // elimino l'obbligo di scartare
        matchController.getTurnController().getTurn().setMustDiscard(false);

    }

}

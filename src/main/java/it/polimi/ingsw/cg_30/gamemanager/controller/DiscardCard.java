package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.gamemanager.model.SpareDeck;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

/**
 * The Class DiscardCard.
 */
public class DiscardCard extends ActionController {

    /** The deck of item card of current player. */
    private SpareDeck<ItemCard> spareDeck;

    /** The item to discard. */
    private Item item;

    /**
     * Initializes the action.
     *
     * @param matchController
     *            the match controller
     * @param request
     *            the request
     */
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
    public boolean isValid() {
        // hypothesis: a player can discard a card only if he possesses more
        // than three cards
        return this.matchController.getTurnController().getTurn()
                .getMustDiscard()
                && this.findItemCardByItem(this.item) != null;
    }

    /**
     * Executes the action.
     *
     * @throws DisconnectedException
     *             the disconnected exception
     */
    @Override
    public void processAction() throws DisconnectedException {
        ItemCard card = findItemCardByItem(this.item);
        // discard the card
        this.matchController.getMatch().getItemsDeck().putIntoBucket(card);
        this.spareDeck.getCards().remove(card);
        // remove the obliged to discard
        this.matchController.getTurnController().getTurn()
                .setMustDiscard(false);
        this.notifyInChatByCurrentPlayer("CARD DISCARDED");
        this.updateDeckView();
    }

}

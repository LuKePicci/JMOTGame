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

    /** The spare deck. */
    private SpareDeck<ItemCard> spareDeck;

    /** The item. */
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
    public boolean isValid() {// ipotizzo che si possa scartare una carta solo
                              // se si hanno più di 3 carte in mano
        return matchController.getTurnController().getTurn().getMustDiscard()
                && findItemCardByItem(item) != null;
    }

    /**
     * Executes the action.
     *
     * @throws DisconnectedException
     *             the disconnected exception
     */
    @Override
    public void processAction() throws DisconnectedException {
        ItemCard card = findItemCardByItem(item);
        // scarto la carta oggetto
        matchController.getMatch().getItemsDeck().putIntoBucket(card);
        spareDeck.getCards().remove(card);
        // elimino l'obbligo di scartare
        matchController.getTurnController().getTurn().setMustDiscard(false);
        notifyInChatByCurrentPlayer("CARD DISCARDED");
        updateDeckView();
    }

}

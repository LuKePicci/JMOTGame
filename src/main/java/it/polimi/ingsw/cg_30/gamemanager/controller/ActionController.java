package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;

import java.util.EmptyStackException;
import java.util.List;

/**
 * The Class ActionController.
 */
public abstract class ActionController {

    /** The match controller. */
    protected MatchController matchController;

    /** The req. */
    protected ActionRequest req;

    /** The player. */
    protected Player player;

    /**
     * Initializes the action.
     *
     * @param match
     *            the match
     * @param request
     *            the request
     */
    public void initAction(MatchController match, ActionRequest request) {
        this.req = request;
        this.matchController = match;
        this.player = match.getTurnController().getTurn().getCurrentPlayer();
    }

    /**
     * Gets the strategy.
     *
     * @param request
     *            the request
     * @return the strategy
     * @throws InstantiationException
     *             the instantiation exception
     * @throws IllegalAccessException
     *             the illegal access exception
     */
    public static ActionController getStrategy(ActionRequest request)
            throws InstantiationException, IllegalAccessException {

        return request.getActionType().getController();
    }

    /**
     * Checks if is valid.
     *
     * @return true, if is valid
     */
    public abstract boolean isValid();

    /**
     * Process action.
     */
    public abstract void processAction();

    /**
     * Obtain party players list.
     *
     * @return the list of players of the current party
     */
    public List<Player> obtainPartyPlayers() {
        return matchController.obtainPartyPlayers();
    }

    // cerco tra le carte in mano a player se c'è quella del tipo richiesto
    // la ritorno se c'è; ritorno null altrimenti
    /**
     * Find item card by item.
     *
     * @param item
     *            the item
     * @return the item card
     */
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

    /**
     * Checks for object symbol on the sector card.
     *
     * @param drawnCard
     *            the sector card to check
     */
    protected void hasObject(SectorCard drawnCard) {
        if (drawnCard.hasObjectSymbol()) {
            ItemCard icard;
            // il mazzo item è l'unico che potrebbe terminare le carte
            try {
                icard = (ItemCard) matchController.getMatch().getItemsDeck()
                        .pickCard();
            } catch (EmptyStackException e) {
                // informa il giocatore che non ci son più carte oggetto
                notifyCurrentPlayerByServer("NO ITEM CARDS AVAILABLE");
                return;
            }
            player.getItemsDeck().getCards().add(icard);
            // notifica il giocatore sulla carta pescata
            notifyCurrentPlayerByServer("You have just picked a "
                    + icard.getItem().toString() + " card");
            showCardToCurrentPlayer(icard);
            updateCardsView();
            if (player.getItemsDeck().getCards().size() > 3) {
                matchController.getTurnController().getTurn()
                        .setMustDiscard(true);
                notifyCurrentPlayerByServer("You must use or discard at least one card in this turn");
            }
        }
    }

    // NOTIFICATION METHODS

    /**
     * Notifies the string received to the party chat using the current player
     * as sender.
     *
     * @param what
     *            the string to notify
     */
    protected void notifyInChatByCurrentPlayer(String what) {
        matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel(what, matchController
                        .getTurnController().getTurn().getCurrentPlayer()
                        .getName(), ChatVisibility.PARTY)));
    }

    /**
     * Notifies the string received to the party chat using server as sender.
     *
     * @param what
     *            the string to notify
     */
    protected void notifyInChatByServer(String what) {
        matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel(what, "Server",
                        ChatVisibility.PARTY)));
    }

    /**
     * Notifies the string received to the current player chat using server as
     * sender.
     *
     * @param what
     *            the string to notify
     */
    protected void notifyCurrentPlayerByServer(String what) {
        MessageController
                .getPlayerHandler(
                        matchController
                                .getPartyController()
                                .getCurrentParty()
                                .getPlayerUUID(
                                        matchController.getTurnController()
                                                .getTurn().getCurrentPlayer()))
                .getAcceptPlayer()
                .sendMessage(
                        new ChatMessage(new ChatViewModel(what, "Server",
                                ChatVisibility.PLAYER)));
    }

    /**
     * Notifies the string received to the player received using server as
     * sender.
     *
     * @param about
     *            the string to notify
     */
    protected void notifyAPlayerAbout(Player player, String about) {
        MessageController
                .getPlayerHandler(
                        matchController.getPartyController().getCurrentParty()
                                .getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(
                        new ChatMessage(new ChatViewModel(about, "Server",
                                ChatVisibility.PLAYER)));
    }

    /**
     * Shows the card received to the party.
     *
     * @param card
     *            the card to notify
     */
    protected void showCardToParty(Card card) {
        matchController.getPartyController().sendMessageToParty(
                new Message(card));
    }

    /**
     * Shows the card received to the current player.
     *
     * @param card
     *            the card to notify
     */
    protected void showCardToCurrentPlayer(Card card) {
        MessageController
                .getPlayerHandler(
                        matchController
                                .getPartyController()
                                .getCurrentParty()
                                .getPlayerUUID(
                                        matchController.getTurnController()
                                                .getTurn().getCurrentPlayer()))
                .getAcceptPlayer().sendMessage(new Message(card));
    }

    /**
     * Updates cards view for the current player.
     */
    protected void updateCardsView() {
        MessageController
                .getPlayerHandler(
                        matchController
                                .getPartyController()
                                .getCurrentParty()
                                .getPlayerUUID(
                                        matchController.getTurnController()
                                                .getTurn().getCurrentPlayer()))
                .getAcceptPlayer()
                .sendMessage(
                        new Message(matchController.getTurnController()
                                .getTurn().getCurrentPlayer().getItemsDeck()
                                .getViewModel()));
    }

    /**
     * Updates map view for the current player.
     */
    protected void updateMapView() {
        MessageController
                .getPlayerHandler(
                        matchController
                                .getPartyController()
                                .getCurrentParty()
                                .getPlayerUUID(
                                        matchController.getTurnController()
                                                .getTurn().getCurrentPlayer()))
                .getAcceptPlayer()
                .sendMessage(
                        new Message(matchController.getZoneController()
                                .getCurrentZone().getViewModel()));
    }

}

package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ZoneViewModel;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ActionController.
 */
public abstract class ActionController {

    private static Map<ActionType, Class> strategies = new HashMap<ActionType, Class>();

    /** The match controller. */
    protected MatchController matchController;

    /** The request */
    protected ActionRequest req;

    /** The current player. */
    protected Player player;

    /** The server word text. */
    private String serverWordText = "Server";

    static {
        strategies.put(ActionType.ATTACK, Attack.class);
        strategies.put(ActionType.DISCARD_CARD, DiscardCard.class);
        strategies.put(ActionType.MOVE, Move.class);
        strategies.put(ActionType.NOISE_ANY, NoiseAny.class);
        strategies.put(ActionType.TURN_OVER, TurnOver.class);
        strategies.put(ActionType.USE_ITEM, UseCard.class);
        strategies.put(ActionType.DRAW_CARD, DrawCard.class);
    }

    /**
     * Initializes the action.
     *
     * @param match
     *            the match controller
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

        return (ActionController) strategies.get(request.getActionType())
                .newInstance();
    }

    /**
     * Checks if is valid.
     *
     * @return true, if is valid
     */
    public abstract boolean isValid();

    /**
     * Processes action.
     * 
     * @throws DisconnectedException
     */
    public abstract void processAction() throws DisconnectedException;

    /**
     * Obtains party players list.
     *
     * @return the list of players of the current party
     */
    public List<Player> obtainPartyPlayers() {
        return this.matchController.obtainPartyPlayers();
    }

    /**
     * Searches among player's cards an item card whose item is "item"; if it
     * can't find one, return null.
     *
     * @param item
     *            the item
     * @return the item card
     */
    protected ItemCard findItemCardByItem(Item item) {
        for (Card card : this.matchController.getTurnController().getTurn()
                .getCurrentPlayer().getItemsDeck().getCards()) {
            ItemCard icard = (ItemCard) card;
            if (item.equals(icard.getItem())) {
                return icard;
            }
        }
        return null;
    }

    /**
     * Checks for object symbol on the sector card; if there is an object this
     * method processes it.
     *
     * @param drawnCard
     *            the sector card to check
     * @throws DisconnectedException
     */
    protected void hasObject(SectorCard drawnCard) throws DisconnectedException {
        if (drawnCard.hasObjectSymbol()) {
            ItemCard icard;
            // only the itemDeck could end its cards
            try {
                icard = this.matchController.getMatch().getItemsDeck()
                        .pickCard();
            } catch (EmptyStackException e) {
                // informs the player that there are not item card available
                this.notifyCurrentPlayerByServer("No more cards in the item deck.");
                return;
            }
            this.player.getItemsDeck().getCards().add(icard);
            // notifies the player about the drawn card
            this.notifyCurrentPlayerByServer("You have just picked a "
                    + icard.getItem().toString() + " card");
            this.showCardToCurrentPlayer(icard);
            this.updateDeckView();
            if (this.player.getItemsDeck().getCards().size() > 3) {
                this.matchController.getTurnController().getTurn()
                        .setMustDiscard(true);
                this.notifyCurrentPlayerByServer("You must use or discard at least one card in this turn");
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
        this.matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel(what, this.matchController
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
        this.matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel(what, serverWordText,
                        ChatVisibility.PARTY)));
    }

    /**
     * Notifies the string received to the current player chat using server as
     * sender.
     *
     * @param what
     *            the string to notify
     * @throws DisconnectedException
     */
    protected void notifyCurrentPlayerByServer(String what)
            throws DisconnectedException {
        MessageController
                .getPlayerHandler(
                        this.matchController.getPartyController()
                                .getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(
                        new ChatMessage(new ChatViewModel(what, serverWordText,
                                ChatVisibility.PLAYER)));
    }

    /**
     * Notifies the string received to the player received using server as
     * sender.
     *
     * @param about
     *            the string to notify
     * @throws DisconnectedException
     */
    protected void notifyAPlayerAbout(Player player, String about)
            throws DisconnectedException {
        MessageController
                .getPlayerHandler(
                        this.matchController.getPartyController()
                                .getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(
                        new ChatMessage(new ChatViewModel(about,
                                serverWordText, ChatVisibility.PLAYER)));
    }

    /**
     * Shows the card received to the party.
     *
     * @param card
     *            the card to notify
     */
    protected void showCardToParty(Card card) {
        this.matchController.getPartyController().sendMessageToParty(
                new Message(card));
    }

    /**
     * Shows the card received to the current player.
     *
     * @param card
     *            the card to notify
     * @throws DisconnectedException
     */
    protected void showCardToCurrentPlayer(Card card)
            throws DisconnectedException {
        MessageController
                .getPlayerHandler(
                        this.matchController.getPartyController()
                                .getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer().sendMessage(new Message(card));
    }

    /**
     * Updates deck view for the current player.
     * 
     * @throws DisconnectedException
     */
    protected void updateDeckView() throws DisconnectedException {
        MessageController
                .getPlayerHandler(
                        this.matchController.getPartyController()
                                .getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(new Message(player.getItemsDeck().getViewModel()));
    }

    /**
     * Updates map view for the current player.
     * 
     * @throws DisconnectedException
     */
    protected void updateMapView() throws DisconnectedException {
        ZoneViewModel viewModel = (ZoneViewModel) this.matchController
                .getZoneController().getCurrentZone().getViewModel();
        viewModel.setPlayerLocation(this.matchController.getZoneController()
                .getCurrentZone().getCell(this.player));
        MessageController
                .getPlayerHandler(
                        this.matchController.getPartyController()
                                .getCurrentParty().getPlayerUUID(this.player))
                .getAcceptPlayer().sendMessage(new Message(viewModel));
    }

    /**
     * Updates map view for all party players.
     * 
     * @throws DisconnectedException
     */
    protected void updateMapToPartyPlayers() throws DisconnectedException {
        for (Player playerToNotify : obtainPartyPlayers()) {
            ZoneViewModel viewModel = (ZoneViewModel) this.matchController
                    .getZoneController().getCurrentZone().getViewModel();
            viewModel.setPlayerLocation(this.matchController
                    .getZoneController().getCurrentZone()
                    .getCell(playerToNotify));
            MessageController
                    .getPlayerHandler(
                            this.matchController.getPartyController()
                                    .getCurrentParty()
                                    .getPlayerUUID(playerToNotify))
                    .getAcceptPlayer().sendMessage(new Message(viewModel));
        }
    }

}

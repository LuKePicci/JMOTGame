package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorHighlight;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorViewModel;
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

    /** The strategies. */
    private static Map<ActionType, Class> strategies = new HashMap<ActionType, Class>();

    /** The match controller. */
    protected MatchController matchController;

    /** The request. */
    protected ActionRequest req;

    /** The current player. */
    protected Player player;

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
     */
    public abstract void processAction();

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
     */
    protected void hasObject(SectorCard drawnCard) {
        if (drawnCard.hasObjectSymbol()) {
            ItemCard icard;
            // only the itemDeck could end its cards
            try {
                icard = this.matchController.getMatch().getItemsDeck()
                        .pickCard();
            } catch (EmptyStackException e) {
                LoggerMethods
                        .emptyStackException(e,
                                "the stack of item card is empty; but it is not a problem");
                // informs the player that there are not item card available
                try {
                    this.notifyCurrentPlayerByServer("No more cards in the item deck.");
                } catch (DisconnectedException e1) {
                    LoggerMethods
                            .disconnectedException(
                                    e1,
                                    "no problem: the player will notice this because he doesn't have any new item card");
                }
                return;
            }
            this.player.getItemsDeck().getCards().add(icard);
            // notifies the player about the drawn card
            try {
                this.notifyCurrentPlayerByServer("You have just picked a "
                        + icard.getItem().toString() + " item.");
                this.matchController.updateDeckView(player);
            } catch (DisconnectedException e1) {
                LoggerMethods
                        .disconnectedException(
                                e1,
                                "no problem: the player will discover his new card as soon as he comes back; his deck will be update as soon as he comes back thanks to modelSender(Player returningPlayer) in MatchController");
            }
            // the other players are informed that an item card has been drawn.
            this.notifyOtherPlayers(this.matchController.getTurnController()
                    .getTurn().getCurrentPlayer().getName()
                    + " has just picked an item card.");

            this.matchController.updatePartyToAllPlayers();
            if (this.player.getItemsDeck().getCards().size() > 3) {
                this.matchController.getTurnController().getTurn()
                        .setMustDiscard(true);
                try {
                    this.notifyCurrentPlayerByServer("You must use or discard at least one card in this turn.");
                } catch (DisconnectedException e) {
                    LoggerMethods
                            .disconnectedException(
                                    e,
                                    "the player won't be able to end his turn until he discards a card, if he won't figure it out the game will automatically discard a card");
                }
                this.matchController.sendTurnViewModel();
            }
        }
    }

    // NOTIFICATION METHODS

    /**
     * Notifies the string received to the match chat adding the current player
     * name before the string and using server as sender.
     *
     * @param what
     *            the string to notify
     */
    protected void notifyInChatByCurrentPlayer(String what) {
        this.matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel(this.matchController
                        .getTurnController().getTurn().getCurrentPlayer()
                        .getName()
                        + ": " + what, MatchController.SERVER_WORD_TEXT,
                        ChatVisibility.PARTY)));
    }

    /**
     * Notifies the string received to the match chat using server as sender.
     *
     * @param what
     *            the string to notify
     */
    protected void notifyInChatByServer(String what) {
        this.matchController.getPartyController()
                .sendMessageToParty(
                        new ChatMessage(new ChatViewModel(what,
                                MatchController.SERVER_WORD_TEXT,
                                ChatVisibility.PARTY)));
    }

    /**
     * Notifies the string received to the current player using server as
     * sender.
     *
     * @param what
     *            the string to notify
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void notifyCurrentPlayerByServer(String what)
            throws DisconnectedException {
        MessageController.getPlayerHandler(
                this.matchController.getPartyController().getCurrentParty()
                        .getPlayerUUID(player)).dispatchOutgoing(
                new ChatMessage(
                        new ChatViewModel(what,
                                MatchController.SERVER_WORD_TEXT,
                                ChatVisibility.PLAYER)));
    }

    /**
     * Notifies all party players except the current player about the string
     * received.
     *
     * @param about
     *            the string received
     */
    protected void notifyOtherPlayers(String about) {
        List<Player> others = this.matchController.obtainPartyPlayers();
        others.remove(this.matchController.getTurnController().getTurn()
                .getCurrentPlayer());
        for (Player pl : others) {
            this.matchController.notifyAPlayerAbout(pl, about);
        }
    }

    /**
     * Shows the card received to the current player.
     *
     * @param card
     *            the card to notify
     * @throws DisconnectedException
     *             the disconnected exception
     */
    protected void showCardToCurrentPlayer(Card card)
            throws DisconnectedException {
        MessageController.getPlayerHandler(
                this.matchController.getPartyController().getCurrentParty()
                        .getPlayerUUID(player)).dispatchOutgoing(
                new Message(card));
    }

    /**
     * Sends map variation to all party players; a variation could be about
     * player's location, used hatch,... (see SectorHighlight enum) .
     *
     * @param sec
     *            the sector
     * @param highlight
     *            the kind of highlight
     */
    protected void sendMapVariationToParty(Sector sec, SectorHighlight highlight) {
        SectorViewModel viewModel = new SectorViewModel(sec, highlight);
        for (Player playerToNotify : this.matchController.obtainPartyPlayers()) {
            try {
                this.matchController.sendViewModelToAPlayer(playerToNotify,
                        viewModel);
            } catch (DisconnectedException e) {
                LoggerMethods
                        .disconnectedException(
                                e,
                                "the players who will not receive this update will be updated as soon as they reconnect using modelSender(Player returningPlayer) in MatchController");
            }
        }
    }

    /**
     * Gets the char whose alphabet position is the number received.
     *
     * @param i
     *            the number received
     * @return the char obtained from number received
     */
    protected String getCharFromNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
    }

    /**
     * Gets the string from sector received. This string is the literal
     * representation of the sector coordinates.
     *
     * @param sec
     *            the sector received
     * @return the string
     */
    protected String getStringFromSector(Sector sec) {
        return this.getCharFromNumber(sec.getPoint().getOffsetX() + 1)
                + String.format("%02d", sec.getPoint().getOffsetY() + 1);
    }

    /**
     * Gets the string from the hex point received. This string is the literal
     * representation of the hex coordinates.
     *
     * @param hex
     *            the hex coordinates
     * @return the string
     */
    protected String getStringFromHexPoint(HexPoint hex) {
        return this.getCharFromNumber(hex.getOffsetX() + 1)
                + String.format("%02d", hex.getOffsetY() + 1);
    }

}

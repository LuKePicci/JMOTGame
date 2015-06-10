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
     * Inits the action.
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

    // controllo la presenza del sibolo oggetto sulla carta settore pescata
    /**
     * Checks for object.
     *
     * @param drawnCard
     *            the drawn card
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
                MessageController
                        .getPlayerHandler(
                                matchController
                                        .getPartyController()
                                        .getCurrentParty()
                                        .getPlayerUUID(
                                                matchController
                                                        .getTurnController()
                                                        .getTurn()
                                                        .getCurrentPlayer()))
                        .getAcceptPlayer()
                        .sendMessage(
                                new ChatMessage(new ChatViewModel(
                                        "NO ITEM CARDS AVAILABLE", "Server",
                                        ChatVisibility.PLAYER)));
                return;
            }
            player.getItemsDeck().getCards().add(icard);
            // notifica il giocatore sulla carta pescata
            MessageController
                    .getPlayerHandler(
                            matchController
                                    .getPartyController()
                                    .getCurrentParty()
                                    .getPlayerUUID(
                                            matchController.getTurnController()
                                                    .getTurn()
                                                    .getCurrentPlayer()))
                    .getAcceptPlayer()
                    .sendMessage(
                            new ChatMessage(new ChatViewModel(
                                    "You have just picked a "
                                            + icard.getItem().toString()
                                            + " card", "Server",
                                    ChatVisibility.PLAYER)));
            MessageController
                    .getPlayerHandler(
                            matchController.getPartyController()
                                    .getCurrentParty().getPlayerUUID(player))
                    .getAcceptPlayer()
                    .sendMessage(
                            new Message(matchController.getTurnController()
                                    .getTurn().getCurrentPlayer()
                                    .getItemsDeck().getViewModel()));
            if (player.getItemsDeck().getCards().size() > 3) {
                matchController.getTurnController().getTurn()
                        .setMustDiscard(true);
                MessageController
                        .getPlayerHandler(
                                matchController
                                        .getPartyController()
                                        .getCurrentParty()
                                        .getPlayerUUID(
                                                matchController
                                                        .getTurnController()
                                                        .getTurn()
                                                        .getCurrentPlayer()))
                        .getAcceptPlayer()
                        .sendMessage(
                                new ChatMessage(
                                        new ChatViewModel(
                                                "You must use or discard at least one card in this turn",
                                                "Server", ChatVisibility.PLAYER)));
            }
        }
    }

    /**
     * Obtain party players.
     *
     * @return the list of players of the current party
     */
    public List<Player> obtainPartyPlayers() {
        return matchController.obtainPartyPlayers();
    }

    /**
     * Notifies in the chat the string received.
     *
     * @param card
     *            the string to be notified
     */
    protected void notifyInChatByCurrentPlayer(String card) {
        matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel(card, matchController
                        .getTurnController().getTurn().getCurrentPlayer()
                        .getName(), ChatVisibility.PARTY)));
    }

    protected void updateCardsView() {
        MessageController
                .getPlayerHandler(
                        matchController.getPartyController().getCurrentParty()
                                .getPlayerUUID(player))
                .getAcceptPlayer()
                .sendMessage(
                        new Message(matchController.getTurnController()
                                .getTurn().getCurrentPlayer().getItemsDeck()
                                .getViewModel()));
    }

}

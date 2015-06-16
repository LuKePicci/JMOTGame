package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.List;
import java.util.Set;

/**
 * The Class TurnController.
 */
public class TurnController {

    /** The turn. */
    protected Turn turn;

    /**
     * Instance of discard card action needed if a player can't end his turn
     * properly.
     */
    protected DiscardCard forcedDiscard = new DiscardCard();

    /**
     * Gets the turn.
     *
     * @return the turn
     */
    public Turn getTurn() {
        return this.turn;
    }

    /**
     * Sets the turn.
     *
     * @param turn
     *            the new turn
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * Prepares the first turn by assigning it to the player whose index is one.
     *
     * @param playerList
     *            the player list
     */
    public void firstTurn(List<Player> playerList) {
        for (Player nextPlayer : playerList) {
            if (nextPlayer.getIndex() == 1) {
                this.turn = new Turn(nextPlayer);
                return;
            }
        }
    }

    /**
     * Gets the players of this party.
     *
     * @param matchController
     *            the match controller
     * @return the players of the party
     */
    public Set<Player> getPartyPlayers(MatchController matchController) {
        // take all party players passing through matchController
        return matchController.getPartyController().getCurrentParty()
                .getMembers().keySet();// set containing all party players
    }

    /**
     * Assigns the turn to the next player.
     *
     * @param matchController
     *            the match controller
     * @throws DisconnectedException
     *             the disconnected exception
     */
    public void nextTurn(MatchController matchController)
            throws DisconnectedException {
        // in case a player has gone offline and the new turn is called due to a
        // timer expiration, I must guarantee the player doesn't end his turn
        // with more than three item card
        this.checkLegality(matchController);
        Set<Player> playerList = getPartyPlayers(matchController);
        int playerNumber = playerList.size();
        int index = this.turn.getCurrentPlayer().getIndex();
        playerList.removeAll(matchController.getMatch().getDeadPlayer());
        playerList.removeAll(matchController.getMatch().getRescuedPlayer());
        for (int i = 0; i < playerNumber; i++) {
            if (index == playerNumber) {
                index = 1;
                matchController.getMatch().incrementTurnCount();
            } else {
                index++;
            }
            for (Player nextPlayer : playerList) {
                if (nextPlayer.getIndex() == index
                        && !this.checkIfPlayerIsOnline(nextPlayer,
                                matchController)) {
                    // it's nextPlayer's turn
                    matchController.checkEndGame();
                    matchController.getTurnController().setTurn(
                            new Turn(nextPlayer));
                    this.notify(nextPlayer, matchController);
                    return;
                }
            }
        }
    }

    /**
     * Checks if a player is ending his turn without having solved the
     * obligation of discarding a card. In case of positive answer, the method
     * discards a card before ending the player's turn.
     *
     * @param matchController
     *            the match controller
     * @throws DisconnectedException
     *             the disconnected exception
     */
    private void checkLegality(MatchController matchController)
            throws DisconnectedException {
        if (matchController.getTurnController().getTurn().getMustDiscard()) {
            Object[] playerCards = matchController.getTurnController()
                    .getTurn().getCurrentPlayer().getItemsDeck().getCards()
                    .toArray();
            ActionRequest action = new ActionRequest(ActionType.DISCARD_CARD,
                    null, ((ItemCard) playerCards[3]).getItem());
            forcedDiscard.initAction(matchController, action);
            forcedDiscard.processAction();
        }
    }

    /**
     * Checks if the player is online.
     *
     * @param player
     *            the player
     * @param matchController
     *            the match controller
     * @return true, if the player is online
     */
    protected boolean checkIfPlayerIsOnline(Player player,
            MatchController matchController) {
        return MessageController
                .getPlayerHandler(
                        matchController.getPartyController().getCurrentParty()
                                .getPlayerUUID(player)).getAcceptPlayer()
                .connectionLost();

    }

    /**
     * Notify the new turn to all players.
     *
     * @param nextPlayer
     *            the next player
     * @param matchController
     *            the match controller
     */
    protected void notify(Player nextPlayer, MatchController matchController) {
        matchController.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel("It's "
                        + nextPlayer.getName() + "'s turn", "Server",
                        ChatVisibility.PARTY)));
    }

}

package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;

import java.util.List;
import java.util.Set;

/**
 * The Class TurnController.
 */
public class TurnController {

    /** The turn. */
    protected Turn turn;

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
     */
    public void nextTurn(MatchController matchController) {
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
                        && checkIfPlayerIsOnline(nextPlayer, matchController)) {
                    // it's nextPlayer's turn
                    matchController.checkEndGame();
                    matchController.getTurnController().setTurn(
                            new Turn(nextPlayer));
                    notify(nextPlayer, matchController);
                    return;
                }
            }
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

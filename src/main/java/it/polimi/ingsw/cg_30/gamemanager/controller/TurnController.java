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
    private Turn turn;

    /**
     * Sets the turn (method implemented only for testing purpose).
     *
     * @param turn
     *            the new turn
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * Gets the turn.
     *
     * @return the turn
     */
    public Turn getTurn() {
        return turn;
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
        // prendo i membri dal party passando da partyController
        return matchController.getPartyController().getCurrentParty()
                .getMembers().keySet();// set con tutti i
                                       // player del party
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
        int index = turn.getCurrentPlayer().getIndex();// indice del giocatore
        // successivo a quello di cui
        // voglio terminare il turno
        playerList.removeAll(matchController.getMatch().getDeadPlayer());
        playerList.removeAll(matchController.getMatch().getRescuedPlayer());
        for (int i = 0; i < playerNumber; i++) {
            if (index == playerNumber) {
                index = 1;
                matchController.getMatch().incrementTurnCount();
            } else {
                index++;
            }
            for (Player nextPlayer : playerList) {// faccio ciò in quanto non ho
                                                  // la
                                                  // cartezza che nel set
                                                  // restituito
                                                  // i player siano nell'ordine
                                                  // index (vedi attributi
                                                  // Player)
                if (nextPlayer.getIndex() == index
                        && checkIfPlayerIsOnline(nextPlayer, matchController)) {
                    // passo il turno a nextPlayer
                    matchController.checkEndGame();
                    matchController.getTurnController().setTurn(
                            new Turn(nextPlayer));
                    notify(nextPlayer, matchController);
                    return;
                }
            }
        }
        // qui non ci dovrei mai arrivare
    }

    /**
     * Check if the player is online.
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

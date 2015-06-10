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

    // TODO metodo implementato per il testing
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

    public void firstTurn(List<Player> playerList) {
        for (Player nextPlayer : playerList) {
            if (nextPlayer.getIndex() == 1) {
                this.turn = new Turn(nextPlayer);
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
        Set<Player> playerList = matchController.getPartyController()
                .getCurrentParty().getMembers().keySet();// set con tutti i
                                                         // player del party
        return playerList;
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
                if (nextPlayer.getIndex() == index) {
                    // qui passo il turno a nextPlayer
                    turn = new Turn(nextPlayer);
                    matchController.checkEndGame();
                    matchController.getPartyController().sendMessageToParty(
                            new ChatMessage(new ChatViewModel(nextPlayer
                                    .getName() + "'s turn", "Server",
                                    ChatVisibility.PARTY)));
                }
            }
        }
        // qui non ci dovrei mai arrivare
    }
}

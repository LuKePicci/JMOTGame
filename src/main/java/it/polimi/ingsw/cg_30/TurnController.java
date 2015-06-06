package it.polimi.ingsw.cg_30;

import java.util.Set;

/**
 * The Class TurnController.
 */
public class TurnController {

    /** The turn. */
    private Turn turn;

    /**
     * Gets the turn.
     *
     * @return the turn
     */
    public Turn getTurn() {
        return turn;
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
     * Assigns the turn to the first player.
     *
     * @param matchController
     *            the match controller
     * @return the turn
     */
    public Turn firstTurn(MatchController matchController) {
        Set<Player> playerList = getPartyPlayers(matchController);
        for (Player nextPlayer : playerList) {
            if (nextPlayer.getIndex() == 1) {
                turn = new Turn(nextPlayer);
                return turn;
            }
        }
        return null;
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
                    // TODO comunico il passaggio del turno
                }
            }
        }
        // qui non ci dovrei mai arrivare
    }

}

package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class TurnController {

    private Turn turn;

    public Turn getTurn() {
        return turn;
    }

    public void nextTurn(MatchController matchController) {
        // prendo i membri dal party passando da partyController
        Set<Player> playerList = new HashSet<Player>();
        playerList = matchController.getPartyController().getCurrentParty()
                .getMembers().keySet();// set con tutti i player del party
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

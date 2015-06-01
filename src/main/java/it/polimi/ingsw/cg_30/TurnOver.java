package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class TurnOver extends ActionController {

    private MatchController matchController;

    public TurnOver(MatchController matchController) {

    }

    @Override
    public boolean isValid() {
        // player deve essersi mosso
        if (matchController.getCurrentTurn().getTurn().getMustMove() == true) {
            return false;
        }
        // player non deve avere 4 carte in mano
        if (matchController.getCurrentTurn().getTurn().getMustDiscard() == true) {
            return false;
        }
        return true;
    }

    @Override
    public ActionMessage processAction() {
        // prendo i membri dal party passando da partyController
        Set<Player> playerList = new HashSet<Player>();
        playerList = matchController.getCurrentParty().getCurrentParty()
                .getMembers().keySet();// set con tutti i player del party
        int playerNumber = playerList.size();
        int index = matchController.getCurrentTurn().getTurn()
                .getCurrentPlayer().getIndex();// indice del giocatore
                                               // successivo a quello di cui
                                               // voglio terminare il turno
        playerList.removeAll(matchController.getDeadPlayer());
        playerList.removeAll(matchController.getRescuedPlayer());
        for (int i = 0; i < playerNumber; i++) {
            if (index == playerNumber) {
                index = 1;
                matchController.incrementTurnCount();
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
                    matchController.newTurn(nextPlayer);
                    // TO DO comunico il passaggio del turno
                    matchController.checkEndGame();
                    // TO DO rimuovere la seguente riga
                    return null;
                }
            }
        }
        // qui non ci dovrei mai arrivare
        // TO DO rimuovere la seguente riga
        return null;
    }

}

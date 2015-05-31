package it.polimi.ingsw.cg_30;

import java.io.Serializable;

public class MatchController implements Serializable {

    private static final long serialVersionUID = 7559199248239316673L;

    public TurnController currentTurn;

    public PartyController currentParty;

    private StackedDeck itemsDeck;
    private StackedDeck hatchesDeck;
    private StackedDeck sectorsDeck;

    private int turnCount;

    private ZoneController currentZone;

    public MatchController() {
        // TODO assign all sub-controllers instances
    }

    public int getTurnCount() {
        throw new UnsupportedOperationException();
    }

    private void initMatch() {

        // TODO call init methods on every sub-controller

        this.currentTurn = this.newTurn();
    }

    private TurnController newTurn() {
        throw new UnsupportedOperationException();
    }

    public TurnController getCurrentTurn() {
        throw new UnsupportedOperationException();
    }

    public PartyController getCurrentParty() {
        throw new UnsupportedOperationException();
    }

    public PartyController getItemsDeck() {
        throw new UnsupportedOperationException();
    }

    public ZoneController getCurrentZone() {
        throw new UnsupportedOperationException();
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public synchronized void processActionRequest(ActionRequest req) {
        throw new UnsupportedOperationException();
    }

}

package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class Match {

    private int turnCount;
    private StackedDeck itemsDeck;
    private StackedDeck hatchesDeck;
    private StackedDeck sectorsDeck;
    private Set<Player> deadPlayer;
    private Set<Player> rescuedPlayer;

    // COSTRUTTORE
    public Match() {
        this.turnCount = 0;
        this.itemsDeck = StackedDeck.newStackedDeckItem();
        this.hatchesDeck = StackedDeck.newStackedDeckHatch();
        this.sectorsDeck = StackedDeck.newStackedDeckSector();
        this.deadPlayer = new HashSet<Player>();
        this.rescuedPlayer = new HashSet<Player>();
    }

    // getter
    public int getTurnCount() {
        return turnCount;
    }

    public StackedDeck getItemsDeck() {
        return itemsDeck;
    }

    public StackedDeck getHatchesDeck() {
        return hatchesDeck;
    }

    public StackedDeck getSectorsDeck() {
        return sectorsDeck;
    }

    public Set<Player> getDeadPlayer() {
        return deadPlayer;
    }

    public Set<Player> getRescuedPlayer() {
        return this.rescuedPlayer;
    }

    // altri metodi
    public void incrementTurnCount() {
        this.turnCount++;
    }

}

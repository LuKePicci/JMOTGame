package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

/**
 * The Class Match.
 */
public class Match {

    /** The turn count. */
    private int turnCount;

    /** The items deck. */
    private StackedDeck<ItemCard> itemsDeck;

    /** The hatches deck. */
    private StackedDeck<HatchCard> hatchesDeck;

    /** The sectors deck. */
    private StackedDeck<SectorCard> sectorsDeck;

    /** The dead player. */
    private Set<Player> deadPlayer;

    /** The rescued player. */
    private Set<Player> rescuedPlayer;

    // COSTRUTTORE
    /**
     * Instantiates a new match.
     */
    public Match() {
        this.turnCount = 1;
        this.itemsDeck = StackedDeck.newStackedDeckItem();
        this.hatchesDeck = StackedDeck.newStackedDeckHatch();
        this.sectorsDeck = StackedDeck.newStackedDeckSector();
        this.deadPlayer = new HashSet<Player>();
        this.rescuedPlayer = new HashSet<Player>();
    }

    // getter
    /**
     * Gets the turn count.
     *
     * @return the turn count
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Gets the items deck.
     *
     * @return the items deck
     */
    public StackedDeck<ItemCard> getItemsDeck() {
        return itemsDeck;
    }

    /**
     * Gets the hatches deck.
     *
     * @return the hatches deck
     */
    public StackedDeck<HatchCard> getHatchesDeck() {
        return hatchesDeck;
    }

    /**
     * Gets the sectors deck.
     *
     * @return the sectors deck
     */
    public StackedDeck<SectorCard> getSectorsDeck() {
        return sectorsDeck;
    }

    /**
     * Gets the dead player.
     *
     * @return the dead player
     */
    public Set<Player> getDeadPlayer() {
        return deadPlayer;
    }

    /**
     * Gets the rescued player.
     *
     * @return the rescued player
     */
    public Set<Player> getRescuedPlayer() {
        return this.rescuedPlayer;
    }

    // altri metodi
    /**
     * Increment turn count.
     */
    public void incrementTurnCount() {
        this.turnCount++;
    }

}

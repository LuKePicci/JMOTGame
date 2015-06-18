package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;

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

    /** The dead players. */
    private Set<Player> deadPlayer;

    /** The rescued players. */
    private Set<Player> rescuedPlayer;

    /**
     * Instantiates a new match.
     */
    public Match() {
        this.turnCount = 1;
        this.itemsDeck = EftaiosDecks.newStackedDeckItem();
        this.hatchesDeck = EftaiosDecks.newStackedDeckHatch();
        this.sectorsDeck = EftaiosDecks.newStackedDeckSector();
        this.deadPlayer = new HashSet<Player>();
        this.rescuedPlayer = new HashSet<Player>();
    }

    /**
     * Gets the turn count.
     *
     * @return the turn count
     */
    public int getTurnCount() {
        return this.turnCount;
    }

    /**
     * Gets the items deck.
     *
     * @return the items deck
     */
    public StackedDeck<ItemCard> getItemsDeck() {
        return this.itemsDeck;
    }

    /**
     * Gets the hatches deck.
     *
     * @return the hatches deck
     */
    public StackedDeck<HatchCard> getHatchesDeck() {
        return this.hatchesDeck;
    }

    /**
     * Gets the sectors deck.
     *
     * @return the sectors deck
     */
    public StackedDeck<SectorCard> getSectorsDeck() {
        return this.sectorsDeck;
    }

    /**
     * Gets the dead player.
     *
     * @return the dead player
     */
    public Set<Player> getDeadPlayer() {
        return this.deadPlayer;
    }

    /**
     * Gets the rescued player.
     *
     * @return the rescued player
     */
    public Set<Player> getRescuedPlayer() {
        return this.rescuedPlayer;
    }

    /**
     * Increments turn count.
     */
    public void incrementTurnCount() {
        this.turnCount++;
    }

}

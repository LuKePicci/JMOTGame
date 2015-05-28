package it.polimi.ingsw.cg_30;

import java.io.Serializable;

/**
 * The Class Player.
 */
public class Player implements Serializable {

    /** The name. */
    private String name;

    /** The index. */
    private int index;

    /** The identity. */
    private PlayerCard identity;

    /** The kills count. */
    private int killsCount;

    /** The items deck. */
    private SpareDeck itemsDeck;

    /** The player is dead. */
    private boolean isDead;

    /**
     * Instantiates a new player.
     *
     * @param name
     *            the name
     * @param index
     *            the index
     * @param identity
     *            the identity
     */
    public Player(String name, int index, PlayerCard identity) {
        this.name = name;
        this.index = index;
        this.identity = identity;
        this.killsCount = 0;
        this.itemsDeck = new SpareDeck();
        this.isDead = false;
    }

    /**
     * Instantiates a new player.
     */
    protected Player() {
        this.name = "unknown player";
        this.index = 0;
        this.identity = null;
        this.killsCount = 0;
        this.itemsDeck = new SpareDeck();
        this.isDead = false;
    }

    /**
     * Gets the index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the identity.
     *
     * @return the identity
     */
    public PlayerCard getIdentity() {
        return identity;
    }

    /**
     * Gets the items deck.
     *
     * @return the items deck
     */
    public SpareDeck getItemsDeck() {
        return itemsDeck;
    }

    /**
     * Gets the kills count.
     *
     * @return the kills count
     */
    public int getKillsCount() {
        return killsCount;
    }

    /**
     * Gets the checks if player is dead.
     *
     * @return the checks if player is dead
     */
    public boolean getIsDead() {
        return isDead;
    }

    /**
     * Sets that the player is dead.
     */
    public void setIsDead() {
        this.isDead = true;
    }

    /**
     * Increment kills count.
     */
    public void incrementKillsCount() {
        killsCount++;
    }
}

package it.polimi.ingsw.cg_30;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player implements Serializable {

    /** The name. */
    private String name;

    /** Player readiness to play. */
    private boolean ready;

    /** The index. */
    private int index;

    /** The identity. */
    private PlayerCard identity;

    /** The kills count. */
    private int killsCount;

    /** The items deck. */
    private SpareDeck itemsDeck;

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
    }

    /**
     * Checks if is ready to play.
     *
     * @return true, if is ready
     */
    public boolean isReady() {
        return this.ready;
    }

    /**
     * Sets the readiness of this player.
     *
     * @param r
     *            the new ready state
     */
    public void setReady(boolean r) {
        this.ready = r;
    }

    /**
     * Gets the index of this player in the context of its party.
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
     * Increment kills count.
     */
    public void incrementKillsCount() {
        killsCount++;
    }
}

package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCharacter;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;

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
    // index deve essere compreso tra 1 e 8

    /** The identity. */
    private PlayerCard identity;

    /** The kills count. */
    private int killsCount;

    /** The items deck. */
    private SpareDeck<ItemCard> itemsDeck;

    /**
     * Instantiates a new player leaving identity undefined.
     *
     * @param name
     *            the name
     * @param index
     *            the index
     */
    public Player(String name, int index) {
        this(name, index, null);
    }

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
        this.itemsDeck = new SpareDeck<ItemCard>();
    }

    /**
     * Instantiates a new mock player (for testing).
     */
    protected Player() {
        PlayerCard es = new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_CAPTAIN);
        this.name = "unknown player";
        this.index = 0;
        this.identity = es;
        this.killsCount = 0;
        this.itemsDeck = new SpareDeck<ItemCard>();
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
    public SpareDeck<ItemCard> getItemsDeck() {
        return itemsDeck;
    }

    /**
     * Sets the player identity from the given card.
     *
     * @param myId
     *            the new identity
     */
    public void setIdentity(PlayerCard myId) {
        this.identity = myId;
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

    /**
     * Decrement kills count.
     */
    public void decrementKillsCount() {
        killsCount--;
    }

}

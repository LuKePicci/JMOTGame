package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

import java.io.Serializable;

/**
 * The Class Player.
 */
public class Player implements IViewable, Serializable {

    /** The name. */
    private String name;

    /** Player readiness to play. */
    private boolean ready;

    /**
     * The index. (Index value is between 1 and 8)
     */
    private int index;

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
        return this.index;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the identity.
     *
     * @return the identity
     */
    public PlayerCard getIdentity() {
        return this.identity;
    }

    /**
     * Gets the items deck.
     *
     * @return the items deck
     */
    public SpareDeck<ItemCard> getItemsDeck() {
        return this.itemsDeck;
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
        return this.killsCount;
    }

    /**
     * Increments kills count.
     */
    public void incrementKillsCount() {
        this.killsCount++;
    }

    /**
     * Decrements kills count.
     */
    public void decrementKillsCount() {
        this.killsCount--;
    }

    @Override
    public ViewModel getViewModel() {
        return new PlayerViewModel(this.getIndex(), this.getName(),
                this.getKillsCount());
    }

}

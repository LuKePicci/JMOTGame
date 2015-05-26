package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * The Class Deck.
 */
public abstract class Deck implements Serializable {

    private static final long serialVersionUID = -555545505649022630L;
    /** The bucket. */
    protected HashSet<Card> bucket;

    /**
     * Instantiates a new deck.
     */
    protected Deck() {
        this.bucket = new HashSet<Card>();
    }

    /**
     * Gets the card collection.
     *
     * @return the card collection
     */
    public abstract Collection getCardCollection();

    /**
     * Recycle.
     */
    public abstract void recycle();

    /**
     * Put into bucket.
     *
     * @param c
     *            the card that will be put into bucket
     */
    public void putIntoBucket(Card c) {
        this.bucket.add(c);
    }

}

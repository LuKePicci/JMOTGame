package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class Deck.
 */
public abstract class Deck implements Serializable {

    /** The bucket. */
    protected Set<Card> bucket;

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
     *            the c
     */
    public void putIntoBucket(Card c) {
        this.bucket.add(c);
    }

    /**
     * Instantiates a new deck.
     */
    protected Deck() {
        this.bucket = new HashSet<Card>();
    }

}

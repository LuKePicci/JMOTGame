package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.HashSet;

/**
 * The Class SpareDeck.
 */
public class SpareDeck extends Deck {

    /**
     * 
     */
    private static final long serialVersionUID = -8291946562346963478L;
    /** The cards. */
    private HashSet<Card> cards;

    /**
     * Instantiates a new spare deck.
     */
    public SpareDeck() {
        this.cards = new HashSet<Card>();
    }

    /**
     * Gets the cards.
     *
     * @return the cards
     */
    public HashSet<Card> getCards() {
        return cards;
    }

    /**
     * Gets the card collection.
     *
     * @return the card collection
     */
    @Override
    public Collection getCardCollection() {
        return this.cards;
    }

    /**
     * Recycle Deck using all cards in bucket
     */
    @Override
    public void recycle() {
        this.cards.clear();
        this.cards.addAll(bucket);
        this.bucket.clear();
    }

}
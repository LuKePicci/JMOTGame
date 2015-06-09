package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class SpareDeck.
 */
public class SpareDeck<C extends Card> extends Deck<C> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8291946562346963478L;

    /** The cards. */
    private Set<C> cards;

    /**
     * Instantiates a new spare deck.
     */
    public SpareDeck() {
        this.cards = new HashSet<C>();
    }

    /**
     * Gets the cards.
     *
     * @return the cards
     */
    public Set<C> getCards() {
        return cards;
    }

    /**
     * Gets the card collection.
     *
     * @return the card collection
     */
    @Override
    public Collection<C> getCardCollection() {
        return this.cards;
    }

    /**
     * Recycle Deck using all cards in bucket.
     */
    @Override
    public void recycle() {
        this.cards.clear();
        this.cards.addAll(bucket);
        this.bucket.clear();
    }

}
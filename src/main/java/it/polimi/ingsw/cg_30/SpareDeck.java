package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class SpareDeck.
 */
public class SpareDeck extends Deck {

    /** The cards. */
    private Set<Card> cards;

    /**
     * Gets the cards.
     *
     * @return the cards
     */
    public Set<Card> getCards() {
        return cards;
    }

    // COSTRUTTORE
    /**
     * Instantiates a new spare deck.
     */
    public SpareDeck() {
        this.cards = new HashSet<Card>();
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

}
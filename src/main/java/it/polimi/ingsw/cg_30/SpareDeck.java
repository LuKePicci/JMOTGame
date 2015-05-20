package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SpareDeck extends Deck {

    private Set<Card> cards;

    public Set<Card> getCards() {
        return cards;
    }

    // COSTRUTTORE
    public SpareDeck() {
        this.cards = new HashSet<Card>();
    }

    @Override
    public Collection getCardCollection() {
        return this.cards;
    }

}
package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.Stack;

/**
 * The Class StackedDeck.
 */
public class StackedDeck extends Deck {

    /** The cards. */
    private Stack<Card> cards;

    public void shuffle() {
        throw new UnsupportedOperationException();
    }

    public Card pickCard() {
        return this.cards.pop();
    }

    public Card pickAndThrow() {
        Card c = pickCard();
        this.putIntoBucket(c);
        return c;
    }

    public StackedDeck newStackedDeckHatch() {
        throw new UnsupportedOperationException();
    }

    public StackedDeck newStackedDeckSector() {
        throw new UnsupportedOperationException();
    }

    public StackedDeck newStackedDeckItem() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the card collection.
     *
     * @return the card collection
     */
    @Override
    public Collection getCardCollection() {
        return cards;
    }

    // "COSTRUTTORI"
    /**
     * Instantiates a new stacked deck.
     */
    private StackedDeck() {
        this.cards = new Stack<Card>();
    }

    /**
     * Stacked deck hatch.
     *
     * @return the stacked deck
     */
    public static StackedDeck StackedDeckHatch() {
        StackedDeck ex = new StackedDeck();
        for (int i = 0; i < 3; i++) {
            ex.cards.push(new HatchCard(HatchChance.Free));
            ex.cards.push(new HatchCard(HatchChance.Locked));
        }
        return ex;
    }

    /**
     * Stacked deck sector.
     *
     * @return the stacked deck
     */
    public static StackedDeck StackedDeckSector() {
        StackedDeck ex = new StackedDeck();
        for (int i = 0; i < 5; i++) {
            ex.cards.push(new SectorCard(SectorEvent.Silence));
        }
        for (int i = 0; i < 10; i++) {
            ex.cards.push(new SectorCard(SectorEvent.NoiseAny));
            ex.cards.push(new SectorCard(SectorEvent.NoiseYour));
        }
        return ex;
    }

    /**
     * Stacked deck item.
     *
     * @return the stacked deck
     */
    public static StackedDeck StackedDeckItem() {
        StackedDeck ex = new StackedDeck();
        ex.cards.push(new ItemCard(Item.Defense));
        for (int i = 0; i < 2; i++) {
            ex.cards.push(new ItemCard(Item.Adrenaline));
            ex.cards.push(new ItemCard(Item.Attack));
            ex.cards.push(new ItemCard(Item.Teleport));
            ex.cards.push(new ItemCard(Item.Spotlight));
        }
        for (int i = 0; i < 3; i++) {
            ex.cards.push(new ItemCard(Item.Sedatives));
        }
        return ex;
    }

}

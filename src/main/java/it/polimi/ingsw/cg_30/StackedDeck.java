package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

/**
 * The Class StackedDeck.
 */
public class StackedDeck extends Deck {

    /** The cards. */
    private Stack<Card> cards;

    /**
     * Shuffle.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Pick card.
     *
     * @return the card
     */
    public Card pickCard() {
        return this.cards.pop();
    }

    /**
     * Pick card and throw it into the bucket.
     *
     * @return the card
     */
    public Card pickAndThrow() {
        Card c = pickCard();
        this.putIntoBucket(c);
        return c;
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
        super();
        this.cards = new Stack<Card>();
    }

    /**
     * Stacked deck hatch.
     *
     * @return the stacked deck
     */
    public static StackedDeck newStackedDeckHatch() {
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
    public static StackedDeck newStackedDeckSector() {
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
    public static StackedDeck newStackedDeckItem() {
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

    /**
     * Recycle Deck using all cards in bucket
     */
    @Override
    public void recycle() {
        this.cards.clear();
        this.cards.addAll(bucket);
        this.shuffle();
        this.bucket.clear();
    }
}

package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * The Class StackedDeck.
 */
public class StackedDeck extends Deck {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7122684108628884837L;

    /** The cards. */
    private Stack<Card> cards;

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
            ex.cards.push(new HatchCard(HatchChance.FREE));
            ex.cards.push(new HatchCard(HatchChance.LOCKED));
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
            ex.cards.push(new SectorCard(SectorEvent.SILENCE, false));
        }
        for (int i = 0; i < 4; i++) {
            ex.cards.push(new SectorCard(SectorEvent.NOISE_ANY, true));
            ex.cards.push(new SectorCard(SectorEvent.NOISE_YOUR, true));
        }
        for (int i = 0; i < 6; i++) {
            ex.cards.push(new SectorCard(SectorEvent.NOISE_ANY, false));
            ex.cards.push(new SectorCard(SectorEvent.NOISE_YOUR, false));
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
        ex.cards.push(new ItemCard(Item.DEFENSE));
        for (int i = 0; i < 2; i++) {
            ex.cards.push(new ItemCard(Item.ADRENALINE));
            ex.cards.push(new ItemCard(Item.ATTACK));
            ex.cards.push(new ItemCard(Item.TELEPORT));
            ex.cards.push(new ItemCard(Item.SPOTLIGHT));
        }
        for (int i = 0; i < 3; i++) {
            ex.cards.push(new ItemCard(Item.SEDATIVES));
        }
        return ex;
    }

    /**
     * Shuffle the card collection.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Picks a card from the deck and throws an exception if both deck and
     * bucket are empty.
     *
     * @return the card
     * @throws EmptyStackException
     *             both bucket and deck are empty; the first exception is
     *             managed locally by recycling bucket card, if the bucket is
     *             empty the second exception is thrown. This exception must be
     *             managed by the method which called pickCard
     */
    public Card pickCard() throws EmptyStackException {
        try {
            return this.cards.pop();

        } catch (EmptyStackException e) {
            this.recycle();
            return this.cards.pop();
        }
    }

    /**
     * Pick card from deck and throw it into the bucket. If it can't pick a card
     * the exception is thrown.
     *
     * @return the card
     * @throws EmptyStackException
     *             both the deck and the bucket are empty, pickCard can't pick a
     *             card so it throws the exception. This exception must be
     *             managed by the method which called pickAndThrow
     */
    public Card pickAndThrow() throws EmptyStackException {
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
    public Collection<Card> getCardCollection() {
        return cards;
    }

    /**
     * Recycle Deck using all cards in bucket.
     */
    @Override
    protected void recycle() {
        this.cards.clear();
        this.cards.addAll(bucket);
        this.shuffle();
        this.bucket.clear();
    }
}

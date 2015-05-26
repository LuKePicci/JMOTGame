package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * The Class StackedDeck.
 */
public class StackedDeck extends Deck {

    /**
     * 
     */
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
            ex.cards.push(new SectorCard(SectorEvent.SILENCE));
        }
        for (int i = 0; i < 10; i++) {
            ex.cards.push(new SectorCard(SectorEvent.NOISE_ANY));
            ex.cards.push(new SectorCard(SectorEvent.NOISE_YOUR));
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
     * Shuffle.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Pick card.
     *
     * @return the card
     * @throws EmptyStackException
     *             both bucket and deck are empty
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
     * Pick card and throw it into the bucket.
     *
     * @return the card
     */
    public Card pickAndThrow() {// devo aggiungere anche qui la parte
                                // sull'eccezione?
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
     * Recycle Deck using all cards in bucket
     */
    @Override
    protected void recycle() {// non l'ho messo private per non invalidare il
                              // test
        this.cards.clear();
        this.cards.addAll(bucket);
        this.shuffle();
        this.bucket.clear();
    }
}

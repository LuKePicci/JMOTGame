package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.messaging.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;

import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;

/**
 * The Class StackedDeck.
 *
 * @param <C>
 *            the generic type
 */
public class StackedDeck<C extends Card> extends Deck<C> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7122684108628884837L;

    /** The cards. */
    private Stack<C> cards = new Stack<C>();

    /**
     * Instantiates a new stacked deck.
     */
    public StackedDeck(Collection<C> cardsToPut) {
        super();
        this.cards.addAll(cardsToPut);
    }

    /**
     * Shuffles the card collection.
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
    public C pickCard() {
        try {
            return this.cards.pop();

        } catch (EmptyStackException e) {
            LoggerMethods.emptyStackException(e, "Local exception management");
            this.recycle();
            return this.cards.pop();
        }
    }

    /**
     * Picks card from deck and throws it into the bucket. If it can't pick a
     * card the exception is thrown.
     *
     * @return the card
     * @throws EmptyStackException
     *             both the deck and the bucket are empty, pickCard can't pick a
     *             card so it throws the exception. This exception must be
     *             managed by the method which called pickAndThrow
     */
    public C pickAndThrow() {
        C c = pickCard();
        this.putIntoBucket(c);
        return c;
    }

    /**
     * Gets the card collection.
     *
     * @return the card collection
     */
    @Override
    public Stack<C> getCardCollection() {
        return cards;
    }

    /**
     * Recycles deck using all cards in bucket.
     */
    @Override
    protected void recycle() {
        this.cards.clear();
        this.cards.addAll(bucket);
        this.shuffle();
        this.bucket.clear();
    }

    /**
     * Gets the bucket.
     *
     * @return the bucket
     */
    public Set<C> getBucket() {
        return bucket;
    }

}

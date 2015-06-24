package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.DeckViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class Deck.
 *
 * @param <C>
 *            the generic type
 */
public abstract class Deck<C extends Card> implements IViewable, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -555545505649022630L;

    /** The bucket. */
    protected Set<C> bucket;

    /**
     * Instantiates a new deck.
     */
    protected Deck() {
        this.bucket = new HashSet<C>();
    }

    /**
     * Gets the card collection.
     *
     * @return the card collection
     */
    public abstract Collection<C> getCardCollection();

    /**
     * Recycle.
     */
    protected abstract void recycle();

    /**
     * Puts card into bucket.
     *
     * @param c
     *            the card that will be put into bucket
     */
    public void putIntoBucket(C c) {
        this.bucket.add(c);
    }

    /**
     * Put all cards into bucket.
     *
     * @param c
     *            the cards that will be put into bucket
     */
    public void putAllIntoBucket(Set<C> c) {
        this.bucket.addAll(c);
    }

    /**
     * @see it.polimi.ingsw.cg_30.gamemanager.model.IViewable#getViewModel()
     */
    @Override
    public ViewModel getViewModel() {
        return new DeckViewModel<C>(new ArrayList<C>(this.getCardCollection()));
    }

}

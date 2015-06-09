package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.DeckViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class Deck.
 */
public abstract class Deck<C extends Card> implements IViewable, Serializable {

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
     * Put into bucket.
     *
     * @param c
     *            the card that will be put into bucket
     */
    public void putIntoBucket(C c) {
        this.bucket.add(c);
    }

    @Override
    public ViewModel getViewModel() {
        return new DeckViewModel<C>(this);
    }

}

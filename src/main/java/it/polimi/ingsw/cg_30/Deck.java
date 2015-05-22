package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public abstract class Deck implements Serializable {

    private Set<Card> bucket;

    public abstract Collection getCardCollection();

    public void recycle() {
        throw new UnsupportedOperationException();
    }

    public void putIntoBucket(Card c) {
        throw new UnsupportedOperationException();
    }

}

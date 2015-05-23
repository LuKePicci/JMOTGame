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

    // questa implementazione prevede che i tre bucketDeck siano nel
    // matchcontroller
    public void putIntoBucket(Card c) {/*
                                        * if (c.getClass().equals(HatchCard)){
                                        * MatchController
                                        * .getBucketDeckHatch.push(c); } if
                                        * (c.getClass
                                        * ().equals(SectorEventCard)){
                                        * MatchController
                                        * .getBucketDeckSector.push(c); } if
                                        * (c.getClass().equals(ItemCard)){
                                        * MatchController
                                        * .getBucketDeckItem.push(c); }
                                        */
    }
}

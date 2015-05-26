package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class StackedDeckTest {

    @Test
    public void shuffleTest() {
        StackedDeck ex = StackedDeck.newStackedDeckSector();
        ex.shuffle();
        int j = 0;
        for (int i = 0; i < 20; i++) {
            SectorCard c = (SectorCard) ex.pickCard();
            if (c.getEvent().equals(SectorEvent.SILENCE)) {
                j++;
            }
        }
        assertNotSame(0, j);
    }

    @Test
    public void pickCardTest() {
        StackedDeck ex = StackedDeck.newStackedDeckHatch();
        int prevSize = ex.getCardCollection().size();
        HatchCard c = (HatchCard) ex.pickCard();
        assertEquals(HatchChance.LOCKED, c.getChance());
        ex.putIntoBucket(c);
        for (int i = 0; i < prevSize - 1; i++) {
            c = (HatchCard) ex.pickCard();
            ex.putIntoBucket(c);
        }
        assertEquals(0, ex.getCardCollection().size());
        assertEquals(prevSize, ex.bucket.size());
        c = (HatchCard) ex.pickCard();
        assertEquals(prevSize - 1, ex.getCardCollection().size());
        assertEquals(0, ex.bucket.size());
    }

    @Test
    public void pickAndThrowTest() {
        StackedDeck ex = StackedDeck.newStackedDeckHatch();
        assertTrue(ex.bucket.isEmpty());
        HatchCard c = (HatchCard) ex.pickAndThrow();
        assertEquals(HatchChance.LOCKED, c.getChance());
        assertTrue(ex.bucket.contains(c));
    }

    @Test
    public void StackedDeckHatchTest() {
        StackedDeck ex = StackedDeck.newStackedDeckHatch();
        assertEquals(6, ex.getCardCollection().size());
    }

    @Test
    public void StackedDeckSectorTest() {
        StackedDeck ex = StackedDeck.newStackedDeckSector();
        assertEquals(25, ex.getCardCollection().size());
    }

    @Test
    public void StackedDeckItemTest() {
        StackedDeck ex = StackedDeck.newStackedDeckItem();
        assertEquals(12, ex.getCardCollection().size());

    }

    @Test
    public void recycleTest() {
        StackedDeck ex = StackedDeck.newStackedDeckItem();
        int prevSize = ex.getCardCollection().size();
        for (int i = 0; i < prevSize; i++) {
            ex.pickAndThrow();
        }
        assertEquals(prevSize, ex.bucket.size());
        assertEquals(0, ex.getCardCollection().size());
        Set<Card> ck = new HashSet<Card>(ex.bucket);
        ex.recycle();
        assertTrue(ex.getCardCollection().containsAll(ck));
        assertEquals(0, ex.bucket.size());
        assertEquals(prevSize, ex.getCardCollection().size());
    }

}

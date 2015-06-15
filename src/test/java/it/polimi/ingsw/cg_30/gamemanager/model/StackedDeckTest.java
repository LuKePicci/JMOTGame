package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchChance;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class StackedDeckTest {

    @Test
    public void shuffleTest() {
        StackedDeck<SectorCard> ex = StackedDeck.newStackedDeckSector();
        ex.shuffle();
        int j = 0;
        for (int i = 0; i < 20; i++) {
            SectorCard c = ex.pickCard();
            if (c.getEvent().equals(SectorEvent.SILENCE)) {
                j++;
            }
        }
        assertNotSame(0, j);
    }

    @Test
    public void pickCardTest() {
        StackedDeck<HatchCard> ex = StackedDeck.newStackedDeckHatch();
        int prevSize = ex.getCardCollection().size();
        HatchCard c = ex.pickCard();
        assertEquals(HatchChance.LOCKED, c.getChance());
        ex.putIntoBucket(c);
        for (int i = 0; i < prevSize - 1; i++) {
            c = ex.pickCard();
            ex.putIntoBucket(c);
        }
        assertEquals(0, ex.getCardCollection().size());
        assertEquals(prevSize, ex.bucket.size());
        c = ex.pickCard();
        assertEquals(prevSize - 1, ex.getCardCollection().size());
        assertEquals(0, ex.bucket.size());
    }

    @Test
    public void pickAndThrowTest() {
        StackedDeck<HatchCard> ex = StackedDeck.newStackedDeckHatch();
        assertTrue(ex.bucket.isEmpty());
        HatchCard c = ex.pickAndThrow();
        assertEquals(HatchChance.LOCKED, c.getChance());
        assertTrue(ex.bucket.contains(c));
    }

    @Test
    public void stackedDeckHatchTest() {
        StackedDeck<HatchCard> ex = StackedDeck.newStackedDeckHatch();
        assertEquals(6, ex.getCardCollection().size());
    }

    @Test
    public void stackedDeckSectorTest() {
        StackedDeck<SectorCard> ex = StackedDeck.newStackedDeckSector();
        assertEquals(25, ex.getCardCollection().size());
    }

    @Test
    public void stackedDeckItemTest() {
        StackedDeck<ItemCard> ex = StackedDeck.newStackedDeckItem();
        assertEquals(12, ex.getCardCollection().size());
    }

    @Test
    public void stackedDeckPlayerTest() {
        StackedDeck<PlayerCard> ex = StackedDeck.newStackedDeckPlayer();
        assertEquals(8, ex.getCardCollection().size());
    }

    @Test
    public void recycleTest() {
        StackedDeck<ItemCard> ex = StackedDeck.newStackedDeckItem();
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

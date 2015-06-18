package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HatchCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class StackedDeckTest {

    @Test
    public void pickCardTest() {
        StackedDeck<PlayerCard> ex = EftaiosDecks.newStackedDeckPlayer();
        int prevSize = ex.getCardCollection().size();
        PlayerCard c = ex.pickCard();
        assertEquals(PlayerRace.ALIEN, c.getRace());
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
        StackedDeck<PlayerCard> ex = EftaiosDecks.newStackedDeckPlayer();
        assertTrue(ex.bucket.isEmpty());
        PlayerCard c = ex.pickAndThrow();
        assertEquals(PlayerRace.ALIEN, c.getRace());
        assertTrue(ex.bucket.contains(c));
    }

    @Test
    public void stackedDeckHatchTest() {
        StackedDeck<HatchCard> ex = EftaiosDecks.newStackedDeckHatch();
        assertEquals(6, ex.getCardCollection().size());
    }

    @Test
    public void stackedDeckSectorTest() {
        StackedDeck<SectorCard> ex = EftaiosDecks.newStackedDeckSector();
        assertEquals(25, ex.getCardCollection().size());
    }

    @Test
    public void stackedDeckItemTest() {
        StackedDeck<ItemCard> ex = EftaiosDecks.newStackedDeckItem();
        assertEquals(12, ex.getCardCollection().size());
    }

    @Test
    public void stackedDeckPlayerTest() {
        StackedDeck<PlayerCard> ex = EftaiosDecks.newStackedDeckPlayer();
        assertEquals(8, ex.getCardCollection().size());
    }

    @Test
    public void recycleTest() {
        StackedDeck<ItemCard> ex = EftaiosDecks.newStackedDeckItem();
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

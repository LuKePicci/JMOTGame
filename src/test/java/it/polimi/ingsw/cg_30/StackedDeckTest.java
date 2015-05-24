package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class StackedDeckTest {

    @Test
    public void shuffleTest() {
        StackedDeck ex = StackedDeck.newStackedDeckSector();
        ex.shuffle();
        int j = 0;
        for (int i = 0; i < 20; i++) {
            SectorCard c = (SectorCard) ex.pickCard();
            if (c.getEvent().equals(SectorEvent.Silence)) {
                j++;
            }
        }
        assertNotSame(0, j);
    }

    @Test
    public void pickCardTest() {
        StackedDeck ex = StackedDeck.newStackedDeckHatch();
        HatchCard c = (HatchCard) ex.pickCard();
        assertEquals(HatchChance.Locked, c.getChance());
    }

    @Test
    public void pickAndThrowTest() {
        // TO DO
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

}

package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StackedDeckTest {

    @Test
    public void pickCardTest() {
        StackedDeck ex = StackedDeck.StackedDeckHatch();
        HatchCard c = (HatchCard) ex.pickCard();
        assertEquals(HatchChance.Locked, c.getChance());
    }

    @Test
    public void StackedDeckHatchTest() {
        StackedDeck ex = StackedDeck.StackedDeckHatch();
        assertEquals(6, ex.getCardCollection().size());
    }

    @Test
    public void StackedDeckSectorTest() {
        StackedDeck ex = StackedDeck.StackedDeckSector();
        assertEquals(25, ex.getCardCollection().size());
    }

    @Test
    public void StackedDeckItemTest() {
        StackedDeck ex = StackedDeck.StackedDeckItem();
        assertEquals(12, ex.getCardCollection().size());
    }

}

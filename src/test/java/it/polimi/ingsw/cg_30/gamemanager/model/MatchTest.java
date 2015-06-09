package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.gamemanager.model.Match;

import org.junit.Test;

public class MatchTest {

    @Test
    public void MatchGettersTest() {
        Match match = new Match();
        assertEquals(1, match.getTurnCount(), 0);
        assertEquals(12, match.getItemsDeck().getCardCollection().size(), 0);
        assertEquals(6, match.getHatchesDeck().getCardCollection().size(), 0);
        assertEquals(25, match.getSectorsDeck().getCardCollection().size(), 0);
        assertTrue(match.getDeadPlayer().isEmpty());
        assertTrue(match.getRescuedPlayer().isEmpty());
    }

    @Test
    public void incrementTurnCountTest() {
        Match match = new Match();
        assertEquals(1, match.getTurnCount(), 0);
        match.incrementTurnCount();
        assertEquals(2, match.getTurnCount(), 0);
    }

}

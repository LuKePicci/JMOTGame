package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerTest {

    @Test
    public void incrementKillsCountTest() {
        Player ex = new Player();
        assertEquals(0, ex.getKillsCount(), 0);
        ex.incrementKillsCount();
        assertEquals(1, ex.getKillsCount(), 0);
    }

    @Test
    public void gettersTest() {
        Player ex1 = new Player();
        assertEquals(0, ex1.getIndex());
        assertEquals("unknown player", ex1.getName());
        PlayerCard es = new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_CAPTAIN);
        Player ex2 = new Player("tizio", 1, es);
        assertEquals(es, ex2.getIdentity());

    }

}

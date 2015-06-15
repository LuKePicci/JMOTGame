package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCharacter;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;

import org.junit.Test;

public class PlayerTest {

    @Test
    public void incrementKillsCountTest() {
        PlayerCard es = new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_CAPTAIN);
        Player ex = new Player("unknown player", 0, es);
        assertEquals(0, ex.getKillsCount(), 0);
        ex.incrementKillsCount();
        assertEquals(1, ex.getKillsCount(), 0);
    }

    @Test
    public void gettersTest() {
        PlayerCard es0 = new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_CAPTAIN);
        Player ex1 = new Player("unknown player", 0, es0);
        assertEquals(0, ex1.getIndex());
        assertEquals("unknown player", ex1.getName());
        PlayerCard es = new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_FIRST_ALIEN);
        Player ex2 = new Player("tizio", 1, es);
        assertEquals(es, ex2.getIdentity());
    }

}

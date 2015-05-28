package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TurnTest {

    @Test
    public void gettersTest() {
        Player player = new Player();
        Turn turn = new Turn(player);
        assertEquals(false, turn.getCanAttack());
        assertEquals(true, turn.getMustMove());
        assertEquals(1, turn.getMaxSteps(), 0);
        assertEquals(false, turn.getSilenceForced());
        assertEquals(false, turn.getMustDiscard());
        assertEquals(player, turn.getCurrentPlayer());
    }

    @Test
    public void settersTest() {
        Player player = new Player();
        Turn turn = new Turn(player);
        turn.setCanAttack(true);
        assertEquals(true, turn.getCanAttack());
        turn.setSilenceForced(true);
        assertEquals(true, turn.getSilenceForced());
        turn.setMustDiscard(true);
        assertEquals(true, turn.getMustDiscard());
        turn.setMaxSteps(2);
        assertEquals(2, turn.getMaxSteps(), 0);
    }

    @Test
    public void constructorTest() {
        PlayerCard es = new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_FIRST_ALIEN);
        Player player = new Player("nome", 1, es);
        Turn turn1 = new Turn(player);
        assertEquals(true, turn1.getCanAttack());
        assertEquals(2, turn1.getMaxSteps(), 0);
        player.incrementKillsCount();
        Turn turn2 = new Turn(player);
        assertEquals(3, turn2.getMaxSteps(), 0);
    }

}

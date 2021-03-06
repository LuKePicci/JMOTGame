package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCharacter;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;

import org.junit.Test;

public class TurnTest {

    @Test
    public void gettersTest() {
        PlayerCard es = new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_CAPTAIN);
        Player player = new Player("unknown player", 0, es);
        Turn turn = new Turn(player, 1);
        assertEquals(false, turn.getCanAttack());
        assertEquals(true, turn.getMustMove());
        assertEquals(1, turn.getMaxSteps(), 0);
        assertEquals(player, turn.getCurrentPlayer());
        assertEquals(false, turn.getMustDiscard());
        assertEquals(false, turn.getSilenceForced());
        assertEquals(false, turn.getIsSecDangerous());
        assertEquals(0, turn.getHumanKilled(), 0);
        assertEquals(null, turn.getDrawnCard());
    }

    @Test
    public void settersTest() {
        PlayerCard es = new PlayerCard(PlayerRace.HUMAN,
                PlayerCharacter.THE_CAPTAIN);
        Player player = new Player("unknown player", 0, es);
        Turn turn = new Turn(player, 1);
        turn.setCanAttack(true);
        assertEquals(true, turn.getCanAttack());
        turn.setSilenceForced(true);
        assertEquals(true, turn.getSilenceForced());
        turn.setMustDiscard(true);
        assertEquals(true, turn.getMustDiscard());
        turn.setMaxSteps(2);
        assertEquals(2, turn.getMaxSteps(), 0);
        turn.setMustMove(false);
        assertEquals(false, turn.getMustMove());
        turn.setIsSecDangerous(true);
        assertEquals(true, turn.getIsSecDangerous());
        turn.changeHumanKilled(5);
        turn.changeHumanKilled(-2);
        assertEquals(3, turn.getHumanKilled(), 3);
        SectorCard card = new SectorCard(SectorEvent.SILENCE, false);
        turn.setDrawnCard(card);
        assertEquals(card, turn.getDrawnCard());
    }

    @Test
    public void constructorTest() {
        PlayerCard es = new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_FIRST_ALIEN);
        Player player = new Player("nome", 1, es);
        Turn turn1 = new Turn(player, 1);
        assertEquals(false, turn1.getCanAttack());
        assertEquals(2, turn1.getMaxSteps(), 0);
        player.incrementKillsCount();
        Turn turn2 = new Turn(player, 1);
        assertEquals(3, turn2.getMaxSteps(), 0);
    }

}

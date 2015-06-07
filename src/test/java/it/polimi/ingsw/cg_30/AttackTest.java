package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AttackTest {

    /*
     * PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
     * 
     * PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
     * 
     * Player player1=new Player("pl1", 1, alien);
     * 
     * Player player2=new Player("pl2", 2, alien);
     * 
     * Player player3=new Player("pl3", 3, alien);
     * 
     * Player player4=new Player("pl4", 4, alien);
     * 
     * Player player5=new Player("pl5", 5, human);
     * 
     * Player player6=new Player("pl6", 6, human);
     * 
     * Player player7=new Player("pl7", 7, human);
     * 
     * Player player8=new Player("pl8", 8, human);
     */

    @Test
    public void AlienAttacksNoone() {
        // preparo il terreno
        PartyController partyController = new PartyController(null);
        MatchController matchController = new MatchController(partyController);
        matchController.getZoneController().getCurrentZone();
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Player player1 = new Player("pl1", 1, alien);
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        // eseguo l'azione
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 0);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
    }

    // TODO ancora da fare
    @Test
    public void AlienAttacksHuman() {
        // preparo il terreno
        PartyController partyController = new PartyController(null);
        MatchController matchController = new MatchController(partyController);
        matchController.getZoneController().getCurrentZone();
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Player player1 = new Player("pl1", 1, alien);
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        // eseguo l'azione
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 0);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
    }

}

package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    // alieno attacca settore vuoto
    @Test
    public void AlienAttacksNoone() {
        // preparo il terreno
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController) {

                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                Zone zone = new Zone();

                this.zoneController = new ZoneController(zone);

            }
        };
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);

        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Collections.shuffle(players);
        for (Player player : players) {
            player.setIdentity(alien);
        }

        matchController.initMatch(partyController);
        Player player1 = new Player("pl1", 1, alien);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        matchController.processActionRequest(action);
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 0);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
        assertTrue(matchController.getMatch().getDeadPlayer().size() == 0);
    }

    // alieno attacca settore con alieno
    @Test
    public void AlienAttacksAlien() {
        // preparo il terreno
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController) {

                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                Zone zone = new Zone();

                this.zoneController = new ZoneController(zone);

            }
        };
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);

        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        for (Player player : players) {
            player.setIdentity(alien);
        }

        matchController.initMatch(partyController);

        Player player1 = new Player("pl1", 1, alien);
        Player player2 = new Player("pl2", 2, alien);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player2, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        matchController.processActionRequest(action);
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 0);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
        assertTrue(matchController.getTurnController().getTurn().getCanAttack() == false);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player2) == null);
        assertTrue(matchController.getMatch().getDeadPlayer().contains(player2));
        assertTrue(matchController.getMatch().getDeadPlayer().size() == 1);
    }

    // alieno attacca settore con umano indifeso
    @Test
    public void AlienAttacksUndefendedHuman() {
        // preparo il terreno
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController) {

                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                Zone zone = new Zone();

                this.zoneController = new ZoneController(zone);

            }
        };
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);

        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());

        players.get(0).setIdentity(alien);
        players.get(1).setIdentity(human);

        matchController.initMatch(partyController);
        Player player1 = new Player("pl1", 1, alien);
        Player player4 = new Player("pl4", 4, human);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player4, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        matchController.processActionRequest(action);
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 1);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
        assertTrue(matchController.getTurnController().getTurn().getCanAttack() == false);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player4) == null);
        assertTrue(matchController.getMatch().getDeadPlayer().contains(player4));
        assertTrue(matchController.getMatch().getDeadPlayer().size() == 1);
    }

    // alieno attacca settore con umano con carta difesa

    // alieno attacca settore con bersagli multipli

    // umano tenta invano di attaccare prima di muoversi
    // umano tenta invano di attaccare dopo il movimento

}

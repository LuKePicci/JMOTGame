package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.gamemanager.model.Match;
import it.polimi.ingsw.cg_30.gamemanager.model.Party;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.model.Zone;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class AttackTest {

    /*
     * PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
     * 
     * PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
     */

    // alieno attacca settore vuoto
    @Test
    public void AlienAttacksNoone() throws FileNotFoundException,
            URISyntaxException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController) {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                Zone zone = new Zone();
                this.zoneController = new ZoneController(zone);
            }

            @Override
            public void checkEndGame() {
            }
        };

        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(alien);

        matchController.initMatch(partyController);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        Attack atk = new Attack() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        atk.initAction(matchController, action);
        if (atk.isValid())
            atk.processAction();
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 0);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
        assertTrue(matchController.getMatch().getDeadPlayer().size() == 0);
    }

    // alieno attacca settore con alieno
    @Test
    public void AlienAttacksAlien() throws FileNotFoundException,
            URISyntaxException {
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

            @Override
            public void checkEndGame() {
            }

            @Override
            protected void notifyPartyFromPlayer(Player player, String what) {
            }

            @Override
            protected void showCardToParty(Card card) {
            }

            @Override
            protected void updateDeckView(Player player) {
            }

            @Override
            protected void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            protected void updateMapView(Player player) {
            }
        };

        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setIdentity(alien);
        player2.setIdentity(alien);

        matchController.initMatch(partyController);

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
        Attack atk = new Attack() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        atk.initAction(matchController, action);
        if (atk.isValid())
            atk.processAction();
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
    public void AlienAttacksUndefendedHuman() throws FileNotFoundException,
            URISyntaxException {
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

            @Override
            public void checkEndGame() {
            }

            @Override
            protected void notifyPartyFromPlayer(Player player, String what) {
            }

            @Override
            protected void showCardToParty(Card card) {
            }

            @Override
            protected void updateDeckView(Player player) {
            }

            @Override
            protected void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            protected void updateMapView(Player player) {
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
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setIdentity(alien);
        player2.setIdentity(human);

        matchController.initMatch(partyController);

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
        Attack atk = new Attack() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        atk.initAction(matchController, action);
        if (atk.isValid())
            atk.processAction();
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 1);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
        assertTrue(matchController.getTurnController().getTurn().getCanAttack() == false);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player2) == null);
        assertTrue(matchController.getMatch().getDeadPlayer().contains(player2));
        assertTrue(matchController.getMatch().getDeadPlayer().size() == 1);
    }

    // alieno attacca settore con umano con carta difesa
    @Test
    public void AlienAttacksDefendedHuman() throws FileNotFoundException,
            URISyntaxException {
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

            @Override
            public void checkEndGame() {
            }

            @Override
            protected void notifyPartyFromPlayer(Player player, String what) {
            }

            @Override
            protected void showCardToParty(Card card) {
            }

            @Override
            protected void updateDeckView(Player player) {
            }

            @Override
            protected void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            protected void updateMapView(Player player) {
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
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setIdentity(alien);
        player2.setIdentity(human);
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        player2.getItemsDeck().getCards().add(defenseCard);

        matchController.initMatch(partyController);

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
        Attack atk = new Attack() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        atk.initAction(matchController, action);
        if (atk.isValid())
            atk.processAction();
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 0);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player2).equals(sec));
        assertFalse(matchController.getMatch().getDeadPlayer()
                .contains(player2));
        assertTrue(matchController.getMatch().getDeadPlayer().size() == 0);
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(defenseCard));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket().size() == 1);
        assertFalse(player1.getItemsDeck().getCards().contains(defenseCard));

    }

    // alieno attacca settore con bersagli multipli
    @Test
    public void AlienAttacksMultipleTargets() throws FileNotFoundException,
            URISyntaxException {
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

            @Override
            public void checkEndGame() {
            }

            @Override
            protected void notifyPartyFromPlayer(Player player, String what) {
            }

            @Override
            protected void showCardToParty(Card card) {
            }

            @Override
            protected void updateDeckView(Player player) {
            }

            @Override
            protected void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            protected void updateMapView(Player player) {
            }
        };

        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        party.addToParty(UUID.randomUUID(), "player3");
        party.addToParty(UUID.randomUUID(), "player4");
        party.addToParty(UUID.randomUUID(), "player5");
        party.addToParty(UUID.randomUUID(), "player6");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        player1.setIdentity(alien);// attaccante
        player2.setIdentity(alien);// alieno in loco
        player3.setIdentity(alien);// alieno difeso
        player4.setIdentity(human);// umano indifeso
        player5.setIdentity(human);// umano altrove
        player6.setIdentity(human);// umano difeso
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        player3.getItemsDeck().getCards().add(defenseCard);
        player6.getItemsDeck().getCards().add(defenseCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        HexPoint point2 = new HexPoint(2, 3);
        Sector sec2 = new Sector(null, point2);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player2, sec);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player3, sec);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player4, sec);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player5, sec2);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player6, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        Attack atk = new Attack() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        atk.initAction(matchController, action);
        if (atk.isValid())
            atk.processAction();
        // verifico gli esiti
        assertTrue(player1.getKillsCount() == 1);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec));
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player2) == null);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player3) == null);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player4) == null);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player5).equals(sec2));
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player6).equals(sec));
        assertFalse(matchController.getMatch().getDeadPlayer()
                .contains(player1));
        assertTrue(matchController.getMatch().getDeadPlayer().contains(player2));
        assertTrue(matchController.getMatch().getDeadPlayer().contains(player3));
        assertTrue(matchController.getMatch().getDeadPlayer().contains(player4));
        assertFalse(matchController.getMatch().getDeadPlayer()
                .contains(player5));
        assertFalse(matchController.getMatch().getDeadPlayer()
                .contains(player6));
        assertTrue(matchController.getMatch().getDeadPlayer().size() == 3);
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(defenseCard));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket().size() == 1);
        assertTrue(player3.getItemsDeck().getCards().contains(defenseCard));
        assertFalse(player6.getItemsDeck().getCards().contains(defenseCard));
    }

    // alieno tenta invano di attaccare prima di muoversi
    @Test
    public void AlienAttacksBeforeMoving() throws FileNotFoundException,
            URISyntaxException {
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
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(alien);

        matchController.initMatch(partyController);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        Attack atk = new Attack();
        atk.initAction(matchController, action);
        // verifico gli esiti
        assertFalse(atk.isValid());
    }

    // umano tenta invano di attaccare prima di muoversi
    @Test
    public void HumanAttacksBeforeMoving() throws FileNotFoundException,
            URISyntaxException {
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        Attack atk = new Attack();
        atk.initAction(matchController, action);
        // verifico gli esiti
        assertFalse(atk.isValid());
    }

    // umano tenta invano di attaccare dopo il movimento
    @Test
    public void HumanAttacksAfterMoving() throws FileNotFoundException,
            URISyntaxException {
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        HexPoint point = new HexPoint(1, 1);
        Sector sec = new Sector(null, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        ActionRequest action = new ActionRequest(ActionType.ATTACK, null, null);
        // eseguo l'azione
        Attack atk = new Attack();
        atk.initAction(matchController, action);
        // verifico gli esiti
        assertFalse(atk.isValid());
    }

}

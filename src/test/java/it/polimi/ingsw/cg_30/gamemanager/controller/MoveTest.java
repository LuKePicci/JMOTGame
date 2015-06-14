package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.model.Match;
import it.polimi.ingsw.cg_30.gamemanager.model.Party;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class MoveTest {

    // non posso muovermi
    @Test
    public void cantMove() throws FileNotFoundException, URISyntaxException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(alien);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        HexPoint point = HexPoint.fromOffset(11, 4);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        // verifico esito
        assertFalse(mo.isValid());
    }

    // obiettivo fuori portata
    @Test
    public void tooFarTarget() throws FileNotFoundException, URISyntaxException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(alien);

        matchController.initMatch(partyController);
        List<Player> playersList = new ArrayList<Player>();
        playersList.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint point = HexPoint.fromOffset(12, 3);
        // matchController.getZoneController().getCurrentZone().movePlayer(player1,
        // where);
        matchController.getZoneController().placePlayers(playersList);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        // verifico esito
        assertFalse(mo.isValid());
    }

    // alieno non va su scialuppa
    @Test
    public void alienOnHatch() throws FileNotFoundException, URISyntaxException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(alien);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint startPoint = HexPoint.fromOffset(15, 1);
        Sector sec = new Sector(SectorType.DANGEROUS, startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(15, 0);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        // verifico esito
        assertFalse(mo.isValid());
    }

    // umano va su scialuppa (4volte per coprire entrambi i codici)
    @Test
    public void humanOnHatch() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint startPoint = HexPoint.fromOffset(15, 1);
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getMap().get(startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(15, 0);
        Sector secFinal = matchController.getZoneController().getCurrentZone()
                .getMap().get(point);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }

            @Override
            protected void showCardToParty(Card card) {
            }

            @Override
            protected void notifyInChatByServer(String what) {
            }

            @Override
            protected void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            protected void updateMapToPartyPlayers() {
            }

        };
        mo.initAction(matchController, action);
        assertTrue(mo.isValid());
        if (mo.isValid()) {
            mo.processAction();
        }
        // verifico esito
        assertTrue(matchController.getMatch().getHatchesDeck().getBucket()
                .size() == 1);
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(secFinal));
    }

    // non vado su settore partenza umani
    @Test
    public void moveOnHumanStartSector() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint startPoint = HexPoint.fromOffset(11, 8);
        Sector sec = new Sector(SectorType.DANGEROUS, startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(11, 7);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        // verifico esito
        assertFalse(mo.isValid());
    }

    // non vado su settore partenza alieni
    @Test
    public void moveOnAlienStartSector() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint startPoint = HexPoint.fromOffset(11, 4);
        Sector sec = new Sector(SectorType.DANGEROUS, startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(11, 5);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        // verifico esito
        assertFalse(mo.isValid());
    }

    // alieno va su dangerous
    @Test
    public void alienOnDangerous() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(alien);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint startPoint = HexPoint.fromOffset(11, 5);
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getMap().get(startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(11, 4);
        Sector secFinal = matchController.getZoneController().getCurrentZone()
                .getMap().get(point);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        assertTrue(mo.isValid());
        if (mo.isValid()) {
            mo.processAction();
        }
        // verifico esito
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(secFinal));
        assertTrue(matchController.getTurnController().getTurn()
                .getIsSecDangerous() == true);
    }

    // umano senza aver usato sedativi va su dangerous
    @Test
    public void humanWithoutSedativesOnDangerous()
            throws FileNotFoundException, URISyntaxException,
            DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint startPoint = HexPoint.fromOffset(11, 7);
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getMap().get(startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(11, 8);
        Sector secFinal = matchController.getZoneController().getCurrentZone()
                .getMap().get(point);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }

            @Override
            protected void updateMapView() throws DisconnectedException {
            }
        };
        mo.initAction(matchController, action);
        assertTrue(mo.isValid());
        if (mo.isValid()) {
            mo.forcedDraw = new DrawCard() {
                @Override
                public void processAction() {
                    // see you later
                }
            };
            mo.processAction();
        }
        // verifico esito
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(secFinal));
        assertTrue(matchController.getTurnController().getTurn()
                .getIsSecDangerous() == true);
    }

    // umano che ha usato sedativi va su dangerous
    @Test
    public void humanWithSedativesOnDangerous() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setSilenceForced(true);
        HexPoint startPoint = HexPoint.fromOffset(11, 7);
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getMap().get(startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(11, 8);
        Sector secFinal = matchController.getZoneController().getCurrentZone()
                .getMap().get(point);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        assertTrue(mo.isValid());
        if (mo.isValid()) {
            mo.processAction();
        }
        // verifico esito
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(secFinal));
        assertTrue(matchController.getTurnController().getTurn()
                .getIsSecDangerous() == false);
    }

    // settore sicuro
    @Test
    public void secureSector() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);

        matchController.initMatch(partyController);
        players.add(player1);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint startPoint = HexPoint.fromOffset(10, 12);
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getMap().get(startPoint);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        HexPoint point = HexPoint.fromOffset(10, 13);
        Sector secFinal = matchController.getZoneController().getCurrentZone()
                .getMap().get(point);
        ActionRequest action = new ActionRequest(ActionType.MOVE, point, null);
        // eseguo l'azione
        Move mo = new Move() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }

            @Override
            protected void updateDeckView() {
            }
        };
        mo.initAction(matchController, action);
        assertTrue(mo.isValid());
        if (mo.isValid()) {
            mo.processAction();
        }
        // verifico esito
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(secFinal));
        assertTrue(matchController.getTurnController().getTurn()
                .getIsSecDangerous() == false);
    }

}

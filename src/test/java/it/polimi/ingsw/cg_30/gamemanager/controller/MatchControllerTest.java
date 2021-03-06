package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.gamemanager.model.Match;
import it.polimi.ingsw.cg_30.gamemanager.model.Party;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

public class MatchControllerTest {

    // tutti gli umani sono morti
    @Test
    public void allHumansAreDead() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        // preparo il terreno
        final Set<Player> vincitori = new HashSet<Player>();
        final Set<Player> perdenti = new HashSet<Player>();
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this);
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
            }

            @Override
            protected void sayYouWin(Set<Player> winners) {
                vincitori.addAll(winners);
            }

            @Override
            protected void sayYouLose(Set<Player> losers) {
                perdenti.addAll(losers);
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
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        matchController.getMatch().getDeadPlayer().add(player3);
        matchController.getMatch().getDeadPlayer().add(player4);
        matchController.getMatch().getDeadPlayer().add(player5);
        matchController.getMatch().getDeadPlayer().add(player6);
        matchController.getMatch().getDeadPlayer().add(player7);
        // eseguo l'azione
        matchController.checkEndGame();
        // verifico gli esiti
        assertTrue(perdenti.size() == 3);
        assertTrue(vincitori.size() == 4);
        assertTrue(perdenti.contains(player5));
        assertTrue(perdenti.contains(player6));
        assertTrue(perdenti.contains(player7));
        assertTrue(vincitori.contains(player1));
        assertTrue(vincitori.contains(player2));
        assertTrue(vincitori.contains(player3));
        assertTrue(vincitori.contains(player4));
    }

    // tutti gli umani sono fuggiti
    @Test
    public void allHumansAreEscaped() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        final Set<Player> vincitori = new HashSet<Player>();
        final Set<Player> perdenti = new HashSet<Player>();
        // preparo il terreno
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this);
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
            }

            @Override
            protected void sayYouWin(Set<Player> winners) {
                vincitori.addAll(winners);
            }

            @Override
            protected void sayYouLose(Set<Player> losers) {
                perdenti.addAll(losers);
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
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        matchController.getMatch().getDeadPlayer().add(player3);
        matchController.getMatch().getDeadPlayer().add(player4);
        matchController.getMatch().getRescuedPlayer().add(player5);
        matchController.getMatch().getRescuedPlayer().add(player6);
        matchController.getMatch().getRescuedPlayer().add(player7);
        // eseguo l'azione
        matchController.checkEndGame();
        // verifico gli esiti
        assertTrue(perdenti.size() == 4);
        assertTrue(vincitori.size() == 3);
        assertTrue(perdenti.contains(player1));
        assertTrue(perdenti.contains(player2));
        assertTrue(perdenti.contains(player3));
        assertTrue(perdenti.contains(player4));
        assertTrue(vincitori.contains(player5));
        assertTrue(vincitori.contains(player6));
        assertTrue(vincitori.contains(player7));
    }

    // gli alieni uccidono l'ultimo umano
    @Test
    public void lastHumanKilled() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        // preparo il terreno
        final Set<Player> vincitori = new HashSet<Player>();
        final Set<Player> perdenti = new HashSet<Player>();
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this);
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
            }

            @Override
            protected void sayYouWin(Set<Player> winners) {
                vincitori.addAll(winners);
            }

            @Override
            protected void sayYouLose(Set<Player> losers) {
                perdenti.addAll(losers);
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
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        matchController.getMatch().getDeadPlayer().add(player3);
        matchController.getMatch().getDeadPlayer().add(player4);
        matchController.getMatch().getRescuedPlayer().add(player5);
        matchController.getMatch().getRescuedPlayer().add(player6);
        matchController.getMatch().getDeadPlayer().add(player7);
        matchController.getTurnController().getTurn().changeHumanKilled(1);
        // eseguo l'azione
        matchController.checkEndGame();
        // verifico gli esiti
        assertTrue(perdenti.size() == 1);
        assertTrue(vincitori.size() == 6);
        assertTrue(perdenti.contains(player7));
        assertTrue(vincitori.contains(player1));
        assertTrue(vincitori.contains(player2));
        assertTrue(vincitori.contains(player3));
        assertTrue(vincitori.contains(player4));
        assertTrue(vincitori.contains(player5));
        assertTrue(vincitori.contains(player6));
    }

    // l'ultimo umano riesce a fuggire
    @Test
    public void lastHumanEscaped() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        // preparo il terreno
        final Set<Player> vincitori = new HashSet<Player>();
        final Set<Player> perdenti = new HashSet<Player>();
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this);
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
            }

            @Override
            protected void sayYouWin(Set<Player> winners) {
                vincitori.addAll(winners);
            }

            @Override
            protected void sayYouLose(Set<Player> losers) {
                perdenti.addAll(losers);
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
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        matchController.getMatch().getDeadPlayer().add(player3);
        matchController.getMatch().getDeadPlayer().add(player4);
        matchController.getMatch().getDeadPlayer().add(player5);
        matchController.getMatch().getRescuedPlayer().add(player6);
        matchController.getMatch().getRescuedPlayer().add(player7);
        // eseguo l'azione
        matchController.checkEndGame();
        // verifico gli esiti
        assertTrue(perdenti.size() == 5);
        assertTrue(vincitori.size() == 2);
        assertTrue(perdenti.contains(player1));
        assertTrue(perdenti.contains(player2));
        assertTrue(perdenti.contains(player3));
        assertTrue(perdenti.contains(player4));
        assertTrue(perdenti.contains(player5));
        assertTrue(vincitori.contains(player6));
        assertTrue(vincitori.contains(player7));
    }

    // non ci sono più scialuppe
    @Test
    public void noMoreHatchesAvailable() throws FileNotFoundException,
            URISyntaxException, NotAnHatchException, DisconnectedException {
        // preparo il terreno
        final Set<Player> vincitori = new HashSet<Player>();
        final Set<Player> perdenti = new HashSet<Player>();
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this);
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
            }

            @Override
            protected void sayYouWin(Set<Player> winners) {
                vincitori.addAll(winners);
            }

            @Override
            protected void sayYouLose(Set<Player> losers) {
                perdenti.addAll(losers);
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
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        matchController.getMatch().getDeadPlayer().add(player3);
        matchController.getMatch().getDeadPlayer().add(player4);
        matchController.getMatch().getDeadPlayer().add(player5);
        matchController.getMatch().getRescuedPlayer().add(player6);

        Set<HexPoint> hatchesLocation = new HashSet<HexPoint>();
        for (Sector s : matchController.getZoneController().getCurrentZone()
                .getMap().values()) {
            switch (s.getType()) {
                case ESCAPE_HATCH:
                    hatchesLocation.add(s.getPoint());
                    break;
                default:
                    continue;
            }
        }
        for (HexPoint loc : hatchesLocation) {
            matchController.getZoneController().lockHatch(loc);
        }
        // eseguo l'azione
        matchController.checkEndGame();
        // verifico gli esiti
        assertTrue(perdenti.size() == 2);
        assertTrue(vincitori.size() == 5);
        assertTrue(perdenti.contains(player5));
        assertTrue(perdenti.contains(player7));
        assertTrue(vincitori.contains(player1));
        assertTrue(vincitori.contains(player2));
        assertTrue(vincitori.contains(player3));
        assertTrue(vincitori.contains(player4));
        assertTrue(vincitori.contains(player6));
    }

}

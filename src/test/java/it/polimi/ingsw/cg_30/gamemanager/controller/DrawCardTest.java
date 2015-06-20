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

public class DrawCardTest {

    // settore non pericoloso e isSecDangerous a false
    @Test
    public void notDangerousSector() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {

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
            protected void updateDeckView(Player player) {
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
        HexPoint point = HexPoint.fromOffset(0, 7);
        Sector sec = new Sector(SectorType.SECURE, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getTurnController().getTurn().setIsSecDangerous(false);
        ActionRequest action = new ActionRequest(ActionType.DRAW_CARD, null,
                null);
        // eseguo l'azione
        DrawCard dc = new DrawCard() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        dc.initAction(matchController, action);
        for (int i = 0; i < 25; i++) {
            dc.processAction();
        }
        // verifico gli esiti
        assertFalse(dc.isValid());
    }

    // settore non pericoloso ma isSecDangerous è true
    @Test
    public void notDangerousSectorButFlagIsDanTrue()
            throws FileNotFoundException, URISyntaxException,
            DisconnectedException {

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
            protected void updateDeckView(Player player) {
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
        HexPoint point = HexPoint.fromOffset(0, 7);
        Sector sec = new Sector(SectorType.SECURE, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getTurnController().getTurn().setIsSecDangerous(true);
        ActionRequest action = new ActionRequest(ActionType.DRAW_CARD, null,
                null);
        // eseguo l'azione
        DrawCard dc = new DrawCard() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        dc.initAction(matchController, action);
        for (int i = 0; i < 25; i++) {
            dc.processAction();
        }
        // verifico gli esiti
        assertFalse(dc.isValid());
    }

    // settore pericoloso ma isSecDangerous è false
    @Test
    public void drawCardTest() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {

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
            protected void updateDeckView(Player player) {
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
        HexPoint point = HexPoint.fromOffset(0, 2);
        Sector sec = new Sector(SectorType.DANGEROUS, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getTurnController().getTurn().setIsSecDangerous(false);
        ActionRequest action = new ActionRequest(ActionType.DRAW_CARD, null,
                null);
        // eseguo l'azione
        DrawCard dc = new DrawCard() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        dc.initAction(matchController, action);
        for (int i = 0; i < 25; i++) {
            dc.processAction();
        }
        // verifico gli esiti
        assertFalse(dc.isValid());
    }

    // procedo normalmente (isVaid() ritorna true)
    @Test
    public void allGood() throws FileNotFoundException, URISyntaxException,
            DisconnectedException {
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
            protected void updateDeckView(Player player) {
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
        HexPoint point = HexPoint.fromOffset(0, 2);
        Sector sec = new Sector(SectorType.DANGEROUS, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getTurnController().getTurn().setIsSecDangerous(true);
        ActionRequest action = new ActionRequest(ActionType.DRAW_CARD, null,
                null);
        // eseguo l'azione
        DrawCard dc = new DrawCard() {
            @Override
            protected void showCardToCurrentPlayer(Card card) {
            }

            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        dc.initAction(matchController, action);
        dc.isValid();
        assertTrue(dc.isValid());
        for (int i = 0; i < 25; i++) {
            dc.processAction();
        }
        assertTrue(player1.getItemsDeck().getCards().size() == 4);
    }
}

package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;
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

public class NoiseAnyTest {

    // settore non sulla mappa
    @Test
    public void wrongSector() throws FileNotFoundException, URISyntaxException,
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
        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        SectorCard card = new SectorCard(SectorEvent.NOISE_ANY, false);
        matchController.getTurnController().getTurn().setDrawnCard(card);
        HexPoint point = HexPoint.fromOffset(0, 0);
        ActionRequest action = new ActionRequest(ActionType.NOISE_ANY, point,
                null);
        // eseguo l'azione
        NoiseAny na = new NoiseAny() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }
        };
        na.initAction(matchController, action);
        if (na.isValid())
            na.processAction();
        // verifico gli esiti
        assertFalse(na.isValid());
    }

    // non ha pescato una carta
    @Test
    public void notCard() throws FileNotFoundException, URISyntaxException,
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
        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        List<HexPoint> secLoc = new ArrayList<HexPoint>();
        for (Sector s : matchController.getZoneController().getCurrentZone()
                .getMap().values()) {
            switch (s.getType()) {
                case DANGEROUS:
                    secLoc.add(s.getPoint());
                    break;
                default:
                    continue;
            }
        }
        HexPoint point = secLoc.get(0);
        ActionRequest action = new ActionRequest(ActionType.NOISE_ANY, point,
                null);
        // eseguo l'azione
        NoiseAny na = new NoiseAny() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }
        };
        na.initAction(matchController, action);
        if (na.isValid())
            na.processAction();
        // verifico gli esiti
        assertFalse(na.isValid());
    }

    // tutto ok
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
            protected void sendTurnViewModel() {
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
        Turn turn = new Turn(player1, matchController.getMatch().getTurnCount());
        matchController.getTurnController().setTurn(turn);
        SectorCard card = new SectorCard(SectorEvent.NOISE_ANY, false);
        matchController.getTurnController().getTurn().setDrawnCard(card);

        List<HexPoint> secLoc = new ArrayList<HexPoint>();
        for (Sector s : matchController.getZoneController().getCurrentZone()
                .getMap().values()) {
            switch (s.getType()) {
                case DANGEROUS:
                    secLoc.add(s.getPoint());
                    break;
                default:
                    continue;
            }
        }
        HexPoint point = secLoc.get(0);
        ActionRequest action = new ActionRequest(ActionType.NOISE_ANY, point,
                null);
        // eseguo l'azione
        NoiseAny na = new NoiseAny() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }
        };
        na.initAction(matchController, action);
        if (na.isValid())
            na.processAction();
        // verifico gli esiti
        assertTrue(matchController.getTurnController().getTurn().getDrawnCard() == null);
    }

}

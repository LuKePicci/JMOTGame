package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorEvent;
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

public class TurnOverTest {

    // subito dopo aver iniziato il turno
    @Test
    public void beginningOfTurn() throws FileNotFoundException,
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
        ActionRequest action = new ActionRequest(ActionType.TURN_OVER, null,
                null);
        // eseguo l'azione
        TurnOver to = new TurnOver();
        to.initAction(matchController, action);
        // verifico gli esiti
        assertFalse(to.isValid());
    }

    // subito dopo lo spostamento (su settore non pericoloso)
    @Test
    public void afterSafeMovement() throws FileNotFoundException,
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
        ActionRequest action = new ActionRequest(ActionType.TURN_OVER, null,
                null);
        matchController.getTurnController().getTurn().setMustMove();
        // eseguo l'azione
        TurnOver to = new TurnOver() {
            @Override
            public void processAction() {
            }
        };
        to.initAction(matchController, action);

        if (to.isValid())
            to.processAction();
        // verifico gli esiti
        assertTrue(to.isValid());
    }

    // subito dopo aver attaccato
    @Test
    public void afterAttack() throws FileNotFoundException, URISyntaxException {
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
        ActionRequest action = new ActionRequest(ActionType.TURN_OVER, null,
                null);
        matchController.getTurnController().getTurn().setMustMove();
        matchController.getTurnController().getTurn().setCanAttack(false);
        // eseguo l'azione
        TurnOver to = new TurnOver();
        to.initAction(matchController, action);
        // verifico gli esiti
        assertTrue(to.isValid());
    }

    // alieno che si è mosso ma non ha attacato nè pescato
    @Test
    public void afterMovingToDangerousSector() throws FileNotFoundException,
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
        ActionRequest action = new ActionRequest(ActionType.TURN_OVER, null,
                null);
        matchController.getTurnController().getTurn().setMustMove();
        matchController.getTurnController().getTurn().setIsSecDangerous(true);
        // eseguo l'azione
        TurnOver to = new TurnOver();
        to.initAction(matchController, action);
        // verifico gli esiti
        assertFalse(to.isValid());
    }

    // subito dopo aver pescato ma senza aver risolto l'effetto (noiseAny)
    @Test
    public void notNoiseYet() throws FileNotFoundException, URISyntaxException {
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
        ActionRequest action = new ActionRequest(ActionType.TURN_OVER, null,
                null);
        matchController.getTurnController().getTurn().setMustMove();
        matchController.getTurnController().getTurn().setIsSecDangerous(false);
        SectorCard drawnCard = new SectorCard(SectorEvent.NOISE_ANY, false);
        matchController.getTurnController().getTurn().setDrawnCard(drawnCard);
        // eseguo l'azione
        TurnOver to = new TurnOver();
        to.initAction(matchController, action);
        // verifico gli esiti
        assertFalse(to.isValid());
    }

    // subito dopo aver pescato e aver risolto l'effetto (noiseAny)
    @Test
    public void noiseDone() throws FileNotFoundException, URISyntaxException {
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
        ActionRequest action = new ActionRequest(ActionType.TURN_OVER, null,
                null);
        matchController.getTurnController().getTurn().setMustMove();
        matchController.getTurnController().getTurn().setIsSecDangerous(false);
        matchController.getTurnController().getTurn().setDrawnCard(null);
        // eseguo l'azione
        TurnOver to = new TurnOver();
        to.initAction(matchController, action);
        // verifico gli esiti
        assertTrue(to.isValid());
    }

    // avendo carte da scartare
    @Test
    public void haveToDiscard() throws FileNotFoundException,
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
        ActionRequest action = new ActionRequest(ActionType.TURN_OVER, null,
                null);
        matchController.getTurnController().getTurn().setMustMove();
        matchController.getTurnController().getTurn().setMustDiscard(true);
        // eseguo l'azione
        TurnOver to = new TurnOver();
        to.initAction(matchController, action);
        // verifico gli esiti
        assertFalse(to.isValid());
    }

}

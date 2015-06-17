package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Card;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorHighlight;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
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

public class UseCardTest {

    // alieno cerca di usare una carta
    @Test
    public void alienTest() throws FileNotFoundException, URISyntaxException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
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
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.ATTACK);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.ATTACK);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        // verifico esito
        assertFalse(uc.isValid());
    }

    // umano che usa carta che non ha
    @Test
    public void unknownCardTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
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
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.ATTACK);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        // verifico esito
        assertFalse(uc.isValid());
    }

    // umano che usa adrenaline dopo il movimento
    @Test
    public void adrenalinePreMovementTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
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
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.ADRENALINE);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.ADRENALINE);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        // verifico esito
        assertFalse(uc.isValid());
    }

    // umano che usa sedatives dopo il movimento
    @Test
    public void sedativesPreMovementTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
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
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustMove();
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.SEDATIVES);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        // verifico esito
        assertFalse(uc.isValid());
    }

    // umano che usa difesa
    @Test
    public void defenseCardTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
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
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.DEFENSE);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        // verifico esito
        assertFalse(uc.isValid());
    }

    // umano che usa adrenaline prima del movimento (verifica attr. turn)
    @Test
    public void adrenalinePostMovementTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
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
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.ADRENALINE);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.ADRENALINE);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        assertTrue(uc.isValid());
        uc.processAction();
        // verifico esito
        assertTrue(matchController.getTurnController().getTurn().getMaxSteps() == 2);
        assertFalse(player1.getItemsDeck().getCards().contains(attackCard));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(attackCard));
    }

    // umano che usa sedatives prima del movimento (verifica attr. turn)
    @Test
    public void sedativesPostMovementTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
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
        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.SEDATIVES);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        assertTrue(uc.isValid());
        uc.processAction();
        // verifico esito
        assertTrue(matchController.getTurnController().getTurn()
                .getSilenceForced() == true);
        assertFalse(player1.getItemsDeck().getCards().contains(attackCard));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(attackCard));
    }

    // umano che usa attack
    @Test
    public void attackTest() throws FileNotFoundException, URISyntaxException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
            }
        };

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");

        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setIdentity(human);
        player2.setIdentity(human);

        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.ATTACK);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint point = HexPoint.fromOffset(1, 10);
        Sector sec = new Sector(SectorType.SECURE, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player2, sec);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.ATTACK);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        assertTrue(uc.isValid());
        uc.forcedAttack = new Attack() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }

            @Override
            protected void notifyCurrentPlayerByServer(String what) {
            }
        };
        uc.processAction();
        // verifico esito
        assertFalse(player1.getItemsDeck().getCards().contains(spotlightCard));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(spotlightCard));
        assertTrue(matchController.getMatch().getDeadPlayer().contains(player2));
    }

    // umano che usa teleport (verifica esito spostamento)
    @Test
    public void teleportTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
            }
        };

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");

        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setIdentity(human);
        player2.setIdentity(human);

        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.TELEPORT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint point = HexPoint.fromOffset(1, 10);
        Sector sec = new Sector(SectorType.SECURE, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, null,
                Item.TELEPORT);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        assertTrue(uc.isValid());
        uc.processAction();
        // verifico esito
        Sector sec2 = matchController.getZoneController().getHumansStart();
        assertFalse(player1.getItemsDeck().getCards().contains(spotlightCard));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(spotlightCard));
        assertTrue(matchController.getZoneController().getCurrentZone()
                .getCell(player1).equals(sec2));
    }

    // umano che usa spotlight su un settore inesistente
    @Test
    public void spotlightNotExistTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
            }
        };

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");

        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setIdentity(human);
        player2.setIdentity(human);

        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        HexPoint notExist = HexPoint.fromOffset(999, 999);
        HexPoint point = HexPoint.fromOffset(1, 10);
        Sector sec = new Sector(SectorType.SECURE, point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, notExist,
                Item.SPOTLIGHT);
        // eseguo l'azione
        UseCard uc = new UseCard() {
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
        uc.initAction(matchController, action);
        // verifico esito
        assertFalse(uc.isValid());
    }

    // umano che usa spotlight (con verifica giocatori ritornati)
    @Test
    public void spotlightTest() throws FileNotFoundException,
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
            public void notifyAPlayerAbout(Player player, String about) {
            }

            @Override
            public void showCardToParty(Card card) {
            }

            @Override
            public void updateDeckView(Player player) {
            }

            @Override
            public void sendMapVariationToPlayer(Player player, Sector sec,
                    SectorHighlight highlight) {
            }

            @Override
            public void sendViewModelToAPlayer(Player p, ViewModel content) {
            }
        };

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");

        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        player1.setIdentity(human);
        player2.setIdentity(human);

        ItemCard defenseCard = new ItemCard(Item.DEFENSE);
        ItemCard attackCard = new ItemCard(Item.SEDATIVES);
        ItemCard spotlightCard = new ItemCard(Item.SPOTLIGHT);
        player1.getItemsDeck().getCards().add(defenseCard);
        player1.getItemsDeck().getCards().add(attackCard);
        player1.getItemsDeck().getCards().add(spotlightCard);

        matchController.initMatch(partyController);

        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);

        HexPoint point = HexPoint.fromOffset(1, 10);
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getMap().get(point);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player1, sec);

        HexPoint point2 = HexPoint.fromOffset(1, 11);
        Sector sec2 = matchController.getZoneController().getCurrentZone()
                .getMap().get(point2);
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player2, sec2);

        HexPoint point3 = HexPoint.fromOffset(1, 11);
        ActionRequest action = new ActionRequest(ActionType.USE_ITEM, point3,
                Item.SPOTLIGHT);
        // eseguo l'azione
        final Set<String> watched = new HashSet<String>();

        UseCard uc = new UseCard() {

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
            protected void notifyInChatByServer(String what) {
                watched.add(what);
            }
        };
        uc.initAction(matchController, action);
        assertTrue(uc.isValid());
        uc.processAction();
        // verifico esito
        assertFalse(player1.getItemsDeck().getCards().contains(spotlightCard));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(spotlightCard));
        assertTrue(watched.size() == 2);
        boolean result = false;
        for (String s : watched)
            if (s.contains("player1"))
                result = true;
        assertTrue(result);
        result = false;

        for (String s : watched)
            if (s.contains("player2"))
                result = true;
        assertTrue(result);

    }

}

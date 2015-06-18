package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
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

public class DiscardCardTest {

    // non ha più di tre carte
    @Test
    public void cantDiscard() throws FileNotFoundException, URISyntaxException,
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
        ActionRequest action = new ActionRequest(ActionType.DISCARD_CARD, null,
                null);
        // eseguo l'azione
        DiscardCard dis = new DiscardCard() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }
        };
        dis.initAction(matchController, action);
        if (dis.isValid())
            dis.processAction();
        // verifico gli esiti
        assertFalse(dis.isValid());
    }

    // tenta di scartare una carta che non ha
    @Test
    public void discardCardNotOwn() throws FileNotFoundException,
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
        };

        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        player1.setIdentity(human);
        ItemCard card1 = new ItemCard(Item.ADRENALINE);
        ItemCard card2 = new ItemCard(Item.DEFENSE);
        ItemCard card3 = new ItemCard(Item.ATTACK);
        ItemCard card4 = new ItemCard(Item.SEDATIVES);
        player1.getItemsDeck().getCards().add(card1);
        player1.getItemsDeck().getCards().add(card2);
        player1.getItemsDeck().getCards().add(card3);
        player1.getItemsDeck().getCards().add(card4);

        matchController.initMatch(partyController);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustDiscard(true);
        ActionRequest action = new ActionRequest(ActionType.DISCARD_CARD, null,
                Item.SPOTLIGHT);
        // eseguo l'azione
        DiscardCard dis = new DiscardCard() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }
        };
        dis.initAction(matchController, action);
        if (dis.isValid())
            dis.processAction();
        // verifico gli esiti
        assertFalse(dis.isValid());
    }

    // scarta correttamente
    @Test
    public void discardCardProperly() throws FileNotFoundException,
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
            protected void updateDeckView(Player player) {
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
        ItemCard card1 = new ItemCard(Item.ADRENALINE);
        ItemCard card2 = new ItemCard(Item.DEFENSE);
        ItemCard card3 = new ItemCard(Item.ATTACK);
        ItemCard card4 = new ItemCard(Item.SEDATIVES);
        player1.getItemsDeck().getCards().add(card1);
        player1.getItemsDeck().getCards().add(card2);
        player1.getItemsDeck().getCards().add(card3);
        player1.getItemsDeck().getCards().add(card4);

        matchController.initMatch(partyController);
        Turn turn = new Turn(player1);
        matchController.getTurnController().setTurn(turn);
        matchController.getTurnController().getTurn().setMustDiscard(true);
        ActionRequest action = new ActionRequest(ActionType.DISCARD_CARD, null,
                Item.ATTACK);
        // eseguo l'azione
        DiscardCard dis = new DiscardCard() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }
        };
        dis.initAction(matchController, action);
        if (dis.isValid())
            dis.processAction();
        // verifico gli esiti
        assertFalse(matchController.getMatch().getItemsDeck().getBucket()
                .contains(card1));
        assertFalse(matchController.getMatch().getItemsDeck().getBucket()
                .contains(card2));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket()
                .contains(card3));
        assertFalse(matchController.getMatch().getItemsDeck().getBucket()
                .contains(card4));
        assertTrue(player1.getItemsDeck().getCards().contains(card1));
        assertTrue(player1.getItemsDeck().getCards().contains(card2));
        assertFalse(player1.getItemsDeck().getCards().contains(card3));
        assertTrue(player1.getItemsDeck().getCards().contains(card4));
        assertTrue(matchController.getMatch().getItemsDeck().getBucket().size() == 1);
        assertTrue(player1.getItemsDeck().getCards().size() == 3);

    }
}

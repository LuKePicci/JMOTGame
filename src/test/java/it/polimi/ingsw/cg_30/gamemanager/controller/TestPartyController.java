package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.gamemanager.network.AcceptPlayer;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class TestPartyController {

    public static AcceptPlayer newMockAp() {
        return new AcceptPlayer(UUID.randomUUID()) {

            @Override
            public void sendMessage(Message message) {
                // mock implementation
            }

            @Override
            protected Message receiveMessage() throws IOException {
                // mock implementation
                return null;
            }

            @Override
            public void ping() {
                // mock implementation
            }
        };
    }

    @Before
    public void resetParties() {
        PartyController.getParties().clear();
    }

    @Test
    public void shouldProcessJoinRequest() {

        // no party at the start, this should create a new one
        int prevSize = PartyController.getParties().size();
        AcceptPlayer mockAp = newMockAp();
        MessageController mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        PartyController firstJoinedParty = PartyController.processJoinRequest(
                mockAp.getUUID(), new JoinRequest("Player", null));
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertTrue(firstJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp.getUUID()));

        PartyController secondJoinedParty;

        // at the moment there is one client connected, another request to
        // the
        // same game should add the new one to previously created party
        for (int i = 0; i < EftaiosGame.MAX_PLAYERS - 1; i++) {

            prevSize = PartyController.getParties().size();
            mockAp = newMockAp();
            mc = new MessageController(mockAp);
            MessageController.usedIds.add(mockAp.getUUID());
            MessageController.connectedClients.put(mockAp.getUUID(), mc);
            secondJoinedParty = PartyController.processJoinRequest(mockAp
                    .getUUID(), new JoinRequest("Player", new EftaiosGame()));
            assertEquals(prevSize, PartyController.getParties().size());
            assertEquals(firstJoinedParty, secondJoinedParty);
            assertTrue(secondJoinedParty.getCurrentParty().getMembers()
                    .containsValue(mockAp.getUUID()));
        }

        // at the moment the first party is full, a new request should create
        // another party
        prevSize = PartyController.getParties().size();
        mockAp = newMockAp();
        mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        secondJoinedParty = PartyController.processJoinRequest(
                mockAp.getUUID(), new JoinRequest("Player", new EftaiosGame()));
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertNotEquals(firstJoinedParty, secondJoinedParty);
        assertTrue(secondJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp.getUUID()));
        assertEquals(1, secondJoinedParty.getCurrentParty().getMembers().size());

        // now a player asks to join a different game, so a new party should be
        // created
        prevSize = PartyController.getParties().size();
        mockAp = newMockAp();
        mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        PartyController thirdJoinedParty = PartyController.processJoinRequest(
                mockAp.getUUID(), new JoinRequest("Player", new EftaiosGame(
                        "galilei")));
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertNotEquals(secondJoinedParty, thirdJoinedParty);
        assertTrue(thirdJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp.getUUID()));
    }

    @Test
    public void shouldJoinPrivateParty() {
        // no party at the start, this should create a new one
        int prevSize = PartyController.getParties().size();
        AcceptPlayer mockAp = newMockAp();
        MessageController mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        PartyController privateJoinedParty = PartyController
                .processJoinRequest(mockAp.getUUID(), new JoinRequest("Player",
                        new EftaiosGame(), "PrivateParty"));
        mockAp = newMockAp();
        mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        privateJoinedParty = PartyController.processJoinRequest(mockAp
                .getUUID(), new JoinRequest("Player", new EftaiosGame(),
                "PrivateParty"));
        assertEquals(2, privateJoinedParty.getCurrentParty().getMembers()
                .size());
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertTrue(privateJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp.getUUID()));
        assertTrue(privateJoinedParty.getCurrentParty().isPrivate());

        // an user ask for the same game but the existing party is private, a
        // new party should be created
        prevSize = PartyController.getParties().size();
        mockAp = newMockAp();
        mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        PartyController publicJoinedParty = PartyController.processJoinRequest(
                mockAp.getUUID(), new JoinRequest("Player", new EftaiosGame()));
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertTrue(publicJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp.getUUID()));
        assertFalse(publicJoinedParty.getCurrentParty().isPrivate());
        assertNotEquals(privateJoinedParty, publicJoinedParty);
    }

    @Test
    public void shouldNotJoinMatchInProgressParty() {
        AcceptPlayer mockAp = newMockAp();
        MessageController mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        PartyController privateJoinedParty = PartyController
                .processJoinRequest(mockAp.getUUID(), new JoinRequest("Player",
                        new EftaiosGame(), "PrivateParty"));
        privateJoinedParty.currentMatch = new MatchController();
        mockAp = newMockAp();
        mc = new MessageController(mockAp);
        MessageController.usedIds.add(mockAp.getUUID());
        MessageController.connectedClients.put(mockAp.getUUID(), mc);
        privateJoinedParty = PartyController.processJoinRequest(mockAp
                .getUUID(), new JoinRequest("Player", new EftaiosGame(),
                "PrivateParty"));

    }
}

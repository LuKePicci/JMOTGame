package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

public class TestPartyController {

    private static AcceptPlayer newMockAp() {
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

    @Test
    public void shouldProcessJoinRequest() {

        // no party at the start, this should create a new one
        int prevSize = PartyController.getParties().size();
        AcceptPlayer mockAp = newMockAp();
        PartyController firstJoinedParty = PartyController.processJoinRequest(
                mockAp, new JoinRequest(new EftaiosGame()));
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertTrue(firstJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp));

        // at the moment there is one client connected, another request to
        // the
        // same game should add the new one to previously created party
        for (int i = 0; i < EftaiosGame.MAX_PLAYERS; i++) {

            prevSize = PartyController.getParties().size();
            mockAp = newMockAp();
            PartyController secondJoinedParty = PartyController
                    .processJoinRequest(mockAp, new JoinRequest(
                            new EftaiosGame()));
            assertEquals(prevSize, PartyController.getParties().size());
            assertEquals(firstJoinedParty, secondJoinedParty);
            assertTrue(secondJoinedParty.getCurrentParty().getMembers()
                    .containsValue(mockAp));
        }

        // at the moment the first party is full, a new request should create
        // another party
        prevSize = PartyController.getParties().size();
        mockAp = newMockAp();
        PartyController secondJoinedParty = PartyController.processJoinRequest(
                mockAp, new JoinRequest(new EftaiosGame()));
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertNotEquals(firstJoinedParty, secondJoinedParty);
        assertTrue(secondJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp));
        assertEquals(1, secondJoinedParty.getCurrentParty().getMembers().size());
    }

    @Test
    public void shouldJoinPrivateParty() {
        // no party at the start, this should create a new one
        int prevSize = PartyController.getParties().size();
        AcceptPlayer mockAp = newMockAp();
        PartyController privateJoinedParty = PartyController
                .processJoinRequest(mockAp, new JoinRequest(new EftaiosGame(),
                        true, "PrivateParty"));
        assertEquals(prevSize + 1, PartyController.getParties().size());
        assertTrue(privateJoinedParty.getCurrentParty().getMembers()
                .containsValue(mockAp));
        assertTrue(privateJoinedParty.getCurrentParty().isPrivate());
    }
}

package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;

import org.junit.Test;

public class TestMessageController {
    @Test
    public void shouldDeliverJoinMessage() {
        final Message testMsg = new JoinMessage(new JoinRequest("Player",
                new EftaiosGame()));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp()) {
            @Override
            public void deliver(JoinRequest req) {
                assertEquals(testMsg.getRawRequest(), req);
            }
        };
        mc.dispatchIncoming(testMsg);
    }

    // @Test
    public void shouldReuseUuidToken() {
        final Message testMsg = new JoinMessage(new JoinRequest("Player",
                new EftaiosGame()));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp());
        mc.dispatchIncoming(testMsg);

        JoinRequest req = new JoinRequest("Player", new EftaiosGame());
        req.myID = mc.getAcceptPlayer().getUUID();
        final Message testMsg2 = new JoinMessage(req);
        MessageController otherMc = new MessageController(
                TestPartyController.newMockAp());
        otherMc.dispatchIncoming(testMsg2);

        assertEquals(mc.getAcceptPlayer().getUUID(), otherMc.getAcceptPlayer()
                .getUUID());
        assertEquals(otherMc, MessageController.connectedClients.get(mc
                .getAcceptPlayer().getUUID()));
    }

    // @Test
    public void shouldNotReuseUuidToken() {
        final Message testMsg = new JoinMessage(new JoinRequest("Player",
                new EftaiosGame()));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp());
        mc.dispatchIncoming(testMsg);

        JoinRequest req = new JoinRequest("Player", new EftaiosGame());
        req.myID = UUID.randomUUID();
        final Message testMsg2 = new JoinMessage(req);
        MessageController otherMc = new MessageController(
                TestPartyController.newMockAp());
        otherMc.dispatchIncoming(testMsg2);

        assertNotEquals(mc.getAcceptPlayer().getUUID(), otherMc
                .getAcceptPlayer().getUUID());
        assertNotEquals(otherMc, MessageController.connectedClients.get(mc
                .getAcceptPlayer().getUUID()));
    }

    @Test
    public void shouldDeliverChatMessage() {
        final Message testMsg = new ChatMessage(new ChatRequest("TEST_MESSAGE",
                ChatVisibility.PUBLIC, null));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp()) {
            @Override
            public void deliver(ChatRequest req) {
                assertEquals(testMsg.getRawRequest(), req);
            }
        };
        mc.dispatchIncoming(testMsg);
    }

    @Test
    public void shouldDeliverPartyMessage() {
        final Message testMsg = new PartyMessage(new PartyRequest());
        MessageController mc = new MessageController(
                TestPartyController.newMockAp()) {
            @Override
            public void deliver(PartyRequest req) {
                assertEquals(testMsg.getRawRequest(), req);
            }
        };
        mc.dispatchIncoming(testMsg);
    }

    @Test
    public void shouldDeliverActionMessage() {
        final Message testMsg = new ActionMessage(new ActionRequest(
                ActionType.ATTACK, null, null));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp()) {
            @Override
            public void deliver(ActionRequest req) {
                assertEquals(testMsg.getRawRequest(), req);
            }
        };
        mc.dispatchIncoming(testMsg);
    }
}

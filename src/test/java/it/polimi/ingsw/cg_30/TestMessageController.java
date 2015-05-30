package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMessageController {
    @Test
    public void shouldDeliverJoinMessage() {
        final Message testMsg = new JoinMessage(new JoinRequest(
                new EftaiosGame()));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp()) {
            @Override
            public void deliver(JoinRequest req) {
                assertEquals(testMsg.getRawContent(), req);
            }
        };
        mc.dispatchIncoming(testMsg);
    }

    @Test
    public void shouldDeliverChatMessage() {
        final Message testMsg = new ChatMessage(new ChatRequest("TEST_MESSAGE"));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp()) {
            @Override
            public void deliver(ChatRequest req) {
                assertEquals(testMsg.getRawContent(), req);
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
                assertEquals(testMsg.getRawContent(), req);
            }
        };
        mc.dispatchIncoming(testMsg);
    }

    @Test
    public void shouldDeliverActionMessage() {
        final Message testMsg = new ActionMessage(new ActionRequest());
        MessageController mc = new MessageController(
                TestPartyController.newMockAp()) {
            @Override
            public void deliver(ActionRequest req) {
                assertEquals(testMsg.getRawContent(), req);
            }
        };
        mc.dispatchIncoming(testMsg);
    }
}

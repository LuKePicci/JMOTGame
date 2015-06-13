package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.messaging.PartyMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.PartyRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;

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

    @Test
    public void shouldReuseUuidToken() {
        final Message testMsg = new JoinMessage(new JoinRequest("Player",
                new EftaiosGame()));
        final MessageController mc = new MessageController(
                TestPartyController.newMockAp());
        mc.dispatchIncoming(testMsg);

        JoinRequest req = new JoinRequest("Player", new EftaiosGame()) {
            {
                super.myID = mc.getAcceptPlayer().getUUID();
            }
        };
        final Message testMsg2 = new JoinMessage(req);
        MessageController otherMc = new MessageController(
                TestPartyController.newMockAp());
        otherMc.dispatchIncoming(testMsg2);

        assertEquals(mc.getAcceptPlayer().getUUID(), otherMc.getAcceptPlayer()
                .getUUID());
        assertEquals(otherMc, MessageController.connectedClients.get(mc
                .getAcceptPlayer().getUUID()));
    }

    @SuppressWarnings("serial")
    @Test
    public void shouldNotReuseUuidToken() {
        final Message testMsg = new JoinMessage(new JoinRequest("Player",
                new EftaiosGame()));
        MessageController mc = new MessageController(
                TestPartyController.newMockAp());
        mc.dispatchIncoming(testMsg);

        JoinRequest req = new JoinRequest("Player", new EftaiosGame()) {
            {
                super.myID = UUID.randomUUID();
            }
        };
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

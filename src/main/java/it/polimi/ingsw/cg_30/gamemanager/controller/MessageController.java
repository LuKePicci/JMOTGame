package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.IDelivery;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.messaging.PartyRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.network.AcceptPlayer;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageController implements IDelivery {

    protected static final Set<UUID> usedIds = new HashSet<UUID>();
    protected static final Map<UUID, MessageController> connectedClients = new ConcurrentHashMap<UUID, MessageController>();

    private AcceptPlayer myAP; // Bind the connected player

    private PartyController myParty; // to its party

    public MessageController(AcceptPlayer ap) {
        this.myAP = ap;
    }

    /**
     * Provides decoupled access to outgoing message delivering. Also sends a
     * notify to the party which the player belong about his disconnection in
     * case of {@code DisconnectedException}.
     *
     * @param message
     *            the message to be sent
     * @throws DisconnectedException
     *             the disconnected exception
     */
    public void dispatchOutgoing(Message message) throws DisconnectedException {
        if (this.myAP.connectionLost())
            throw new DisconnectedException(this.myAP.getDisconnectionDate());

        try {
            this.myAP.sendMessage(message);
        } catch (DisconnectedException disEx) {
            this.myParty.sendMessageToParty(new ChatMessage(new ChatViewModel(
                    "I'm gone offline.", this.myParty.getCurrentParty()
                            .getNickOfUUID(this.myAP.getUUID()),
                    ChatVisibility.PARTY)));
            throw disEx;
        }
    }

    /**
     * Deliver a generic message to its target. Let the request instance to
     * invoke its handler.
     *
     * @param msg
     *            the message to be delivered
     */
    public synchronized void dispatchIncoming(Message msg) {
        try {
            RequestModel rq = msg.getRawRequest();
            rq.process(this);
        } catch (UnsupportedOperationException ex) {
            LoggerMethods.unsupportedOperationException(ex,
                    "User issued an unsupported request");
        }
    }

    @Override
    public void deliver(JoinRequest req) {
        MessageController.usedIds.add(this.myAP.getUUID());
        MessageController.connectedClients.put(this.myAP.getUUID(), this);

        if (req.getMyID() == null)
            this.bindToParty(req);
        else
            try {
                this.reuseId(req.getMyID());
            } catch (IllegalArgumentException ex) {
                LoggerMethods.illegalArgumentException(ex, "");
                this.bindToParty(req);
            }
    }

    private void bindToParty(JoinRequest req) {
        this.myParty = PartyController.processJoinRequest(this.myAP.getUUID(),
                req);
    }

    @Override
    public void deliver(ChatRequest req) {
        req.setSender(this.myAP.getUUID());
        ChatController.processChatRequest(req, this.myParty);
    }

    @Override
    public void deliver(PartyRequest req) {
        this.myParty.processPartyRequest(req);
    }

    @Override
    public void deliver(ActionRequest req) {
        try {
            if (this.isJoined() && this.myParty.matchInProgress()
                    && this.isMyTurn())
                this.myParty.getCurrentMatch().processActionRequest(req);

            else {
                this.dispatchOutgoing(new ChatMessage(new ChatViewModel(
                        "You can't do any action at the moment.", "Server",
                        ChatVisibility.PLAYER)));
            }
        } catch (DisconnectedException e) {
            LoggerMethods
                    .disconnectedException(e,
                            "the connection to the subscriber has been lost while performing action.");
        }
    }

    /**
     * Checks if the managed player can perform actions on its match.
     *
     * @return true, if is my turn
     * @throws NullPointerException
     *             if the player is not joined to a party
     */
    private boolean isMyTurn() {
        Player me = this.myParty.getCurrentMatch().getTurnController()
                .getTurn().getCurrentPlayer();
        return this.myAP.getUUID().equals(
                this.myParty.getCurrentParty().getMembers().get(me));
    }

    /**
     * Checks if the managed player joined to a party.
     *
     * @return true, if is joined
     */
    public boolean isJoined() {
        return this.myParty != null;
    }

    /**
     * Swap this instance of MessageController with the old one referred to the
     * given id.
     *
     * @param usedId
     *            the UUID to be reused
     * @return the PartyCntroller instance that manages the party where reusing
     *         UUID is subscribed
     * @throws IllegalArgumentException
     *             thrown if reuse request contains an unknown UUID
     */
    private void reuseId(UUID usedId) {
        if (this.myAP.getUUID().equals(usedId))
            return;

        MessageController oldMc = connectedClients.get(usedId);

        if (usedIds.contains(usedId) && oldMc != null) {
            UUID unusedId = this.myAP.getUUID();
            connectedClients.put(usedId, this);
            this.myAP.setUUID(usedId);
            connectedClients.remove(unusedId);
            usedIds.remove(unusedId);
            this.myParty = oldMc.myParty;

            this.myParty.resumePlayer(usedId);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public AcceptPlayer getAcceptPlayer() {
        return this.myAP;
    }

    public PartyController getPartyController() {
        return this.myParty;
    }

    public static MessageController getPlayerHandler(UUID apId) {
        return connectedClients.get(apId);
    }

    public static void sendMessageToAll(Message message) {
        for (MessageController mc : MessageController.connectedClients.values())
            try {
                mc.dispatchOutgoing(message);
            } catch (DisconnectedException e) {
                LoggerMethods
                        .disconnectedException(e,
                                "If not handled before, it means at this point the message can be discarded.");
            }
    }
}

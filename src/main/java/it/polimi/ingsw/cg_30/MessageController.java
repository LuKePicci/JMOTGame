package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageController {

    protected static final Set<UUID> usedIds = new HashSet<UUID>();
    protected static final Map<UUID, MessageController> connectedClients = new ConcurrentHashMap<UUID, MessageController>();

    private AcceptPlayer myAP; // Bind the connected player

    private PartyController myParty; // to its party

    public MessageController(AcceptPlayer ap) {
        this.myAP = ap;
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
            RequestModel rq = msg.getRawContent();
            rq.process(this);
        } catch (UnsupportedOperationException ex) {
            // TODO Log this event
            System.out.println("User issued an unsupported request");
        }
    }

    public void deliver(JoinRequest req) {
        MessageController.usedIds.add(this.myAP.getUUID());
        MessageController.connectedClients.put(this.myAP.getUUID(), this);

        if (req.myID == null) {
            this.myParty = PartyController.processJoinRequest(
                    this.myAP.getUUID(), req);
        } else {
            try {
                this.myParty = this.reuseId(req.myID);
            } catch (IllegalArgumentException ex) {
                // TODO Log this event
                System.out.println("User issued an illegal reuse request");
            }
        }

    }

    public void deliver(ChatRequest req) {
        throw new UnsupportedOperationException();
    }

    public void deliver(PartyRequest req) {
        throw new UnsupportedOperationException();
    }

    public void deliver(ActionRequest req) {
        if (this.isJoined() && this.myParty.matchInProgress())
            this.myParty.getCurrentMatch().processActionRequest(req);
    }

    private boolean isJoined() {
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
    private PartyController reuseId(UUID usedId)
            throws IllegalArgumentException {
        if (this.myAP.getUUID().equals(usedId))
            return this.myParty;

        MessageController oldMc = connectedClients.get(usedId);

        if (usedIds.contains(usedId) && oldMc != null) {
            UUID unusedId = this.myAP.getUUID();
            connectedClients.put(usedId, this);
            this.myAP.sessionId = usedId;
            connectedClients.remove(unusedId);
            usedIds.remove(unusedId);
            return oldMc.myParty;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public AcceptPlayer getAcceptPlayer() {
        return this.myAP;
    }

    public static MessageController getPlayerHandler(UUID apId) {
        return connectedClients.get(apId);
    }

    public static void sendMessageToAll(Message message) {
        for (MessageController mc : MessageController.connectedClients.values())
            mc.getAcceptPlayer().sendMessage(message);
    }
}

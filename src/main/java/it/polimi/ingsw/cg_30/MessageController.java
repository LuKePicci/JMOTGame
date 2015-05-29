package it.polimi.ingsw.cg_30;

import javax.naming.OperationNotSupportedException;

public class MessageController {

    private AcceptPlayer myAP; // Bind the connected player

    private PartyController myParty; // to its party

    public MessageController(AcceptPlayer ap) {
        this.myAP = ap;
    }

    /**
     * Deliver a generic message to its target.
     *
     * @param msg
     *            the message to be delivered
     */
    public synchronized void deliver(Message msg) {
        RequestModel rq = msg.getRawContent();
        try {
            msg.getType().linkedClass().cast(rq);
            this.deliver(rq);
        } catch (UnsupportedOperationException | ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Alert that the message has no valid target
     *
     * @param req
     *            the request that has not been delivered
     * @throws OperationNotSupportedException
     *             the operation not supported exception
     */
    private void deliver(RequestModel req) {
        throw new UnsupportedOperationException();
                }

    private void deliver(JoinRequest req) {
        this.myParty = PartyController.processJoinRequest(this.myAP, req);
                }

    private void deliver(ChatRequest req) {
        throw new UnsupportedOperationException();
    }

    private boolean isJoined() {
        return this.myParty != null;
    }
}

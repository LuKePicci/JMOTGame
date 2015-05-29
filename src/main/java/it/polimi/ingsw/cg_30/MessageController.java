package it.polimi.ingsw.cg_30;

public class MessageController {

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
        this.myParty = PartyController.processJoinRequest(this.myAP, req);
    }

    public void deliver(ChatRequest req) {
        throw new UnsupportedOperationException();
    }

    public void deliver(PartyRequest partyRequest) {
        throw new UnsupportedOperationException();
    }

    public void deliver(ActionRequest actionRequest) {
        throw new UnsupportedOperationException();
    }

    private boolean isJoined() {
        return this.myParty != null;
    }

}

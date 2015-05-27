package it.polimi.ingsw.cg_30;

public class MessageController {

    private AcceptPlayer myAP; // Bind the connected player

    private PartyController myParty; // to its party

    public MessageController(AcceptPlayer ap) {
        this.myAP = ap;
    }

    public void deliver(Message msg) {
        switch (msg.getType()) {
            case CHAT_MESSAGE:
                break;

            case JOIN_MESSAGE:
                myParty = PartyController.processJoinRequest(myAP,
                        ((JoinMessage) msg).getContent());
                break;

            case ACTION_MESSAGE:
                if (isJoined()) {

                }
                break;

            case PARTY_MESSAGE:
                if (isJoined()) {

                }
                break;

        }
    }

    private boolean isJoined() {
        return this.myParty != null;
    }
}

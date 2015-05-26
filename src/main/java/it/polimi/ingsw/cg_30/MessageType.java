package it.polimi.ingsw.cg_30;

/**
 * The Enum MessageType.
 */
public enum MessageType {

    /** The chat message. */
    CHAT_MESSAGE(ChatRequest.class),
    /** The action message. */
    ACTION_MESSAGE(ActionRequest.class),
    /** The party message. */
    PARTY_MESSAGE(PartyRequest.class),
    /** The join message. */
    JOIN_MESSAGE(JoinRequest.class);

    /** The c. */
    private Class c;

    /**
     * Instantiates a new message type.
     *
     * @param c
     *            the c
     */
    MessageType(Class c) {
        this.c = c;
    }

    /**
     * Linked class.
     *
     * @return the class
     */
    public Class linkedClass() {
        return this.c;
    }
}

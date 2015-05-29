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

    /** The actual value of this enum constant */
    private Class c;

    /**
     * Instantiates a new message type.
     *
     * @param c
     *            the c
     */
    <T extends RequestModel> MessageType(Class<T> c) {
        this.c = c;
    }

    /**
     * Linked class.
     *
     * @return the class
     */
    public <T extends RequestModel> Class<T> linkedClass() {
        return this.c;
    }
}

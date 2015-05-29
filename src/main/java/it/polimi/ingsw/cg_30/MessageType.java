package it.polimi.ingsw.cg_30;

public enum MessageType {
    ChatMessage(ChatRequest.class), ActionMessage(ActionRequest.class), PartyMessage(
            PartyRequest.class), JoinMessage(JoinRequest.class);

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

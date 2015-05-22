package it.polimi.ingsw.cg_30;

public enum MessageType {
    ChatMessage(ChatRequest.class), ActionMessage(ActionRequest.class), PartyMessage(
            PartyRequest.class), JoinMessage(JoinRequest.class);

    private Class c;

    MessageType(Class c) {
        this.c = c;
    }

    public Class linkedClass() {
        return this.c;
    }
}

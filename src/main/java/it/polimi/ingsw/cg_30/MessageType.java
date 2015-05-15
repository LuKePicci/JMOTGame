package it.polimi.ingsw.cg_30;

public enum MessageType {
	ChatMessage(ChatMessage.class), ActionMessage(ActionMessage.class), PartyMessage(
			PartyMessage.class), InitMessage(Message.class);

	private Class c;

	MessageType(Class c) {
		this.c = c;
	}

	public Class linkedClass() {
		return this.c;
	}
}

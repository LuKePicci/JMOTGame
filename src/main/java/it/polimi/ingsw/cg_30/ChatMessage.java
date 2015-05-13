package it.polimi.ingsw.cg_30;

public class ChatMessage implements IMessage {
	private ChatRequest Message;

	public ChatRequest getMessage() {
		return Message;
	}

	public ChatMessage(ChatRequest request) {
		Message = request;
		
	}

}

package it.polimi.ingsw.cg_30;

public class ChatMessage implements IMessage {
	private ChatRequest Message;


	public ChatMessage(ChatRequest request) {
		Message = request;
		
	}

	@Override
	public RequestModel getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageType getType() {
		// TODO Auto-generated method stub
		return null;
	}

}

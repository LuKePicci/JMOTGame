package it.polimi.ingsw.cg_30;


public class PartyMessage implements IMessage
{
	public PartyRequest Message;


	public PartyMessage(PartyRequest request) {
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


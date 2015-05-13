package it.polimi.ingsw.cg_30;


public class PartyMessage implements IMessage
{
	public PartyRequest Message;

	public PartyRequest getMessage() {
		return Message;
	}
	
	public PartyMessage(PartyRequest request) {
		Message = request;
		
	}

}


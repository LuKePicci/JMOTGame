package it.polimi.ingsw.cg_30;


public class ActionMessage implements IMessage
{
	public ActionRequest Message;
	

	
	public ActionMessage(ActionRequest request) {
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


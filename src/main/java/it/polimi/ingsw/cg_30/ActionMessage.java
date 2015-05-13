package it.polimi.ingsw.cg_30;


public class ActionMessage implements IMessage
{
	public ActionRequest Message;
	
	public ActionRequest getMessage() {
		return Message;
	}
	
	public ActionMessage(ActionRequest request) {
		Message = request;
		
	}


}


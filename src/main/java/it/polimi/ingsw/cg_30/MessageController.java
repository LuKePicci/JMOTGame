package it.polimi.ingsw.cg_30;

public class MessageController {
	private PartyController myParty;
	private AcceptPlayer myAP;

	public MessageController(AcceptPlayer ap) {
		this.myAP = ap;
	}

	public void deliver(Message msg) {
		switch (msg.getType()) {
		case ActionMessage:
			break;
		case ChatMessage:
			break;
		case PartyMessage:
			break;
		case InitMessage:
			break;
		}
	}

}

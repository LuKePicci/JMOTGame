package it.polimi.ingsw.cg_30;

public class MessageController {

	private AcceptPlayer myAP; // Bind the connected player

	private PartyController myParty; // to its party

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

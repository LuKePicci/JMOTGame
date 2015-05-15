package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public abstract class Message {

	@XmlAttribute(name = "Type")
	protected MessageType type;

	protected RequestModel content;

	protected final MessageType getType() {
		return this.type;
	}

	public RequestModel getContent() {
		return this.content;
	}

	public void setContent(RequestModel content) {
		this.content = content;
	}

	// Object From;
	// Object To;

	protected Message(MessageType t) {
		this.type = t;
	}

	@SuppressWarnings("unused")
	protected Message() {
		// local attributes initialization by JAXB
	}
}

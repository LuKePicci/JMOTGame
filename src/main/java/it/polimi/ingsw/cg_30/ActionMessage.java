package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class ActionMessage extends Message {

	public ActionMessage(ActionRequest request) {
		super(MessageType.ActionMessage);
		this.content = request;
	}

	@XmlElement(name = "Content")
	@Override
	public ActionRequest getContent() {
		return (ActionRequest) super.getContent();
	}

	public void setContent(ActionRequest content) {
		super.setContent(content);
	}

	@SuppressWarnings("unused")
	private ActionMessage() {
		// local attributes initialization by JAXB
	}
}

package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class PartyMessage extends Message
{
	public PartyMessage(PartyRequest request) {
		super(MessageType.PartyMessage);
		this.content = request;
	}

	@XmlElement(name = "Content")
	@Override
	public PartyRequest getContent() {
		return (PartyRequest) super.getContent();
	}
	
	public void setContent(PartyRequest content){
		super.setContent(content);
	}
	
	@SuppressWarnings("unused")
	private PartyMessage(){
		// local attributes initialization by JAXB
	}
}


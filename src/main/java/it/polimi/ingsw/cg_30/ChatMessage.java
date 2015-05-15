package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class ChatMessage extends Message {

	public ChatMessage(ChatRequest request) {
		super(MessageType.ChatMessage);
		super.content = request;
	}

	@XmlElement(name = "Content")
	@Override
	public ChatRequest getContent() {
		return (ChatRequest) super.getContent();
	}
	
	public void setContent(ChatRequest content){
		super.setContent(content);
	}

	@SuppressWarnings("unused")
	private ChatMessage(){
		// local attributes initialization by JAXB
	}
}

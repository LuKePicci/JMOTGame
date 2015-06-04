package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class ChatMessage extends Message {

    @SuppressWarnings("unused")
    private ChatMessage() {
        // local attributes initialization by JAXB
    }

    public ChatMessage(ChatRequest request) {
        super(MessageType.CHAT_MESSAGE);
        super.requestContent = request;
    }

    public ChatRequest getContent() {
        return (ChatRequest) super.getRawRequest();
    }

    protected void setContent(ChatRequest content) {
        super.setRawRequest(content);
    }

}

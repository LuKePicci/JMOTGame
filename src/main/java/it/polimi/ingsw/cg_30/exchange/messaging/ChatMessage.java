package it.polimi.ingsw.cg_30.exchange.messaging;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class ChatMessage extends Message {

    private static final long serialVersionUID = -5935094401281777663L;

    @SuppressWarnings("unused")
    private ChatMessage() {
        // local attributes initialization by JAXB
    }

    public ChatMessage(ChatRequest request) {
        super(MessageType.REQUEST_MESSAGE);
        super.requestContent = request;
    }

    public ChatMessage(ChatViewModel model) {
        super(MessageType.VIEW_MESSAGE);
        super.publishedContent = model;
    }

    public ChatRequest getRequest() {
        return (ChatRequest) super.getRawRequest();
    }

    protected void setRequest(ChatRequest content) {
        super.setRawRequest(content);
    }

    public ChatViewModel getPublishedContent() {
        return (ChatViewModel) this.publishedContent;
    }

    protected void setPublishedContent(ChatViewModel model) {
        this.publishedContent = model;
    }

}

package it.polimi.ingsw.cg_30;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class ChatRequest extends RequestModel {

    private static final long serialVersionUID = 1442408036864647648L;

    @XmlElement(name = "Text")
    private String text;

    @XmlAttribute(name = "Visibility")
    private ChatVisibility audience;

    private UUID sender;

    @XmlElement(name = "To")
    private String recipient;

    @SuppressWarnings("unused")
    private ChatRequest() {
        // JAXB handled
        super();
    }

    public ChatRequest(String text, ChatVisibility target, String recipient) {
        this.text = text;
        this.audience = target;
        this.recipient = recipient;
    }

    public String getText() {
        return this.text;
    }

    public ChatVisibility getAudience() {
        return this.audience;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public UUID getSender() {
        return this.sender;
    }

    public void setSender(UUID id) {
        this.sender = id;
    }

    @Override
    public void process(MessageController mc) {
        mc.deliver(this);
    }

}

package it.polimi.ingsw.cg_30.exchange.viewmodels;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Chat")
public class ChatViewModel extends ViewModel {

    @XmlElement(name = "Text")
    private String text;

    @XmlAttribute(name = "From")
    private String senderNick;

    @XmlAttribute(name = "Audience")
    private ChatVisibility audience;

    private ChatViewModel() {
        // JAXB handled
        super(ViewType.CHAT);
    }

    public ChatViewModel(String textMessage, String from, ChatVisibility target) {
        this();
        this.text = textMessage;
        this.senderNick = from;
        this.audience = target;
    }

    public String getText() {
        return this.text;
    }

    public String getSenderNick() {
        return this.senderNick;
    }

    public ChatVisibility getAudience() {
        return this.audience;
    }

}

package it.polimi.ingsw.cg_30.exchange.messaging;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ActionMessage.class, ChatMessage.class, PartyMessage.class,
        JoinMessage.class })
public abstract class Message implements Serializable {

    private static final long serialVersionUID = -7712280460808337633L;

    protected MessageType msgType;

    protected RequestModel requestContent;

    protected ViewModel publishedContent;

    protected Message(MessageType t) {
        this.msgType = t;
    }

    protected Message() {
        // local attributes initialization by JAXB
    }

    @XmlAttribute(name = "MessageType")
    public final MessageType getType() {
        return this.msgType;
    }

    @SuppressWarnings("unused")
    private final void setType(MessageType type) {
        this.msgType = type;
    }

    @XmlElement(name = "Request")
    public RequestModel getRawRequest() {
        return this.requestContent;
    }

    @XmlElement(name = "View")
    public ViewModel getRawView() {
        return this.publishedContent;
    }

    protected void setRawRequest(RequestModel content) {
        this.requestContent = content;
    }

}

package it.polimi.ingsw.cg_30.exchange.messaging;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ChatMessage.class })
public class Message implements Serializable {

    private static final long serialVersionUID = -7712280460808337633L;

    private static Marshaller messageMarshaller;

    private static Unmarshaller messageUnmarshaller;

    protected MessageType msgType;

    protected UUID sessionId;

    protected RequestModel requestContent;

    protected ViewModel publishedContent;

    static {
        Marshaller msl = null;
        Unmarshaller unmsl = null;
        try {
            JAXBContext ctx;
            ctx = JAXBContext.newInstance(Message.class);
            msl = ctx.createMarshaller();
            // msl.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            unmsl = ctx.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            Message.messageMarshaller = msl;
            Message.messageUnmarshaller = unmsl;
        }
    }

    public Message(RequestModel req) {
        this(MessageType.REQUEST_MESSAGE);
        this.requestContent = req;
    }

    public Message(ViewModel pub) {
        this(MessageType.VIEW_MESSAGE);
        this.publishedContent = pub;
    }

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

    @XmlElement(name = "UUID")
    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID id) {
        this.sessionId = id;
    }

    @XmlElement(name = "Request")
    public RequestModel getRawRequest() {
        return this.requestContent;
    }

    protected void setRawRequest(RequestModel content) {
        this.requestContent = content;
    }

    @XmlElement(name = "View")
    public ViewModel getRawView() {
        return this.publishedContent;
    }

    protected void setRawView(ViewModel pub) {
        this.publishedContent = pub;
    }

    public static String msgToXML(Message msg) {
        StringWriter sw = new StringWriter();
        try {
            Message.messageMarshaller.marshal(msg, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static Message msgFromXML(String xml) {
        Message msg = null;
        try {
            msg = (Message) Message.messageUnmarshaller
                    .unmarshal(new StringReader(xml));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return msg;
    }

}

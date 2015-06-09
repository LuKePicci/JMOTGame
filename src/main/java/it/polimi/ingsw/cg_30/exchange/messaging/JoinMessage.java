package it.polimi.ingsw.cg_30.exchange.messaging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class JoinMessage extends Message {

    private static final long serialVersionUID = -832817960487122513L;

    @SuppressWarnings("unused")
    private JoinMessage() {
        // local attributes initialization by JAXB
    }

    public JoinMessage(JoinRequest request) {
        super(MessageType.JOIN_MESSAGE);
        this.requestContent = request;
    }

    public JoinRequest getContent() {
        return (JoinRequest) super.getRawRequest();
    }

    protected void setContent(JoinRequest content) {
        super.setRawRequest(content);
    }

}

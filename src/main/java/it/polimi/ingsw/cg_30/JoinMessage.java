package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class JoinMessage extends Message {

    public JoinMessage(JoinRequest request) {
        super(MessageType.PartyMessage);
        this.content = request;
    }

    public JoinRequest getContent() {
        return (JoinRequest) super.getRawContent();
    }

    protected void setContent(JoinRequest content) {
        super.setRawContent(content);
    }

    @SuppressWarnings("unused")
    private JoinMessage() {
        // local attributes initialization by JAXB
    }
}

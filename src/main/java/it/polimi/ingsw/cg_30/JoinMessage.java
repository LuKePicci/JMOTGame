package it.polimi.ingsw.cg_30;

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
        super(MessageType.PARTY_MESSAGE);
        this.content = request;
    }

    public JoinRequest getContent() {
        return (JoinRequest) super.getRawContent();
    }

    protected void setContent(JoinRequest content) {
        super.setRawContent(content);
    }

}

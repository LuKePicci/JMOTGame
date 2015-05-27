package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class PartyMessage extends Message {

    private static final long serialVersionUID = -8606722115691167447L;

    @SuppressWarnings("unused")
    private PartyMessage() {
        // local attributes initialization by JAXB
    }

    public PartyMessage(PartyRequest request) {
        super(MessageType.PARTY_MESSAGE);
        this.content = request;
    }

    public PartyRequest getContent() {
        return (PartyRequest) super.getRawContent();
    }

    protected void setContent(PartyRequest content) {
        super.setRawContent(content);
    }

}

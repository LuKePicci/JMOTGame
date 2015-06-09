package it.polimi.ingsw.cg_30.exchange.messaging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
@XmlAccessorType(XmlAccessType.NONE)
public class ActionMessage extends Message {

    @SuppressWarnings("unused")
    private ActionMessage() {
        // local attributes initialization by JAXB
    }

    public ActionMessage(ActionRequest request) {
        super(MessageType.ACTION_MESSAGE);
        this.requestContent = request;
    }

    public ActionRequest getContent() {
        return (ActionRequest) super.getRawRequest();
    }

    protected void setContent(ActionRequest content) {
        super.setRawRequest(content);
    }

}

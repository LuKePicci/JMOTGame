package it.polimi.ingsw.cg_30;

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
        this.content = request;
    }

    public ActionRequest getContent() {
        return (ActionRequest) super.getRawContent();
    }

    protected void setContent(ActionRequest content) {
        super.setRawContent(content);
    }

}

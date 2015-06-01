package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class ChatRequest extends RequestModel {
    @XmlAttribute(name = "Text")
    private String text;

    public ChatRequest() {
        // JAXB handled
        super();
    }

    public ChatRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void process(MessageController mc) {
        mc.deliver(this);
    }

}

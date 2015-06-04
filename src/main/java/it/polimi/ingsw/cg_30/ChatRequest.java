package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
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

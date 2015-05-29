package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Content")
public class ActionRequest extends RequestModel implements Serializable {

    private static final long serialVersionUID = 6425875533542791509L;

    @XmlElementWrapper(name = "Settings")
    @XmlElement(name = "Setting")
    public Collection<Object> currentTurnSettings;

    public ActionRequest() {
        // JAXB handled
        super();
    }

    public void getSettingByName(Object settingName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void process(MessageController mc) {
        mc.deliver(this);
    }

}

package it.polimi.ingsw.cg_30;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Content")
public class JoinRequest extends RequestModel {

    @XmlElementWrapper(name = "Informations")
    @XmlElement(name = "Info")
    public Map<String, Object> joinInfo;

    @XmlElement(name = "Game")
    public Game MyGame;

    @XmlElement(name = "PartyName")
    public String PartyName;

    @XmlElement(name = "IsPrivate")
    public boolean IsPrivate = false;

    public JoinRequest() {
        // JAXB handled
        super();
    }

    public Object getInfoByName(String infoName) {
        return this.joinInfo.get(infoName);
    }

}

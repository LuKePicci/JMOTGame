package it.polimi.ingsw.cg_30;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Content")
public class JoinRequest extends RequestModel {

    private static final long serialVersionUID = -4330706792605351579L;

    @XmlElementWrapper(name = "Informations")
    @XmlElement(name = "Info")
    private Map<String, Object> joinInfo;

    @XmlElement(name = "Game")
    private Game myGame;

    @XmlElement(name = "PartyName")
    private String partyName;

    @XmlElement(name = "IsPrivate")
    private boolean isPrivate = false;

    @SuppressWarnings("unused")
    private JoinRequest() {
        // JAXB handled
        super();
    }

    public JoinRequest(Game g) {
        this(g, false, null);
    }

    public JoinRequest(Game g, boolean privateParty, String name) {
        this.myGame = g;
        this.isPrivate = privateParty;
        this.partyName = name;
    }

    public Map<String, Object> getJoinInfo() {
        return this.joinInfo;
    }

    public Object putJoinInfo(String key, Object value) {
        return this.joinInfo.put(key, value);
    }

    public Game getGame() {
        return this.myGame;
    }

    public String getPartyName() {
        return this.partyName;
    }

    public void setGame(Game g) {
        this.myGame = g;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public Object getInfoByName(String infoName) {
        return this.joinInfo.get(infoName);
    }

}

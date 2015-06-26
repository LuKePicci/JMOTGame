package it.polimi.ingsw.cg_30.exchange.messaging;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Game;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class JoinRequest extends RequestModel {

    private static final long serialVersionUID = -4330706792605351579L;

    @XmlElement(name = "NickName")
    private String nickName;

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

    public JoinRequest(String nick, Game g) {
        this(nick, g, false, null);
    }

    public JoinRequest(String nick, Game g, String partyName) {
        this(nick, g, partyName != null && !partyName.equals(""), partyName);
    }

    private JoinRequest(String nick, Game g, boolean privateParty, String name) {
        this.nickName = nick;
        this.myGame = g;
        this.isPrivate = privateParty;
        this.partyName = name;
    }

    public String getNick() {
        return this.nickName;
    }

    public void setNick(String nick) {
        this.nickName = nick;
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

    @Override
    public void process(IDelivery mc) {
        mc.deliver(this);
    }

}

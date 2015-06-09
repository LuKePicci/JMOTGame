package it.polimi.ingsw.cg_30.gamemanager.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

public class XmlPartyMemberEntry {

    @XmlElement(name = "UUID")
    private UUID memberId;

    @XmlElement(name = "NickName")
    private String nickName;

    @XmlElement(name = "PlayerIndex")
    private int myIndex;

    public XmlPartyMemberEntry(int index, String nick, UUID id) {
        this.myIndex = index;
        this.memberId = id;
        this.nickName = nick;
    }

}

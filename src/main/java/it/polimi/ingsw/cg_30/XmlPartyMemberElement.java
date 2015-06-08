package it.polimi.ingsw.cg_30;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

public class XmlPartyMemberElement {

    @XmlElement(name = "Entry")
    List<XmlPartyMemberEntry> memberList = new ArrayList<XmlPartyMemberEntry>();

    public void addEntry(Player key, UUID value) {
        memberList.add(new XmlPartyMemberEntry(key.getIndex(), key.getName(),
                value));
    }

}

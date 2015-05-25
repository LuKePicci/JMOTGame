package it.polimi.ingsw.cg_30;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class XmlZoneMapElement {

    @XmlElement(name = "Entry")
    public List<XmlZoneMapEntry> entries = new ArrayList<XmlZoneMapEntry>();

    public void addEntry(HexPoint key, Sector value) {
        entries.add(new XmlZoneMapEntry(key, value));
    }

}

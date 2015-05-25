package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlElement;

public class XmlZoneMapEntry {

    @XmlElement(name = "Location")
    public HexPoint key;

    @XmlElement(name = "Sector")
    public Sector value;

    public XmlZoneMapEntry() {
    }

    public XmlZoneMapEntry(HexPoint key, Sector value) {
        this.key = key;
        this.value = value;
    }
}

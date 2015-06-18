package it.polimi.ingsw.cg_30.exchange.xmladapters;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;

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

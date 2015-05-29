package eftaios.MapTools;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Map")
public class MapElement {

	@XmlElement(name = "Entry")
	public List<MapEntry> entries = new ArrayList<MapEntry>();

	public void addEntry(PairXY key, SectorModel value) {
		entries.add(new MapEntry(key, value));
	}

}

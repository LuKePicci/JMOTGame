package eftaios.MapTools;

import javax.xml.bind.annotation.XmlElement;

public class MapEntry {

	@XmlElement(name = "Location")
	public PairXY key;
	@XmlElement(name = "Sector")
	public SectorModel value;

	public MapEntry() {
	}

	public MapEntry(PairXY key, SectorModel value) {
		this.key = key;
		this.value = value;
	}
}
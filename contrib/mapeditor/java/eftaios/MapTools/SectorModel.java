package eftaios.MapTools;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Sector")
class SectorModel {

	private SectorType type;

	private PairXY location;

	public SectorModel(PairXY location, SectorType type) {
		this.location = location;
		this.type = type;
	}

	public SectorModel() {
		this.type = SectorType.Empty;
		this.location = new PairXY(0, 0);
	}

	@XmlElement(name = "Location")
	public PairXY getLocation() {
		return this.location;
	}

	public void setLocation(PairXY loc) {
		this.location = loc;
	}
	
	@XmlAttribute(name = "Type")
	public SectorType getType() {
		return this.type;
	}

	
	public void setType(SectorType type) {
		this.type = type;
	}
}

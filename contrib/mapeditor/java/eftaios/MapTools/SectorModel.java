package eftaios.MapTools;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Sector")
@XmlAccessorType(XmlAccessType.NONE)
class SectorModel {

	private SectorType type;

	public SectorModel(SectorType type) {
		this.type = type;
	}

	public SectorModel() {
		this.type = SectorType.Empty;
	}

	@XmlAttribute(name = "Type")
	public SectorType getType() {
		return this.type;
	}

	public void setType(SectorType type) {
		this.type = type;
	}
}

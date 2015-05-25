package eftaios.MapTools;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Zone")
@XmlAccessorType(XmlAccessType.FIELD)
public class MapModel {

	@XmlElement(name = "Contents")
	@XmlJavaTypeAdapter(XmlSectorMapAdapter.class)
	private Map<PairXY, SectorModel> sectors;

	@XmlAttribute(name = "Name")
	private String mapName = "Zone";

	public MapModel() {
		this.sectors = new TreeMap<PairXY, SectorModel>(
				new Comparator<PairXY>() {
					public int compare(PairXY o1, PairXY o2) {
						int comp = o1.X - o2.X;
						return comp == 0 ? o1.Y - o2.Y : comp;
					}
				});
	}

	public Map<PairXY, SectorModel> getSectors() {
		return this.sectors;
	}

	public void setSectors(Map<PairXY, SectorModel> map) {
		this.sectors = map;
	}
}

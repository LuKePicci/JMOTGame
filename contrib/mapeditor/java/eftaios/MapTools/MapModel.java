package eftaios.MapTools;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="Map")
@XmlAccessorType(XmlAccessType.FIELD) 
public class MapModel {
	@XmlElementWrapper(name="Sectors") 
	@XmlElement(name="Sector") 
	private List<SectorModel> sectors;
	
	public MapModel()
	{
		this.sectors = new ArrayList<SectorModel>();
	}
	
	public List<SectorModel> getSectorModels(){
		return this.sectors;
	}
	
	public void setSectorModels(List<SectorModel> list) {
		this.sectors = list;
	}
}

package it.polimi.ingsw.cg_30;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Zone")
public class ZoneViewModel extends ViewModel {

    /** The sectors map. */
    @XmlElement(name = "Contents")
    @XmlJavaTypeAdapter(XmlZoneMapAdapter.class)
    private Map<HexPoint, Sector> sectorsMap;

    @XmlElement(name = "MyLocation")
    private Sector playerLocation;

    public ZoneViewModel(Zone z) {
        this.sectorsMap = z.getMap();
    }

    public ZoneViewModel setPlayerLocation(Sector s) {
        this.playerLocation = s;
        return this;
    }

}

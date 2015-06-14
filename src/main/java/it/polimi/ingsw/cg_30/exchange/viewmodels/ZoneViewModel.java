package it.polimi.ingsw.cg_30.exchange.viewmodels;

import it.polimi.ingsw.cg_30.gamemanager.model.XmlZoneMapAdapter;

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

    public ZoneViewModel(Map<HexPoint, Sector> map) {
        this();
        this.sectorsMap = map;
    }

    private ZoneViewModel() {
        // JAXB handled
        super(ViewType.ZONE);
    }

    public ZoneViewModel setPlayerLocation(Sector s) {
        this.playerLocation = s;
        return this;
    }

    @Override
    public String toString() {
        return "ZoneViewModel { sectorsMap: " + sectorsMap
                + ", playerLocation: " + playerLocation + " }";
    }

}

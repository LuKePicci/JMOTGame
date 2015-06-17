package it.polimi.ingsw.cg_30.exchange.viewmodels;

import it.polimi.ingsw.cg_30.gamemanager.model.XmlZoneMapAdapter;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Zone")
public class ZoneViewModel extends ViewModel {

    /**
     * 
     */
    private static final long serialVersionUID = -437562171602695439L;

    /** The sectors map. */
    @XmlElement(name = "Contents")
    @XmlJavaTypeAdapter(XmlZoneMapAdapter.class)
    private Map<HexPoint, Sector> sectorsMap;

    public ZoneViewModel(Map<HexPoint, Sector> map) {
        this();
        this.sectorsMap = map;
    }

    public Map<HexPoint, Sector> getSectorsMap() {
        return this.sectorsMap;
    }

    private ZoneViewModel() {
        // JAXB handled
        super(ViewType.ZONE);
    }

}

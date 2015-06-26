package it.polimi.ingsw.cg_30.exchange.viewmodels;

import it.polimi.ingsw.cg_30.exchange.xmladapters.XmlZoneMapAdapter;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The Class ZoneViewModel.
 */
@XmlRootElement(name = "Zone")
public class ZoneViewModel extends ViewModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -437562171602695439L;

    /** The sectors map. */
    @XmlElement(name = "Contents")
    @XmlJavaTypeAdapter(XmlZoneMapAdapter.class)
    private Map<HexPoint, Sector> sectorsMap;

    /**
     * Instantiates a new zone view model.
     *
     * @param map
     *            the sectors map
     */
    public ZoneViewModel(Map<HexPoint, Sector> map) {
        this();
        this.sectorsMap = map;
    }

    /**
     * Instantiates a new zone view model.
     */
    private ZoneViewModel() {
        // JAXB handled
        super(ViewType.ZONE);
    }

    /**
     * Gets the sectors map.
     *
     * @return the sectors map
     */
    public Map<HexPoint, Sector> getSectorsMap() {
        return this.sectorsMap;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ZoneViewModel { sectorsMap: " + sectorsMap + " }";
    }

}

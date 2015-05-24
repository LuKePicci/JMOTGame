package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Zone")
@XmlAccessorType(XmlAccessType.NONE)
public class Zone extends GameTable<Sector> implements Serializable {

    private static final long serialVersionUID = -2636229005380853458L;

    @XmlElement(name = "Contents")
    @XmlJavaTypeAdapter(XmlZoneMapAdapter.class)
    private Map<HexPoint, Sector> sectors = new HashMap<HexPoint, Sector>();

    @XmlAttribute(name = "Name")
    private String mapName;

    public Map<Player, Sector> playersLocation;

    private List<Boolean> hatchesStatus = new ArrayList<Boolean>();

    public Iterable<Sector> getSectors() {
        throw new UnsupportedOperationException();
    }

    public String getMapName() {
        return this.mapName;
    }

    @Override
    public Sector getCell(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void movePlayer(Player who, Sector where) {
        playersLocation.put(who, where);
    }

    @Override
    public Collection<Sector> reachableTargets(Sector from, Integer maxSteps) {
		throw new UnsupportedOperationException();
	}
    
    public Sector getSector(Player player) {
        return playersLocation.get(player);
    }

    public Iterable<Player> getPlayersInSector(Sector sec) {
        throw new UnsupportedOperationException();
    }

    private void lockHatch(int hatchNumber) {
        throw new UnsupportedOperationException();
    }

    public void isHatchLocked(int hatchNumber) {
        throw new UnsupportedOperationException();
    }

    public boolean noMoreHatches() {
        throw new UnsupportedOperationException();
    }

}

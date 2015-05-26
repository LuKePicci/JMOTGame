package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private Map<HexPoint, Sector> sectorsMap = new HashMap<HexPoint, Sector>();

    @XmlAttribute(name = "Name")
    private String mapName;

    public Map<Player, Sector> playersLocation;

    private List<Boolean> hatchesStatus = new ArrayList<Boolean>();

    public Map<HexPoint, Sector> getMap() {
        return this.sectorsMap;
    }

    public String getMapName() {
        return this.mapName;
    }

    @Override
    public Sector getCell(Player player) {
        return playersLocation.get(player);
    }

    @Override
    public void movePlayer(Player who, Sector where) {
        playersLocation.put(who, where);
    }

    @Override
    public Collection<Sector> reachableTargets(Sector from, Integer maxSteps) {
        throw new UnsupportedOperationException();
    }

    public Iterable<Player> getPlayersInSector(Sector sec) {
        Set<Player> pl = new HashSet<Player>();
        if (playersLocation.containsValue(sec)) {
            for (Player ex : playersLocation.keySet()) {
                if (playersLocation.get(ex).equals(sec)) {
                    pl.add(ex);
                }
            }
        }
        return pl;
    }

    public void lockHatch(int hatchNumber) {
        this.hatchesStatus.set(hatchNumber, true);
    }

    public void isHatchLocked(int hatchNumber) {
        this.hatchesStatus.get(hatchNumber);
    }

    public boolean noMoreHatches() {
        int g = 0;
        for (Boolean st : hatchesStatus) {
            if (st == true) {
                g++;
            }
        }
        if (g == hatchesStatus.size()) {
            return true;
        } else
            return false;
    }

}

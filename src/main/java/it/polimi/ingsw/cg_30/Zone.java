package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.ArrayList;
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

/**
 * The Class Zone.
 */
@XmlRootElement(name = "Zone")
@XmlAccessorType(XmlAccessType.NONE)
public class Zone extends GameTable<Sector> implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2636229005380853458L;

    /** The sectors map. */
    @XmlElement(name = "Contents")
    @XmlJavaTypeAdapter(XmlZoneMapAdapter.class)
    private Map<HexPoint, Sector> sectorsMap = new HashMap<HexPoint, Sector>();

    /** The map name. */
    @XmlAttribute(name = "Name")
    private String mapName;

    /** The players location. */
    public Map<Player, Sector> playersLocation;

    /** The hatches status. */
    private List<Boolean> hatchesStatus = new ArrayList<Boolean>();

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Map<HexPoint, Sector> getMap() {
        return this.sectorsMap;
    }

    /**
     * Gets the map name.
     *
     * @return the map name
     */
    public String getMapName() {
        return this.mapName;
    }

    /**
     * Gets the sector of a particular player.
     *
     * @return the sector where player is located
     */
    @Override
    public Sector getCell(Player player) {
        return playersLocation.get(player);
    }

    /**
     * Moves player "who" in sector "where". This method does not check the
     * legality of the movement.
     */
    @Override
    public void movePlayer(Player who, Sector where) {
        playersLocation.put(who, where);
    }

    /**
     * Gets all the sectors, whose distance is not higher than maxSteps,
     * reachable from the sector "from".
     * 
     * @return the set of reachable sectors
     * 
     */
    @Override
    public Set<Sector> reachableTargets(Sector from, Integer maxSteps) {

        Set<Sector> visited = new HashSet<Sector>();
        List<List<Sector>> fringes = new ArrayList<List<Sector>>();
        List<Sector> firstlist = new ArrayList<Sector>();
        Sector neighbor;

        visited.add(from);
        firstlist.add(from);
        fringes.add(firstlist);

        for (int k = 1; k <= maxSteps; k++) {
            fringes.add(new ArrayList<Sector>());
            for (Sector var : fringes.get(k - 1))
                for (HexCubeDirections dir : HexCubeDirections.values()) {
                    neighbor = this.sectorsMap
                            .get(var.getPoint().neighbor(dir));
                    if (neighbor == null)
                        // neighbor not existing in this zone
                        continue;
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        fringes.get(k).add(neighbor);
                    }
                }

        }
        visited.remove(from);
        return visited;
    }

    /**
     * Gets all the players in sector sec.
     *
     * @param sec
     *            the sector to be checked
     * @return the players in sector sec
     */
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

    /**
     * Locks hatch whose number is hatchNumber.
     *
     * @param hatchNumber
     *            the number of the hatch that have to be locked
     */
    public void lockHatch(int hatchNumber) {
        this.hatchesStatus.set(hatchNumber, true);
    }

    /**
     * Checks if the hatch number "hatchNumber" is locked.
     *
     * @param hatchNumber
     *            the number of the hatch to be checked
     */
    public void isHatchLocked(int hatchNumber) {
        this.hatchesStatus.get(hatchNumber);
    }

    /**
     * No more hatches.
     *
     * @return true if all hatches are locked, false otherwise
     */
    public boolean noMoreHatches() {
        int g = 0;
        for (Boolean st : hatchesStatus) {
            if (st) {
                g++;
            }
        }
        if (g == hatchesStatus.size()) {
            return true;
        } else
            return false;
    }

}

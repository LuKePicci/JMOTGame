package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private HashMap<HexPoint, Sector> sectorsMap = new HashMap<HexPoint, Sector>();

    /** The map name. */
    @XmlAttribute(name = "Name")
    private String mapName;

    /** The players location. */
    public HashMap<Player, Sector> playersLocation;

    /** The hatches status. */
    private ArrayList<Boolean> hatchesStatus = new ArrayList<Boolean>();

    /**
     * Gets the map.
     *
     * @return the map
     */
    public HashMap<HexPoint, Sector> getMap() {
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
     * Moves player who in sector where. This method does not check the legality
     * of the move.
     */
    @Override
    public void movePlayer(Player who, Sector where) {
        playersLocation.put(who, where);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.polimi.ingsw.cg_30.GameTable#reachableTargets(it.polimi.ingsw.cg_30
     * .Cell, java.lang.Integer)
     */
    @Override
    public Set<Sector> reachableTargets(Sector from, Integer maxSteps) {

        Set<Sector> visited = new HashSet<Sector>();
        ArrayList<ArrayList<Sector>> fringes = new ArrayList<ArrayList<Sector>>();
        ArrayList<Sector> firstlist = new ArrayList<Sector>();
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
     * Gets the players in sector.
     *
     * @param sec
     *            the sec
     * @return the players in sector
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
     * Lock hatch.
     *
     * @param hatchNumber
     *            the hatch number
     */
    public void lockHatch(int hatchNumber) {
        this.hatchesStatus.set(hatchNumber, true);
    }

    /**
     * Checks if is hatch locked.
     *
     * @param hatchNumber
     *            the hatch number
     */
    public void isHatchLocked(int hatchNumber) {
        this.hatchesStatus.get(hatchNumber);
    }

    /**
     * No more hatches.
     *
     * @return true, if successful
     */
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

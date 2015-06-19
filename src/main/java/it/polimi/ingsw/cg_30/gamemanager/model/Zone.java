package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HexCubeDirections;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ZoneViewModel;
import it.polimi.ingsw.cg_30.exchange.xmladapters.XmlZoneMapAdapter;

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
public class Zone extends GameTable<Sector> implements IViewable, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2636229005380853458L;

    /** The sectors map. */
    @XmlElement(name = "Contents")
    @XmlJavaTypeAdapter(XmlZoneMapAdapter.class)
    private Map<HexPoint, Sector> sectorsMap;;

    /** The map name. */
    @XmlAttribute(name = "Name")
    private String mapName;

    /** The players location. */
    private Map<Player, Sector> playersLocation;

    /**
     * Instantiates a new zone.
     */
    public Zone() {
        this.playersLocation = new HashMap<Player, Sector>();
        this.sectorsMap = new HashMap<HexPoint, Sector>();
    }

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
     * Gets the players location.
     *
     * @return the players location
     */
    public Map<Player, Sector> getPlayersLocation() {
        return this.playersLocation;
    }

    /**
     * Gets the sector of a particular player.
     *
     * @param player
     *            the player
     * @return the sector where player is located
     */
    @Override
    public Sector getCell(Player player) {
        return this.playersLocation.get(player);
    }

    /**
     * Moves player "who" in sector "where". This method does not check the
     * legality of the movement.
     *
     * @param who
     *            the who
     * @param where
     *            the where
     */
    @Override
    public void movePlayer(Player who, Sector where) {
        this.playersLocation.put(who, where);
    }

    /**
     * Can visit.
     *
     * @param s
     *            the s
     * @return true, if successful
     */
    private boolean canVisit(Sector s) {
        return s != null && s.getType() != SectorType.EMPTY
                && s.getType() != SectorType.HUMANS_START
                && s.getType() != SectorType.ALIENS_START;
    }

    /**
     * Gets all the sectors, whose distance is not higher than maxSteps,
     * reachable from the sector "from" (canonical BFS).
     * 
     * @param from
     *            the from
     * @param maxSteps
     *            the max steps
     * @return the set of reachable sectors
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
                    if (!this.canVisit(neighbor))
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
     * Gets a collection of all players in sector sector.
     *
     * @param sec
     *            the sector to be checked
     * @return the players in sector sector
     */
    public Set<Player> getPlayersInSector(Sector sec) {
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
     * Gets the view model of the map.
     */
    @Override
    public ViewModel getViewModel() {
        return new ZoneViewModel(this.getMap());
    }

}

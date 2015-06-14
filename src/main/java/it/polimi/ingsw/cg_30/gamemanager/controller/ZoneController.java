package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Zone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ZoneController.
 */
public class ZoneController {

    /** The current zone. */
    private Zone currentZone;

    /** The hatches. */
    private Map<HexPoint, Sector> hatches = new HashMap<HexPoint, Sector>();

    /** The humans start. */
    private Sector aliensStart, humansStart;

    /**
     * Instantiates a new zone controller.
     */
    public ZoneController() {
        this(new RandomZoneFactory());
    }

    /**
     * Instantiates a new zone controller.
     *
     * @param zf
     *            any zone factory instance
     */
    public ZoneController(ZoneFactory zf) {
        this.currentZone = zf.newMap();
        for (Sector s : this.currentZone.getMap().values()) {
            switch (s.getType()) {
                case ESCAPE_HATCH:
                    this.hatches.put(s.getPoint(), s);
                    break;
                case ALIENS_START:
                    this.aliensStart = s;
                    break;
                case HUMANS_START:
                    this.humansStart = s;
                    break;
                default:
                    continue;
            }
        }
    }

    /**
     * Swap the hatch sector with a new Secure one in same place.
     *
     * @param hatchPoint
     *            the hatch point location in the map
     * @throws NotAnHatchException
     *             if no hatch is found in given location
     */
    public void lockHatch(HexPoint hatchPoint) throws NotAnHatchException {
        Sector found = this.currentZone.getMap().get(hatchPoint);

        if (found == null || found.getType() != SectorType.ESCAPE_HATCH)
            throw new NotAnHatchException(found);

        this.hatches.remove(hatchPoint);
        this.currentZone.getMap().put(hatchPoint,
                new Sector(SectorType.SECURE, hatchPoint));
    }

    /**
     * Checks if there is at least another hatch.
     *
     * @return true if all hatches are locked, false otherwise
     */
    public boolean noMoreHatches() {
        return this.hatches.size() == 0;
    }

    /**
     * Gets the current zone.
     *
     * @return the current zone
     */
    public Zone getCurrentZone() {
        return currentZone;
    }

    /**
     * Place players.
     *
     * @param players
     *            the players
     */
    public void placePlayers(List<Player> players) {
        for (Player player : players) {
            if (PlayerRace.ALIEN.equals(player.getIdentity().getRace())) {
                currentZone.movePlayer(player, aliensStart);
            } else {
                currentZone.movePlayer(player, humansStart);
            }
        }
    }

    /**
     * Gets the humans start.
     *
     * @return the humans start sector
     */
    public Sector getHumansStart() {
        return humansStart;
    }

}

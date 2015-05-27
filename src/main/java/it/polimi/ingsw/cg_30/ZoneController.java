package it.polimi.ingsw.cg_30;

import java.util.HashMap;
import java.util.Map;

public class ZoneController {

    private Zone currentZone;

    private Map<HexPoint, Sector> hatches = new HashMap<HexPoint, Sector>();

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
        for (Sector s : this.currentZone.getMap().values())
            if (s.getType() == SectorType.ESCAPE_HATCH)
                this.hatches.put(s.getPoint(), s);
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
}

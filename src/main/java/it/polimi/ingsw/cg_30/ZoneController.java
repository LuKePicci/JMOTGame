package it.polimi.ingsw.cg_30;

public class ZoneController {
    private Zone currentZone;

    public ZoneController() {
        this(new RandomZoneFactory());
    }

    public ZoneController(ZoneFactory zf) {
        this.currentZone = zf.newMap();
    }

}

package it.polimi.ingsw.cg_30;

import java.io.Serializable;

public class Sector implements Serializable {
    private SectorType type;
    private HexPoint point;

    public SectorType getType() {
        return type;
    }

    public HexPoint getPoint() {
        return point;
    }

    // COSTRUTTORI
    // costruttore inutile
    private Sector() {
    }

    // costruttore utile
    public Sector(SectorType type, HexPoint point) {
        this.type = type;
        this.point = point;
    }
}

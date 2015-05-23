package it.polimi.ingsw.cg_30;

import java.io.Serializable;

public class Sector extends Cell implements Serializable {

    private static final long serialVersionUID = -6244660747783651595L;

    private SectorType type;
    private HexPoint point;

    public Sector(SectorType type, HexPoint point) {
        this.type = type;
        this.point = point;
    }

    @SuppressWarnings("unused")
    private Sector() {
        // JAXB handled
    }

    public SectorType getType() {
        return type;
    }

    @Override
    public HexPoint getPoint() {
        return point;
    }
}

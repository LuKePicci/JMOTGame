package it.polimi.ingsw.cg_30;

import java.io.Serializable;

/**
 * The Class Sector.
 */
public class Sector implements Serializable {

    /** The type. */
    private SectorType type;

    /** The point. */
    private HexPoint point;

    /**
     * Gets the type.
     *
     * @return the type
     */
    public SectorType getType() {
        return type;
    }

    /**
     * Gets the point.
     *
     * @return the point
     */
    public HexPoint getPoint() {
        return point;
    }

    // COSTRUTTORI
    // costruttore inutile
    /**
     * Instantiates a new sector.
     */
    private Sector() {
    }

    // costruttore utile
    /**
     * Instantiates a new sector.
     *
     * @param type
     *            the type
     * @param point
     *            the point
     */
    public Sector(SectorType type, HexPoint point) {
        this.type = type;
        this.point = point;
    }
}

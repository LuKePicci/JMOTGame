package it.polimi.ingsw.cg_30;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * The Class Sector.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Sector extends Cell implements Serializable {

    private static final long serialVersionUID = -6244660747783651595L;

    /** The type. */
    @XmlAttribute(name = "Type")
    private SectorType type;

    /** The point. */
    private HexPoint point;

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

    @SuppressWarnings("unused")
    private Sector() {
        // JAXB handled
    }

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
    @Override
    public HexPoint getPoint() {
        return point;
    }
}

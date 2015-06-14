package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class HexPoint.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "offsetX", "offsetY" })
public class HexPoint extends Point implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6158359481566322375L;

    /** The cubic x. */
    private int cubicX;

    /** The cubic y. */
    private int cubicY;

    /**
     * Instantiates a new hex point.
     */
    @SuppressWarnings("unused")
    private HexPoint() {
        // JAXB handled
    }

    /**
     * Instantiates a new hex point.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     */
    private HexPoint(int x, int y) {
        this.cubicX = x;
        this.cubicY = y;
    }

    /**
     * Hex point cubic.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @return the hex point
     */
    public static HexPoint fromCubic(int x, int y) {
        return new HexPoint(x, y);
    }

    /**
     * Hex point axial.
     *
     * @param q
     *            the q
     * @param r
     *            the r
     * @return the hex point
     */
    public static HexPoint fromAxial(int q, int r) {
        return new HexPoint(q, -q - r);
    }

    /**
     * Hex point offset.
     *
     * @param col
     *            the col
     * @param row
     *            the row
     * @return the hex point
     */
    public static HexPoint fromOffset(int col, int row) {
        return new HexPoint(col, -col - (row - (col - (col & 1)) / 2));
    }

    /**
     * Returns an {@code HexPoint} that represents a neighbor of this instance
     * in the given direction
     *
     * @param dir
     *            the direction to explore
     * @return the neighbor {@code HexPoint} in given direction
     */
    public HexPoint neighbor(HexCubeDirections dir) {
        return new HexPoint(this.getCubicX() + dir.getAddX(), this.getCubicY()
                + dir.getAddY());
    }

    /**
     * Gets the cubic x.
     *
     * @return the cubic x
     */
    public int getCubicX() {
        return cubicX;
    }

    /**
     * Gets the cubic y.
     *
     * @return the cubic y
     */
    public int getCubicY() {
        return cubicY;
    }

    /**
     * Gets the cubic z.
     *
     * @return the cubic z
     */
    public int getCubicZ() {
        return -cubicX - cubicY;
    }

    /**
     * Gets the axial x.
     *
     * @return the axial x
     */
    public int getAxialX() {
        return cubicX;
    }

    /**
     * Gets the axial y.
     *
     * @return the axial y
     */
    public int getAxialY() {
        return -cubicX - cubicY;
    }

    /**
     * Gets the offset x.
     *
     * @return the offset x
     */
    public int getOffsetX() {
        return cubicX;
    }

    /**
     * Sets the offset x.
     *
     * @param col
     *            the new offset x
     */
    @XmlAttribute(name = "X")
    private void setOffsetX(int col) {
        this.cubicX = col;
    }

    /**
     * Gets the offset y.
     *
     * @return the offset y
     */
    public int getOffsetY() {
        return -cubicX - cubicY + (cubicX - (cubicX & 1)) / 2;
    }

    /**
     * Sets the offset y.
     *
     * @param row
     *            the new offset y
     */
    @XmlAttribute(name = "Y")
    private void setOffsetY(int row) {
        this.cubicY = -this.cubicX
                - (row - (this.cubicX - (this.cubicX & 1)) / 2);
    }

    /**
     * Gets the x.
     *
     * @return the offset x
     */
    @Override
    public int getX() {
        return getOffsetX();
    }

    /**
     * Gets the y.
     *
     * @return the offset y
     */
    @Override
    public int getY() {
        return getOffsetY();
    }

    /**
     * Gets the hashCode.
     *
     * @return the hashCode based on class attributes
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cubicX;
        result = prime * result + cubicY;
        return result;
    }

    /**
     * Implements the method equals.
     *
     * @param obj
     *            the object to be compared with
     *
     * @return the boolean true if obj is equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        HexPoint other = (HexPoint) obj;
        if (cubicX != other.cubicX || cubicY != other.cubicY)
            return false;
        return true;
    }

}

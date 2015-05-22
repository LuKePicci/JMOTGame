package it.polimi.ingsw.cg_30;

import java.io.Serializable;

/**
 * The Class HexPoint.
 */
public class HexPoint implements Serializable {

    /** The cubic x. */
    private int cubicX;

    /** The cubic y. */
    private int cubicY;

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
     * Gets the offset y.
     *
     * @return the offset y
     */
    public int getOffsetY() {
        return -cubicX - cubicY + (cubicX - (cubicX & 1)) / 2;
    }

    // "COSTRUTTORI"
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
    public static HexPoint hexPointCubic(int x, int y) {
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
    public static HexPoint hexPointAxial(int q, int r) {
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
    public static HexPoint hexPointOffset(int col, int row) {
        return new HexPoint(col, -col - (row - (col - (col & 1)) / 2));
    }

}

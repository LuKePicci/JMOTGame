package it.polimi.ingsw.cg_30.exchange.viewmodels;

/**
 * The Enum HexCubeDirections.
 */
public enum HexCubeDirections {

    /** The bottom right. */
    BOTTOM_RIGHT(+1, -1),
    /** The top right. */
    TOP_RIGHT(+1, 0),
    /** The top. */
    TOP(0, +1),
    /** The top left. */
    TOP_LEFT(-1, +1),
    /** The bottom left. */
    BOTTOM_LEFT(-1, 0),
    /** The bottom. */
    BOTTOM(0, -1);

    /** The add y. */
    private int addX, addY;

    /**
     * Instantiates a new hex cube directions.
     *
     * @param X
     *            the x
     * @param Y
     *            the y
     */
    private HexCubeDirections(int x, int y) {
        this.addX = x;
        this.addY = y;
    }

    /**
     * Gets the adds the x.
     *
     * @return the adds the x
     */
    public int getAddX() {
        return addX;
    }

    /**
     * Gets the adds the y.
     *
     * @return the adds the y
     */
    public int getAddY() {
        return addY;
    }
}

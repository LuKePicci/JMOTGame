package it.polimi.ingsw.cg_30;

public enum HexCubeDirections {
    BOTTOM_RIGHT(+1, -1), TOP_RIGHT(+1, 0), TOP(0, +1), TOP_LEFT(-1, +1), BOTTOM_LEFT(
            -1, 0), BOTTOM(0, -1);

    private int addX, addY;

    private HexCubeDirections(int X, int Y) {
        this.addX = X;
        this.addY = Y;
    }

    public int getAddX() {
        return addX;
    }

    public int getAddY() {
        return addY;
    }
}

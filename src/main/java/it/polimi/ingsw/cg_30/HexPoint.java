package it.polimi.ingsw.cg_30;

import java.io.Serializable;

public class HexPoint implements Serializable {
    private int cubicX;
    private int cubicY;

    public int getCubicX() {
        return cubicX;
    }

    public int getCubicY() {
        return cubicY;
    }

    public int getCubicZ() {
        return -cubicX - cubicY;
    }

    public int getAxialX() {
        return cubicX;
    }

    public int getAxialY() {
        return -cubicX - cubicY;
    }

    public int getOffsetX() {
        return cubicX;
    }

    public int getOffsetY() {
        return -cubicX - cubicY + (cubicX - (cubicX & 1)) / 2;
    }

    // "COSTRUTTORI"
    private HexPoint(int x, int y) {
        this.cubicX = x;
        this.cubicY = y;
    }

    public static HexPoint hexPointCubic(int x, int y) {
        return new HexPoint(x, y);
    }

    public static HexPoint hexPointAxial(int q, int r) {
        return new HexPoint(q, -q - r);
    }

    public static HexPoint hexPointOffset(int col, int row) {
        return new HexPoint(col, -col - (row - (col - (col & 1)) / 2));
    }

}

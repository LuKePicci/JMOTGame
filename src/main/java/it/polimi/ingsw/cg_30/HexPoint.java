package it.polimi.ingsw.cg_30;

public class HexPoint {
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
	private HexPoint hexPoint(int x, int y) {
		HexPoint ex = new HexPoint();
		this.cubicX = x;
		this.cubicY = y;
		return ex;
	}

	public HexPoint hexPointCubic(int x, int y) {
		return hexPoint(x, y);
	}

	public HexPoint hexPointAxial(int q, int r) {
		return hexPoint(q, -q - r);
	}

	public HexPoint hexPointOffset(int col, int row) {
		return hexPoint(col, -col - (row - (col - (col & 1)) / 2));
	}

}

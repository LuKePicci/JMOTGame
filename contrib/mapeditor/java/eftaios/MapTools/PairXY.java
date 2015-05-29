package eftaios.MapTools;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "X", "charX", "Y" })
public class PairXY implements Serializable {
	private static final long serialVersionUID = -3398531959361569313L;

	@XmlAttribute(name = "X")
	public int X;
	@XmlAttribute(name = "Y")
	public final int Y;

	public PairXY(double x, double y) {
		this.X = (int) Math.round(x);
		this.Y = (int) Math.round(y);

	}

	public PairXY(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public PairXY() {
		this.X = 0;
		this.Y = 0;
	}

	public int getX() {
		return this.X;
	}

	public int getY() {
		return this.Y;
	}

	@XmlAttribute(name = "CharX")
	public String getCharX() {
		return getCharForNumber(this.X + 1);
	}

	private void setCharX(String charX) {
		this.X = (int) charX.charAt(0) - 65;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(X);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PairXY other = (PairXY) obj;
		if (Double.doubleToLongBits(X) != Double.doubleToLongBits(other.X))
			return false;
		if (Double.doubleToLongBits(Y) != Double.doubleToLongBits(other.Y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + X + ", " + Y + "]";
	}

	private static String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
	}

}

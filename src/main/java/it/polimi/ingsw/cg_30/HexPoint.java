package it.polimi.ingsw.cg_30;


public class HexPoint
{
	private int cubicX;

	private int cubicY;
	

	public int getCubicX()
	{
		return cubicX;
	}

	public int getCubicY()
	{
		return cubicY;
	}

	public int getCubicZ()
	{
		return (-cubicX-cubicY);
	}

	public int getAxialX()
	{
		return cubicX;
	}

	public int getAxialY()
	{
		return (-cubicX-cubicY);
	}

	public int getOffsetX()
	{
		//z + (x - (x&1)) / 2;
		return (((-cubicX-cubicY)+( cubicX - ( cubicX & 1 ) ))/2);
	}

	public int getOffsetY()
	{
		return cubicX;
	}

}


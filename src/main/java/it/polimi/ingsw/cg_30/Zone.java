package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.*;

public class Zone implements Serializable
{
	private Iterable<Sector> sectors;


	public  Map<Player,Sector> playersLocation;

	private Iterable <Boolean> hatchesStatus;
	

	public Iterable<Sector> getSectors()
	{
		throw new UnsupportedOperationException();
	}

	public void movePlayer(Player who, Sector where)
	{
		throw new UnsupportedOperationException();
	}

	public Sector getSector(Player player)
	{
		throw new UnsupportedOperationException();
	}

	public Iterable<Player> getPlayersInSector(Sector sec)
	{
		throw new UnsupportedOperationException();
	}

	private void lockHatch(int hatchNumber)
	{
		throw new UnsupportedOperationException();
	}

	public void isHatchLocked(int hatchNumber)
	{
		throw new UnsupportedOperationException();
	}

	public Iterable<Sector> reachableTargets(Sector from, Integer maxSteps)
	{
		throw new UnsupportedOperationException();
	}

	public boolean noMoreHatches()
	{
		throw new UnsupportedOperationException();
	}

}

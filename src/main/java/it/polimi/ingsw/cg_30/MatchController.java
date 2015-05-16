package it.polimi.ingsw.cg_30;

public class MatchController
{
	public TurnController currentTurn;

	public PartyController currentParty;

	private StackedDeck itemsDeck;
	private StackedDeck hatchesDeck;
	private StackedDeck sectorsDeck;

	
	private int turnCount;
	
	private ZoneController currentZone;
	
	

	public int getTurnCount()
	{
		throw new UnsupportedOperationException();
	}

	private void initMatch()
	{
		throw new UnsupportedOperationException();
	}

	private TurnController newTurn()
	{
		throw new UnsupportedOperationException();
	}
	
	public TurnController getCurrentTurn()
	{
		throw new UnsupportedOperationException();
	}

	public PartyController getCurrentParty()
	{
		throw new UnsupportedOperationException();
	}
	
	public PartyController getItemsDeck()
	{
		throw new UnsupportedOperationException();
	}
	
	public ZoneController getCurrentZone()
	{
		throw new UnsupportedOperationException();
	}
	
	public void incrementTurnCount(){
		turnCount++;
	}

}


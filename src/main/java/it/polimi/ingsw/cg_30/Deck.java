package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.Set;


public abstract class Deck
{
	protected Collection<Card> cards;

	private Set<Card> bucket;

	public void recycle()
	{
		throw new UnsupportedOperationException();
	}

	public void putIntoBucket(Card c)
	{
		throw new UnsupportedOperationException();
	}
	

	
}
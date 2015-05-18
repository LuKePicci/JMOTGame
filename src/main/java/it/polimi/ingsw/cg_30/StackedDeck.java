package it.polimi.ingsw.cg_30;

import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;

public class StackedDeck extends Deck {
	
	private Stack<Card> cards;

	public void shuffle() {
		throw new UnsupportedOperationException();
	}

	public Card pickCard() {
		throw new UnsupportedOperationException();
	}

	public StackedDeck newStackedDeckHatch() {
		throw new UnsupportedOperationException();
	}

	public StackedDeck newStackedDeckSector() {
		throw new UnsupportedOperationException();
	}

	public StackedDeck newStackedDeckItem() {
		throw new UnsupportedOperationException();
	}
	
	//"COSTRUTTORI"
	private StackedDeck(){
		this.cards = new Stack<Card>();
	}
	
	public StackedDeck StackedDeckHatch(){
		StackedDeck ex = new StackedDeck();
		ex.cards.push(new HatchCard());
		return ex;
		
	}

	@Override
	public Collection getCardCollection() {
		return cards;
	}

}

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

    @Override
    public Collection getCardCollection() {
        return cards;
    }

    // "COSTRUTTORI"
    private StackedDeck() {
        this.cards = new Stack<Card>();
    }

    public StackedDeck StackedDeckHatch() {
        StackedDeck ex = new StackedDeck();
        ex.cards.push(new HatchCard(HatchChance.Free));
        ex.cards.push(new HatchCard(HatchChance.Locked));
        return ex;
    }

    public StackedDeck StackedDeckSector() {
        StackedDeck ex = new StackedDeck();
        ex.cards.push(new SectorCard(SectorEvent.Silence));
        ex.cards.push(new SectorCard(SectorEvent.NoiseAny));
        ex.cards.push(new SectorCard(SectorEvent.NoiseYour));
        return ex;
    }

    public StackedDeck StackedDeckItem() {
        StackedDeck ex = new StackedDeck();
        ex.cards.push(new ItemCard(Item.Attack));
        ex.cards.push(new ItemCard(Item.Teleport));
        ex.cards.push(new ItemCard(Item.Sedatives));
        ex.cards.push(new ItemCard(Item.Spotlight));
        ex.cards.push(new ItemCard(Item.Defense));
        ex.cards.push(new ItemCard(Item.Adrenaline));
        return ex;
    }

}

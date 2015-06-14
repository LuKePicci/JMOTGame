package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Deck")
public class DeckViewModel<C extends Card> extends ViewModel {

    @XmlElement(name = "Cards")
    List<C> cards;

    public DeckViewModel(List<C> d) {
        this();
        this.cards = d;
    }

    private DeckViewModel() {
        // JAXB handled
        super(ViewType.DECK);
    }

    @Override
    public String toString() {
        return "DeckViewModel { cards: " + cards + " }";
    }

}

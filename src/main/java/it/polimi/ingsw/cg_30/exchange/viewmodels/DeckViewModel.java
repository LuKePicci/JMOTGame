package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Deck")
public class DeckViewModel<C extends Card> extends ViewModel {

    private static final long serialVersionUID = -8158974397380721778L;

    @XmlElement(name = "Cards")
    private List<C> cards;

    public DeckViewModel(List<C> d) {
        this();
        this.cards = d;
    }

    private DeckViewModel() {
        // JAXB handled
        super(ViewType.DECK);
    }

    public List<C> getPlayerCards() {
        return this.cards;
    }

    @Override
    public String toString() {
        return "DeckViewModel { cards: " + cards + " }";
    }

}

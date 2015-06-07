package it.polimi.ingsw.cg_30;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Deck")
public class DeckViewModel<C extends Card> extends ViewModel {

    @XmlElement(name = "Cards")
    List<C> cards;

    public DeckViewModel(Deck<C> d) {
        this.cards = new ArrayList<C>(d.getCardCollection());
    }

    @SuppressWarnings("unused")
    private DeckViewModel() {
        // JAXB handled
    }

}

package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class DeckViewModel.
 *
 * @param <C>
 *            the generic type
 */
@XmlRootElement(name = "Deck")
public class DeckViewModel<C extends Card> extends ViewModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8158974397380721778L;

    /** The cards. */
    @XmlElement(name = "Cards")
    private List<C> cards;

    /**
     * Instantiates a new deck view model.
     *
     * @param d
     *            the list
     */
    public DeckViewModel(List<C> d) {
        this();
        this.cards = d;
    }

    /**
     * Instantiates a new deck view model.
     */
    private DeckViewModel() {
        // JAXB handled
        super(ViewType.DECK);
    }

    /**
     * Gets the cards list.
     *
     * @return the cards list
     */
    public List<C> getPlayerCards() {
        return this.cards;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DeckViewModel { cards: " + cards + " }";
    }

}

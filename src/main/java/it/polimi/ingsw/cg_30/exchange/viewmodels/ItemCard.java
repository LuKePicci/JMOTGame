package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class ItemCard.
 */
@XmlRootElement(name = "Card")
public class ItemCard extends Card {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5619363213836716836L;

    /** The item on this card. */
    @XmlElement(name = "Item")
    private Item item;

    /**
     * Instantiates a new item card.
     *
     * @param item
     *            the item
     */
    public ItemCard(Item item) {
        this();
        this.item = item;
    }

    /**
     * Instantiates an empty item card (JAXB needings)
     */
    private ItemCard() {
        // JAXB handled
        super(CardType.ITEM);
    }

    /**
     * Gets the item.
     *
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "ItemCard { item: " + item + " }";
    }

}

package it.polimi.ingsw.cg_30;

/**
 * The Class ItemCard.
 */
public class ItemCard extends Card {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5619363213836716836L;

    /** The item. */
    private Item item;

    /**
     * Instantiates a new item card.
     *
     * @param item
     *            the item
     */
    public ItemCard(Item item) {
        this.item = item;
    }

    /**
     * Gets the item.
     *
     * @return the item
     */
    public Item getItem() {
        return item;
    }

}

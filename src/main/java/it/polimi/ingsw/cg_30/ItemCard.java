package it.polimi.ingsw.cg_30;

/**
 * The Class ItemCard.
 */
public class ItemCard extends Card {

    /** The item. */
    private Item item;

    /**
     * Gets the item.
     *
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Instantiates a new item card.
     *
     * @param item
     *            the item
     */
    public ItemCard(Item item) {
        this.item = item;
    }

}

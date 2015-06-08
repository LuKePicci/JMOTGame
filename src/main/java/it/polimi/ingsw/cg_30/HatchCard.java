package it.polimi.ingsw.cg_30;

/**
 * The Class HatchCard.
 */
public class HatchCard extends Card {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6339280173710451167L;

    /** The chance. */
    private HatchChance chance;

    /**
     * Instantiates a new hatch card.
     *
     * @param chance
     *            the chance
     */
    public HatchCard(HatchChance chance) {
        this.chance = chance;
    }

    /**
     * Instantiates an empty hatch card.
     */
    @SuppressWarnings("unused")
    private HatchCard() {
        // JAXB handled
    }

    /**
     * Gets the chance.
     *
     * @return the chance
     */
    public HatchChance getChance() {
        return chance;
    }

}

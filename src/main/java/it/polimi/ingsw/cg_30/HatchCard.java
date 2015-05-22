package it.polimi.ingsw.cg_30;

/**
 * The Class HatchCard.
 */
public class HatchCard extends Card {

    /** The chance. */
    private HatchChance chance;

    /**
     * Gets the chance.
     *
     * @return the chance
     */
    public HatchChance getChance() {
        return chance;
    }

    /**
     * Instantiates a new hatch card.
     *
     * @param chance
     *            the chance
     */
    public HatchCard(HatchChance chance) {
        this.chance = chance;
    }

}

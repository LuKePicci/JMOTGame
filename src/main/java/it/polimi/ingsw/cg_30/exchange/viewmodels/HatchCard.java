package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class HatchCard.
 */
@XmlRootElement(name = "Card")
public class HatchCard extends Card {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6339280173710451167L;

    /** The chance. */
    @XmlElement(name = "Chance")
    private HatchChance chance;

    /**
     * Instantiates a new hatch card.
     *
     * @param chance
     *            the chance
     */
    public HatchCard(HatchChance chance) {
        this();
        this.chance = chance;
    }

    /**
     * Instantiates an empty hatch card.
     */
    private HatchCard() {
        // JAXB handled
        super(CardType.HATCH);
    }

    /**
     * Gets the chance.
     *
     * @return the chance
     */
    public HatchChance getChance() {
        return chance;
    }

    @Override
    public String toString() {
        return "HatchCard { chance: " + chance + " }";
    }

}

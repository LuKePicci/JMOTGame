package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class PlayerCard.
 */
@XmlRootElement(name = "Card")
public class PlayerCard extends Card {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7504696064744265687L;

    /** The race. */
    @XmlElement(name = "Race")
    private PlayerRace race;

    /** The character. */
    @XmlElement(name = "Character")
    private PlayerCharacter character;

    /**
     * Instantiates a new player card.
     *
     * @param race
     *            the race
     * @param character
     *            the character
     */
    public PlayerCard(PlayerRace race, PlayerCharacter character) {
        this();
        this.race = race;
        this.character = character;
    }

    /**
     * Instantiates a new player card.
     */
    private PlayerCard() {
        // JAXB handled
        super(CardType.PLAYER);
    }

    /**
     * Gets the race.
     *
     * @return the race
     */
    public PlayerRace getRace() {
        return race;
    }

    /**
     * Gets the character.
     *
     * @return the character
     */
    public PlayerCharacter getCharacter() {
        return character;
    }

}

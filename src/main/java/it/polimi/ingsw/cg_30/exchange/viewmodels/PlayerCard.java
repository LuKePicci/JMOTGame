package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Card")
public class PlayerCard extends Card {

    private static final long serialVersionUID = 7504696064744265687L;

    @XmlElement(name = "Race")
    private PlayerRace race;

    @XmlElement(name = "Character")
    private PlayerCharacter character;

    public PlayerCard(PlayerRace race, PlayerCharacter character) {
        this();
        this.race = race;
        this.character = character;
    }

    private PlayerCard() {
        // JAXB handled
        super(CardType.PLAYER);
    }

    public PlayerRace getRace() {
        return race;
    }

    public PlayerCharacter getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return "PlayerCard { race: " + race + ", character: " + character
                + " }";
    }

}

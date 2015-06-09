package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCharacter;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;

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
        this.race = race;
        this.character = character;
    }

    public PlayerRace getRace() {
        return race;
    }

    public PlayerCharacter getCharacter() {
        return character;
    }

}

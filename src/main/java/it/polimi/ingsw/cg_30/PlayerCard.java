package it.polimi.ingsw.cg_30;

public class PlayerCard extends Card {

    private PlayerRace race;
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

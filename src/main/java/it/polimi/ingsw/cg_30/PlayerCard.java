package it.polimi.ingsw.cg_30;

public class PlayerCard extends Card {

    private PlayerRace race;
    private Character character;

    public PlayerCard(PlayerRace race, Character character) {
        this.race = race;
        this.character = character;
    }

    public PlayerRace getRace() {
        return race;
    }

    public Character getCharacter() {
        return character;
    }

}

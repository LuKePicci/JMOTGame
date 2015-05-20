package it.polimi.ingsw.cg_30;

public class HatchCard extends Card {
    private HatchChance chance;

    public HatchChance getChance() {
        return chance;
    }

    public HatchCard(HatchChance chance) {
        this.chance = chance;
    }

}

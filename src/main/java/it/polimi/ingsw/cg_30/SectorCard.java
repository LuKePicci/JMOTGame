package it.polimi.ingsw.cg_30;

public class SectorCard extends Card {
    private SectorEvent event;

    public SectorEvent getEvent() {
        return event;
    }

    public SectorCard(SectorEvent event) {
        this.event = event;
    }

}

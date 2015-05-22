package it.polimi.ingsw.cg_30;

/**
 * The Class SectorCard.
 */
public class SectorCard extends Card {

    /** The event. */
    private SectorEvent event;

    /**
     * Gets the event.
     *
     * @return the event
     */
    public SectorEvent getEvent() {
        return event;
    }

    /**
     * Instantiates a new sector card.
     *
     * @param event
     *            the event
     */
    public SectorCard(SectorEvent event) {
        this.event = event;
    }

}

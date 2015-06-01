package it.polimi.ingsw.cg_30;

/**
 * The Class SectorCard.
 */
public class SectorCard extends Card {

    private static final long serialVersionUID = 5211772687967011728L;

    /** The event. */
    private SectorEvent event;
    private boolean objectSymbol;

    /**
     * Instantiates a new sector card.
     *
     * @param event
     *            the event
     */
    public SectorCard(SectorEvent event, boolean objectSymbol) {
        this.event = event;
        this.objectSymbol = objectSymbol;
    }

    /**
     * Instantiates an empty sector card.
     */
    public SectorCard() {
    }

    /**
     * Gets the event.
     *
     * @return the event
     */
    public SectorEvent getEvent() {
        return event;
    }

    public boolean hasObjectSymbol() {
        return objectSymbol;
    }

}

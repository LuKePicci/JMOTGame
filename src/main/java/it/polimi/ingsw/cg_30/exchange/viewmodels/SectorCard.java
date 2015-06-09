package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class SectorCard.
 */
@XmlRootElement(name = "Card")
public class SectorCard extends Card {

    private static final long serialVersionUID = 5211772687967011728L;

    /** The event. */
    @XmlElement(name = "Event")
    private SectorEvent event;

    @XmlElement(name = "AttachedItem")
    private boolean attachedItem;

    /**
     * Instantiates a new sector card.
     *
     * @param event
     *            the event
     */
    public SectorCard(SectorEvent event, boolean attachedItem) {
        this();
        this.event = event;
        this.attachedItem = attachedItem;
    }

    /**
     * Instantiates an empty sector card.
     */
    private SectorCard() {
        // JAXB handled
        super(CardType.SECTOR);
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
        return attachedItem;
    }

}

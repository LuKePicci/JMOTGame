package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * The Class ViewModel.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ZoneViewModel.class, ChatViewModel.class, PartyViewModel.class,
        DeckViewModel.class, PlayerViewModel.class, Card.class,
        TurnViewModel.class, SectorViewModel.class })
public abstract class ViewModel implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6011241572950363177L;

    /** My id. */
    @XmlElement(name = "UUID")
    protected UUID myID;

    /** The target view. */
    @XmlAttribute(name = "TargetView")
    private ViewType targetView;

    /** The updated at. */
    protected Date updatedAt;

    /**
     * Instantiates a new view model.
     */
    protected ViewModel() {
        this.setDate(new Date());
    }

    /**
     * Instantiates a new view model.
     *
     * @param t
     *            the t
     */
    protected ViewModel(ViewType t) {
        this.targetView = t;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    @XmlAttribute(name = "Date")
    public Date getDate() {
        return this.updatedAt;
    }

    /**
     * Sets the date.
     *
     * @param d
     *            the new date
     */
    public void setDate(Date d) {
        this.updatedAt = d;
    }

    /**
     * Gets the my id.
     *
     * @return the my id
     */
    public UUID getMyID() {
        return this.myID;
    }

    /**
     * Sets the my id.
     *
     * @param id
     *            the new my id
     */
    public void setMyID(UUID id) {
        this.myID = id;
    }

    /**
     * Gets the view type.
     *
     * @return the view type
     */
    public ViewType getViewType() {
        return this.targetView;
    }
}

package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ZoneViewModel.class, ChatViewModel.class, PartyViewModel.class,
        DeckViewModel.class, PlayerViewModel.class, Card.class,
        TurnViewModel.class })
public abstract class ViewModel implements Serializable {

    private static final long serialVersionUID = -6011241572950363177L;

    @XmlElement(name = "UUID")
    protected UUID myID;

    protected Date updatedAt;

    protected ViewModel() {
        this.setDate(new Date());
    }

    @XmlAttribute(name = "Date")
    public Date getDate() {
        return this.updatedAt;
    }

    public void setDate(Date d) {
        this.updatedAt = d;
    }

    public UUID getMyID() {
        return this.myID;
    }

    public void setMyID(UUID id) {
        this.myID = id;
    }

    @XmlAttribute(name = "TargetView")
    private ViewType targetView;

    protected ViewModel(ViewType t) {
        this.targetView = t;
    }

    public ViewType getViewType() {
        return this.targetView;
    }
}

package it.polimi.ingsw.cg_30.exchange.messaging;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ActionRequest.class, ChatRequest.class, PartyRequest.class,
        JoinRequest.class })
public abstract class RequestModel implements Serializable {

    private static final long serialVersionUID = 5763649894549251901L;

    @XmlElement(name = "UUID")
    protected UUID myID;

    protected Date updatedAt;

    protected RequestModel() {
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

    public abstract void process(IDelivery mc);
}

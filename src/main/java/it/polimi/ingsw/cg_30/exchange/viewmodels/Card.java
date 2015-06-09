package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ SectorCard.class, HatchCard.class, ItemCard.class,
        PlayerCard.class })
public abstract class Card extends ViewModel implements Serializable {

    private static final long serialVersionUID = -5988723386578576261L;

    @XmlAttribute(name = "Type")
    private CardType type;

    public Card(CardType t) {
        this();
        this.type = t;
    }

    private Card() {
        super(ViewType.CARD);
    }

    public CardType getType() {
        return this.type;
    }

}

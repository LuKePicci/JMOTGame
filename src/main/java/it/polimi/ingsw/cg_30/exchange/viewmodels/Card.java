package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * The Class Card.
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ SectorCard.class, HatchCard.class, ItemCard.class,
        PlayerCard.class })
public abstract class Card extends ViewModel implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5988723386578576261L;

    /** The type. */
    @XmlAttribute(name = "Type")
    private CardType type;

    /**
     * Instantiates a new card.
     *
     * @param t
     *            the CardType
     */
    public Card(CardType t) {
        this();
        this.type = t;
    }

    /**
     * Instantiates a new card.
     */
    private Card() {
        super(ViewType.CARD);
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public CardType getType() {
        return this.type;
    }

}

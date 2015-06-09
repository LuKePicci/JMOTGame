package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ZoneViewModel.class, ChatViewModel.class, PartyViewModel.class,
        DeckViewModel.class, PlayerViewModel.class, Card.class,
        TurnViewModel.class })
public abstract class ViewModel {

    @XmlAttribute(name = "TargetView")
    ViewType targetView;

    protected ViewModel(ViewType t) {
        this.targetView = t;
    }
}

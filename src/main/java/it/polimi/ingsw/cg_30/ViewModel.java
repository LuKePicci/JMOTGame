package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ZoneViewModel.class, ChatViewModel.class, PartyViewModel.class,
        DeckViewModel.class })
public abstract class ViewModel {

}

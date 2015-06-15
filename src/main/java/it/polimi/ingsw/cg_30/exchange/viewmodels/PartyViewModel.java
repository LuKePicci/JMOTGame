package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Party")
public class PartyViewModel extends ViewModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1432131216732742865L;

    @XmlAttribute(name = "Name")
    String partyName;

    @XmlElement(name = "Members")
    List<ViewModel> players;

    public PartyViewModel(String name, List<ViewModel> pvmList) {
        this();

        this.partyName = name;

        this.players = pvmList;
    }

    private PartyViewModel() {
        // JAXB handled
        super(ViewType.PARTY);
    }

    @Override
    public String toString() {
        return "PartyViewModel { partyName: " + partyName + ", players: "
                + players + " }";
    }

}

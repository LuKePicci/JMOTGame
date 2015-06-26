package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class PartyViewModel.
 */
@XmlRootElement(name = "Party")
public class PartyViewModel extends ViewModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1432131216732742865L;

    /** The party name. */
    @XmlAttribute(name = "Name")
    private String partyName;

    /** The players. */
    @XmlElement(name = "Members")
    private List<PlayerViewModel> players;

    /**
     * Instantiates a new party view model.
     *
     * @param name
     *            the name
     * @param pvmList
     *            the player view model list
     */
    public PartyViewModel(String name, List<PlayerViewModel> pvmList) {
        this();

        this.partyName = name;

        this.players = pvmList;
    }

    /**
     * Instantiates a new party view model.
     */
    private PartyViewModel() {
        // JAXB handled
        super(ViewType.PARTY);
    }

    /**
     * Gets the party name.
     *
     * @return the party name
     */
    public String getPartyName() {
        return this.partyName;
    }

    /**
     * Gets the party players.
     *
     * @return the party players
     */
    public List<PlayerViewModel> getPartyPlayers() {
        return this.players;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PartyViewModel { partyName: " + partyName + ", players: "
                + players + " }";
    }

}

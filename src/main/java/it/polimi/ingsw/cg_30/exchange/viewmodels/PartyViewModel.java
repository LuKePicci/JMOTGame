package it.polimi.ingsw.cg_30.exchange.viewmodels;

import it.polimi.ingsw.cg_30.gamemanager.model.Party;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

class PlayerViewModel {

    @XmlElement(name = "PlayerIndex")
    int index;

    @XmlElement(name = "Name")
    String name;

    @XmlElement(name = "KillsCount")
    int kills;

    @XmlAttribute(name = "UUID")
    UUID playerId;

    public PlayerViewModel(Player pl, UUID id) {
        this.index = pl.getIndex();
        this.name = pl.getName();
        this.kills = pl.getKillsCount();
        this.playerId = id;
    }
}

@XmlRootElement(name = "Party")
public class PartyViewModel extends ViewModel {

    @XmlAttribute(name = "Name")
    String partyName;

    @XmlElement(name = "Members")
    List<PlayerViewModel> players;

    public PartyViewModel(Party p) {

        this.partyName = p.getName();

        Map<Player, UUID> members = p.getMembers();

        for (Player pl : members.keySet())
            this.players.add(new PlayerViewModel(pl, members.get(pl)));
    }

    @SuppressWarnings("unused")
    private PartyViewModel() {
        // JAXB handled
    }

}

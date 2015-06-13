package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Player")
public class PlayerViewModel extends ViewModel {

    @XmlElement(name = "PlayerIndex")
    int index;

    @XmlElement(name = "Name")
    String name;

    @XmlElement(name = "KillsCount")
    int killsCount;

    public PlayerViewModel(int i, String nick, int kills) {
        this();
        this.index = i;
        this.name = nick;
        this.killsCount = kills;
    }

    private PlayerViewModel() {
        // JAXB handled
        super(ViewType.PLAYER);
    }

}
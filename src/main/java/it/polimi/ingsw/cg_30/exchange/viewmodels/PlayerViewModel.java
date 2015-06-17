package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Player")
public class PlayerViewModel extends ViewModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3886929209108097282L;

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

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }

    public int getKillsCount() {
        return this.killsCount;
    }

    @Override
    public String toString() {
        return "PlayerViewModel { index: " + index + ", name: " + name
                + ", killsCount: " + killsCount + " }";
    }

}
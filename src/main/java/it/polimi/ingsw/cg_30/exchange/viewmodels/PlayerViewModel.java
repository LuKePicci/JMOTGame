package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class PlayerViewModel.
 */
@XmlRootElement(name = "Player")
public class PlayerViewModel extends ViewModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3886929209108097282L;

    /** The index. */
    @XmlElement(name = "PlayerIndex")
    int index;

    /** The name. */
    @XmlElement(name = "Name")
    String name;

    /** The kills count value. */
    @XmlElement(name = "KillsCount")
    int killsCount;

    /** The number of item cards. */
    @XmlElement(name = "NumOfItemCards")
    int numOfItemCards;

    /**
     * Instantiates a new player view model.
     *
     * @param i
     *            player's index
     * @param nick
     *            player's nick
     * @param kills
     *            player's kills number
     * @param num
     *            number of player's item cards
     */
    public PlayerViewModel(int i, String nick, int kills, int num) {
        this();
        this.index = i;
        this.name = nick;
        this.killsCount = kills;
        this.numOfItemCards = num;
    }

    /**
     * Instantiates a new player view model.
     */
    private PlayerViewModel() {
        // JAXB handled
        super(ViewType.PLAYER);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the index.
     *
     * @return the index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Gets the kills count value.
     *
     * @return the kills count value
     */
    public int getKillsCount() {
        return this.killsCount;
    }

    /**
     * Gets the number of item cards.
     *
     * @return the number of item cards
     */
    public int getNumOfItemCards() {
        return this.numOfItemCards;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PlayerViewModel { index: " + index + ", name: " + name
                + ", killsCount: " + killsCount + ", numOfItemCards: "
                + numOfItemCards + " }";
    }

}
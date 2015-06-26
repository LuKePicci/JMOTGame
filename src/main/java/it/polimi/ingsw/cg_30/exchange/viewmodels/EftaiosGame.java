package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class EftaiosGame.
 */
@XmlRootElement(name = "Game")
public class EftaiosGame extends Game {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8556562726561842989L;

    /** The Constant TYPE. */
    public static final GameType TYPE = GameType.EFTAIOS;

    /** The Constant MAX_PLAYERS. */
    public static final int MAX_PLAYERS = 8;

    /** The Constant DEFAULT_MAP. */
    public static final String DEFAULT_MAP = "galvani";

    /** The map name. */
    private String mapName;

    /**
     * Instantiates a new eftaios game.
     *
     * @param mapName
     *            the map name
     */
    public EftaiosGame(String mapName) {
        super(EftaiosGame.TYPE);
        this.mapName = mapName;
    }

    /**
     * Instantiates a new eftaios game.
     */
    public EftaiosGame() {
        super(EftaiosGame.TYPE);
        this.mapName = DEFAULT_MAP;
    }

    /**
     * Gets the map name.
     *
     * @return the map name
     */
    @XmlElement(name = "MapName")
    public String getMapName() {
        return this.mapName;
    }

    /**
     * Sets the map name.
     *
     * @param name
     *            the new map name
     */
    @SuppressWarnings("unused")
    private void setMapName(String name) {
        this.mapName = name;
    }

    /**
     * @see it.polimi.ingsw.cg_30.exchange.viewmodels.Game#sameGame(it.polimi.ingsw.cg_30.exchange.viewmodels.Game)
     */
    @Override
    public boolean sameGame(Game g) {
        return this.type == g.getGameType()
                && this.mapName
                        .equalsIgnoreCase(((EftaiosGame) g).getMapName());
    }

    /**
     * @see it.polimi.ingsw.cg_30.exchange.viewmodels.Game#getMaxPlayers()
     */
    @Override
    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

}

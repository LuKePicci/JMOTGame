package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Game")
public class EftaiosGame extends Game {

    private static final long serialVersionUID = -8556562726561842989L;

    public static final GameType TYPE = GameType.EFTAIOS;
    public static final int MAX_PLAYERS = 8;

    public static final String DEFAULT_MAP = "galvani";

    private String mapName;

    public EftaiosGame(String mapName) {
        super(EftaiosGame.TYPE);
        this.mapName = mapName;
    }

    public EftaiosGame() {
        super(EftaiosGame.TYPE);
        this.mapName = DEFAULT_MAP;
    }

    @XmlElement(name = "MapName")
    public String getMapName() {
        return this.mapName;
    }

    @Override
    public boolean sameGame(Game g) {
        return this.type == g.getGameType()
                && this.mapName == ((EftaiosGame) g).getMapName();
    }

    @Override
    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

}

package it.polimi.ingsw.cg_30;

import java.io.Serializable;

public abstract class Game implements Serializable {

    private static final long serialVersionUID = -3498670019399513401L;

    protected GameType type;

    public GameType getGameType() {
        return this.type;
    }

    protected Game(GameType t) {
        this.type = t;
    }

    public abstract boolean sameGame(Game g);

    public abstract int getMaxPlayers();
}

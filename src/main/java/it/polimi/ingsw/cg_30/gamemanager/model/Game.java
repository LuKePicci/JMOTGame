package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.GameType;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ EftaiosGame.class })
public abstract class Game implements Serializable {

    private static final long serialVersionUID = -3498670019399513401L;

    @XmlElement(name = "Type")
    protected GameType type;

    protected Game(GameType t) {
        this.type = t;
    }

    public GameType getGameType() {
        return this.type;
    }

    public abstract boolean sameGame(Game g);

    public abstract int getMaxPlayers();
}

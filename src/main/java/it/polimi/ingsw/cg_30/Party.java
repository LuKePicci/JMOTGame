package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Party implements Serializable {

    private static final long serialVersionUID = 228808363452233075L;

    private Map<Player, AcceptPlayer> members;
    private String name;
    private Game currentGame;
    private Boolean privateParty;

    public String getName() {
        return name;
    }

    public Game getGame() {
        return this.currentGame;
    }

    public Boolean isPrivate() {
        return this.privateParty;
    }

    public Map<Player, AcceptPlayer> getMembers() {
        return members;
    }

    public Party addToParty(AcceptPlayer client) {
        members.put(new Player(), client);
        return this;
    }

    public Party(String name, Game g, Boolean privateParty) {
        this.name = name;
        this.privateParty = privateParty;
        this.currentGame = g;
        this.members = new HashMap<Player, AcceptPlayer>();
    }

}
package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Party implements Serializable {

    private static final long serialVersionUID = 228808363452233075L;

    private Map<Player, UUID> members;
    private String name;
    private Game currentGame;
    private Boolean privateParty;

    public Party(String name, Game g, Boolean privateParty) {
        this.name = name;
        this.privateParty = privateParty;
        this.currentGame = g;
        this.members = new HashMap<Player, UUID>();
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return this.currentGame;
    }

    public Boolean isPrivate() {
        return this.privateParty;
    }

    public Map<Player, UUID> getMembers() {
        return members;
    }

    public Party addToParty(UUID client) {
        members.put(new Player(), client);
        return this;
    }

}
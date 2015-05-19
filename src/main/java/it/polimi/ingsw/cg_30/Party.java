package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Party implements Serializable {

	private static final long serialVersionUID = 228808363452233075L;

	private Map<Player, AcceptPlayer> members;
	private String name;

	public String getName() {
		return name;
	}

	public Map<Player, AcceptPlayer> getMembers() {
		return members;
	}

	public void addToParty(AcceptPlayer client) {
		members.put(new Player(), client);
	}

	public Party(String name) {
		this.name = name;
		this.members = new HashMap<Player, AcceptPlayer>();
	}

}
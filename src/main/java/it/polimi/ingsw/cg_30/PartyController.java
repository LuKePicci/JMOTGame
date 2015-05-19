package it.polimi.ingsw.cg_30;

import java.util.HashMap;
import java.util.Map;

public class PartyController {
	private static Map<Party, PartyController> parties = new HashMap<Party, PartyController>();

	private Party currentParty;

	private MatchController currentMatch;

	public PartyController(AcceptPlayer leader) {
		this.currentParty = new Party(leader.getName());
		this.addToParty(leader);
	}

	public void processRequest(PartyMessage request) {
		throw new UnsupportedOperationException();
	}

	public void addToParty(AcceptPlayer player) {
		Player p = new Player();
		this.currentParty.getMembers().put(p, player);
	}

	public Party getCurrentParty() {
		return this.currentParty;
	}

	public MatchController getCurrentMatch() {
		return currentMatch;
	}

}

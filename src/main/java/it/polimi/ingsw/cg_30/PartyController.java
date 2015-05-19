package it.polimi.ingsw.cg_30;

import java.util.HashMap;
import java.util.Map;

public class PartyController {
	private static Map<Party, PartyController> parties = new HashMap<Party, PartyController>();

	private Party Party;

	private MatchController currentMatch;

	public PartyController(Player leader) {
		this.currentParty = new Party();
		this.addToParty(leader);
	}

	public void processRequest(PartyMessage request) {
		throw new UnsupportedOperationException();
	}

	private void addToParty(Player player) {
		this.currentParty.getMembers().add(player);
	}

	public Party getCurrentParty() {
		return this.currentParty;
	}

	public MatchController getCurrentMatch() {
		return currentMatch;
	}

}

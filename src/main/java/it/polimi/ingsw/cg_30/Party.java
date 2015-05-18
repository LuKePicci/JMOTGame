package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Party implements Serializable {
	private Map<Player, MessageController> members;
	private String name;

	public String getName() {
		return name;
	}

	public Map<Player, MessageController> getMembers() {
		return members;
	}
	
	public void addToParty(MessageController message){
		members.put(new Player(), message);
	}
	
	public Party(String name){
		this.name=name;
		this.members=new HashMap<Player, MessageController>();
	}

}
package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.Iterator;

public class Party implements Serializable{
	private Iterable<Player> members;

	private String name;

	public String getName() {
		return name;
	}

	public Iterable<Player> getMembers() {
		return members;
	}

	// CREAZIONE DI UN NUOVO PARTY DATO UN GIOCATORE
	public Party(Player newPlayer) {
		// this.members=new //da implementare, forse posso usare add
	}

}
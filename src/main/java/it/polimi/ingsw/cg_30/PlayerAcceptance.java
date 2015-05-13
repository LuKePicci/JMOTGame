package it.polimi.ingsw.cg_30;

public abstract class PlayerAcceptance extends Thread {

	abstract protected void acceptance();
	
	@Override
	public void run(){
		this.acceptance();
	}

}

package it.polimi.ingsw.cg_30;

import javax.naming.OperationNotSupportedException;

public class RmiAcceptance extends PlayerAcceptance {

	public RmiAcceptance() {
		this.start();
	}


	@Override
	protected void acceptance() {
		try {
			throw new OperationNotSupportedException();
		} catch (OperationNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

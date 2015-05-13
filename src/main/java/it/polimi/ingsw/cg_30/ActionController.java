package it.polimi.ingsw.cg_30;


public abstract class ActionController
{

	public abstract boolean isValid();

	public abstract ActionMessage processAction();

	public ActionController getStrategy(ActionMessage act)
	{
		throw new UnsupportedOperationException();
	}

}


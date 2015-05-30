package it.polimi.ingsw.cg_30;

public abstract class ActionController {

    /*
     * LEGGIMI NB: l'alieno che attacca non deve pescare la carta settore,
     * quindi in caso di attacco l'rodine delle operazioni da eseguire è:
     * isValid di move; settare l'attributo silencedForse di turn a true;
     * processAction di move; isValid di Attack(inutile in teoria);
     * processAction di Attack.
     */

    public abstract boolean isValid();

    public abstract ActionMessage processAction();

    public ActionController getStrategy(ActionMessage act) {
        throw new UnsupportedOperationException();
    }

}

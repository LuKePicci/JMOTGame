package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class Move extends ActionController {

    private Player player;
    private Sector target;
    private MatchController matchController;

    public Move(MatchController matchController, Sector target) {
        this.player = matchController.getCurrentTurn().getTurn()
                .getCurrentPlayer();
        this.target = target;
        this.matchController = matchController;

    }

    @Override
    public boolean isValid() {
        // TO DO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        if (matchController.getCurrentTurn().getTurn().getMustMove()) {
            Set<Sector> reachableSectors = new HashSet<Sector>();
            int maxSteps = matchController.getCurrentTurn().getTurn()
                    .getMaxSteps();
            reachableSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(target, maxSteps);
            if (reachableSectors.contains(target)) {
                // anche se un settore è raggiungibile devo assicurarmi che non
                // sia (scialuppa/)settore umani/settore alieni
                // TO DO è possibile che i seguenti test non siano necessari a
                // seconda che reachableTargets ritorni o meno i settori start e
                // sciluppa
                if ((!target.getType().equals(SectorType.ALIENS_START))
                        && (!target.getType().equals(SectorType.HUMANS_START))) {
                    if (player.getIdentity().equals(PlayerRace.HUMAN)) {
                        return true; // gli umani posso andare sulle scialuppe
                    } else if (!target.getType()
                            .equals(SectorType.ESCAPE_HATCH)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ActionMessage processAction() {
        // TO DO rimuovere la seguente riga
        return null;

        // TO DO il controllo con isvalid lo eseguo esternamente prima di
        // chiamare processAction

    }

}

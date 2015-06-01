package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class Attack extends ActionController {

    private Player player;
    private MatchController matchController;

    public Attack(MatchController matchController) {
        this.matchController = matchController;
        this.player = matchController.getCurrentTurn().getTurn()
                .getCurrentPlayer();
    }

    @Override
    public boolean isValid() {
        // TO DO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        return matchController.getCurrentTurn().getTurn().getCanAttack();
    }

    @Override
    public ActionMessage processAction() {
        // TO DO il controllo con isvalid lo eseguo esternamente prima di
        // chiamare processAction
        // prendo l'elenco dei giocatori morti
        Sector sec = new Sector(null, null);
        sec = matchController.getZoneController().getCurrentZone()
                .getCell(player);
        Set<Player> dead = new HashSet<Player>();
        dead = (Set<Player>) matchController.getZoneController()
                .getCurrentZone().getPlayersInSector(sec);
        dead.remove(player); // devo evitare di uccidere player
        // uccido i giocatori
        for (Player pl : dead) {
            matchController.killed(pl);
            // se un alieno uccide un umano incremento il contatore
            if (player.getIdentity().getRace().equals(PlayerRace.ALIEN)) {
                if (pl.getIdentity().getRace().equals(PlayerRace.HUMAN)) {
                    player.incrementKillsCount();
                }
            }
            // se un umano uccide un alieno incremento il contatore
            if (player.getIdentity().getRace().equals(PlayerRace.HUMAN)) {
                if (pl.getIdentity().getRace().equals(PlayerRace.ALIEN)) {
                    player.incrementKillsCount();
                }
            }
        }
        // verifico se la partita è finita
        matchController.checkEndGame();
        // TO DO rimuovere la seguente riga
        return null;

    }

}

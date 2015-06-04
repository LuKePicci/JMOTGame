package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class Attack extends ActionController {

    private Player player;
    private MatchController matchController;

    public Attack(MatchController matchController) {
        this.matchController = matchController;
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
    }

    @Override
    public boolean isValid() { // funzione ad uso esclusivo dell'alieno
        // TO DO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        if ((matchController.getTurnController().getTurn().getCanAttack() == true)
                && (matchController.getTurnController().getTurn().getMustMove() == false))// se
                                                                                          // pesco
                                                                                          // non
                                                                                          // posso
                                                                                          // attaccare
            return true;
        else
            return false;
    }

    @Override
    public void processAction() {
        // TO DO il controllo con isvalid lo eseguo esternamente prima di
        // chiamare processAction
        // prendo l'elenco dei giocatori morti
        Sector sec = new Sector(null, null);
        sec = matchController.getZoneController().getCurrentZone()
                .getCell(player);
        Set<Player> dead = new HashSet<Player>();
        dead = matchController.getZoneController().getCurrentZone()
                .getPlayersInSector(sec);
        dead.remove(player); // devo evitare di uccidere player
        // uccido i giocatori
        for (Player kp : dead) {
            matchController.killed(kp);
            // se un alieno uccide un umano incremento il contatore
            if ((PlayerRace.ALIEN.equals(player.getIdentity().getRace()))
                    && (PlayerRace.HUMAN.equals(kp.getIdentity().getRace()))) {
                player.incrementKillsCount();
            }
            // se un umano uccide un alieno incremento il contatore
            else if ((PlayerRace.HUMAN.equals(player.getIdentity().getRace()))
                    && (PlayerRace.ALIEN.equals(kp.getIdentity().getRace()))) {
                player.incrementKillsCount();
            }
        }
        // impedisco di attaccare di nuovo
        matchController.getTurnController().getTurn().setCanAttack(false);
        // ho risolto gli effetti del settore pericoloso
        matchController.getTurnController().getTurn().setIsSecDangerous(false);
        // verifico se la partita è finita
        matchController.checkEndGame();
        // TO DO invio l'ActionMessage
    }

}

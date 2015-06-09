package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;

import java.util.Set;

/**
 * The Class Attack.
 */
public class Attack extends ActionController {

    public Attack() {
        // necessario per istanziare l'azione
    }

    // TODO verificare: prob questo costruttore non funziona bene
    // costruttore ad uso di UseCard
    public Attack(MatchController matchController) {
        this.matchController = matchController;
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if the attack is valid
     */
    @Override
    public boolean isValid() { // funzione ad uso esclusivo dell'alieno
        // TODO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        if ((matchController.getTurnController().getTurn().getCanAttack() == true)
                && (matchController.getTurnController().getTurn().getMustMove() == false))
            return true;
        else
            return false;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        // prendo l'elenco dei giocatori morti
        Sector sec = matchController.getZoneController().getCurrentZone()
                .getCell(player);
        Set<Player> dead = matchController.getZoneController().getCurrentZone()
                .getPlayersInSector(sec);
        dead.remove(player); // devo evitare di uccidere player
        // uccido i giocatori
        for (Player kp : dead) {
            // se un alieno uccide un umano incremento il contatore
            if ((PlayerRace.ALIEN.equals(player.getIdentity().getRace()))
                    && (PlayerRace.HUMAN.equals(kp.getIdentity().getRace()))) {
                player.incrementKillsCount();
                matchController.getTurnController().getTurn()
                        .changeHumanKilled(1);
            }
            // se un umano uccide un alieno incremento il contatore
            else if ((PlayerRace.HUMAN.equals(player.getIdentity().getRace()))
                    && (PlayerRace.ALIEN.equals(kp.getIdentity().getRace()))) {
                player.incrementKillsCount();
            }
            matchController.killed(kp);
        }
        // impedisco di attaccare di nuovo
        matchController.getTurnController().getTurn().setCanAttack(false);
        // ho risolto gli eventuali effetti del settore pericoloso
        matchController.getTurnController().getTurn().setIsSecDangerous(false);
        // verifico se la partita è finita
        matchController.checkEndGame();
        // TODO invio l'ActionMessage
    }

}

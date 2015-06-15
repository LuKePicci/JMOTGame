package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.Set;

/**
 * The Class Attack.
 */
public class Attack extends ActionController {

    /**
     * Checks the legality of this action.
     * 
     * @return true if the attack is valid
     */
    @Override
    public boolean isValid() {
        // method to be used only for the alien
        return this.matchController.getTurnController().getTurn()
                .getCanAttack()
                && !this.matchController.getTurnController().getTurn()
                        .getMustMove();
    }

    /**
     * Executes the action.
     * 
     * @throws DisconnectedException
     */
    @Override
    public void processAction() throws DisconnectedException {
        int killsCountPrecedent = this.matchController.getTurnController()
                .getTurn().getCurrentPlayer().getKillsCount();
        Sector sec = this.matchController.getZoneController().getCurrentZone()
                .getCell(player);
        // notify the attack
        this.notifyInChatByCurrentPlayer("ATTACK in " + sec.toString());
        // take the list of dead plyers
        Set<Player> dead = this.matchController.getZoneController()
                .getCurrentZone().getPlayersInSector(sec);
        dead.remove(player); // current player must not be killed
        // kill the players
        for (Player kp : dead) {
            // if an alien kills a human the counter will be increased
            if ((PlayerRace.ALIEN.equals(this.player.getIdentity().getRace()))
                    && (PlayerRace.HUMAN.equals(kp.getIdentity().getRace()))) {
                this.player.incrementKillsCount();
                this.matchController.getTurnController().getTurn()
                        .changeHumanKilled(1);
            }
            // if a human kills an alien the counter will be increased
            else if ((PlayerRace.HUMAN.equals(this.player.getIdentity()
                    .getRace()))
                    && (PlayerRace.ALIEN.equals(kp.getIdentity().getRace()))) {
                this.player.incrementKillsCount();
            }
            this.matchController.killed(kp);
        }
        // tell the alien he can now move from one to three steps (if he
        // actually can)
        if (PlayerRace.ALIEN.equals(this.player.getIdentity().getRace())
                && this.player.getKillsCount() > 0 && killsCountPrecedent == 0) {
            this.notifyCurrentPlayerByServer("FROM NOW YOU CAN CROSS THREE SECTORS DURING YOUR MOVEMENT");
        }
        // can't attack twice
        this.matchController.getTurnController().getTurn().setCanAttack(false);
        // dangerous sector effect solved (if there was)
        this.matchController.getTurnController().getTurn()
                .setIsSecDangerous(false);
        // check if the game is ended
        this.matchController.checkEndGame();
    }

}

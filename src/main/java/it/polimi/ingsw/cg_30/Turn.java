package it.polimi.ingsw.cg_30;

/**
 * The Class Turn.
 */
public class Turn {

    private boolean canAttack;
    private boolean mustMove;
    private int maxSteps;
    private boolean silenceForced;
    private Player currentPlayer;
    private boolean mustDiscard;

    // costruttore che prepara turno basandosi su un giocatore
    public Turn(Player player) {
        if (player.getIdentity().getRace() == PlayerRace.ALIEN) {// alieno
            this.canAttack = true;
            if (player.getKillsCount() > 0) {
                this.maxSteps = 3;// alieno che ha già ucciso almeno un umano
            } else {
                this.maxSteps = 2;// alieno che non ha ancora uccido umani
            }
        } else {// umano
            this.canAttack = false;
            this.maxSteps = 1;
        }
        this.mustMove = true;
        this.currentPlayer = player;
        this.silenceForced = false;
        this.mustDiscard = false;
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public boolean getMustMove() {
        return mustMove;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public boolean getSilenceForced() {
        return silenceForced;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getMustDiscard() {
        return mustDiscard;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public void setSilenceForced(boolean silenceForced) {
        this.silenceForced = silenceForced;
    }

    public void setMustDiscard(boolean mustDiscard) {
        this.mustDiscard = mustDiscard;
    }

    public void setMaxSteps(int steps) {
        this.maxSteps = steps;
    }

}

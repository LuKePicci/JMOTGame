package it.polimi.ingsw.cg_30;

/**
 * The Class Turn.
 */
public class Turn {

    /** The can attack. */
    private boolean canAttack;

    /** The must move. */
    private boolean mustMove;

    /** The max steps. */
    private int maxSteps;

    /** The current player. */
    private Player currentPlayer;

    /** The must discard. */
    private boolean mustDiscard;

    /** The silence forced. */
    private boolean silenceForced; // indica che non deve pescare una carta
                                   // settore
    /** The is sec dangerous. */
    private boolean isSecDangerous;// indica se devo risolvere gli effetti di un
                                   // settore pericoloso in cui player è finito
                                   // dopo il movimento

    /** The human killed. */
    private int humanKilled; // indica quanti umano sono stato uccisi da alieni
                             // in questo turno

    /**
     * Instantiates a new turn based on the player "player".
     *
     * @param player
     *            the player
     */
    public Turn(Player player) {
        if (PlayerRace.ALIEN == player.getIdentity().getRace()) {// alieno
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
        this.isSecDangerous = false;
        this.humanKilled = 0;
    }

    /**
     * Gets the can attack.
     *
     * @return if the player can attack
     */
    public boolean getCanAttack() {
        return canAttack;
    }

    /**
     * Gets the must move.
     *
     * @return if the player must move
     */
    public boolean getMustMove() {
        return mustMove;
    }

    /**
     * Gets the max steps.
     *
     * @return the max steps
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Gets the silence forced.
     *
     * @return if the silence is forced
     */
    public boolean getSilenceForced() {
        return silenceForced;
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the must discard.
     *
     * @return if the player must discard
     */
    public boolean getMustDiscard() {
        return mustDiscard;
    }

    /**
     * Gets is sec dangerous.
     *
     * @return if sec is dangerous
     */
    public boolean getIsSecDangerous() {
        return isSecDangerous;
    }

    /**
     * Gets the human killed value.
     *
     * @return the human killed value
     */
    public int getHumanKilled() {
        return this.humanKilled;
    }

    /**
     * Sets the can attack value.
     *
     * @param canAttack
     *            the new can attack value
     */
    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    /**
     * Sets the silence forced value.
     *
     * @param silenceForced
     *            the new silence forced value
     */
    public void setSilenceForced(boolean silenceForced) {
        this.silenceForced = silenceForced;
    }

    /**
     * Sets the must discard value.
     *
     * @param mustDiscard
     *            the new must discard value
     */
    public void setMustDiscard(boolean mustDiscard) {
        this.mustDiscard = mustDiscard;
    }

    /**
     * Sets the max steps value.
     *
     * @param steps
     *            the new max steps value
     */
    public void setMaxSteps(int steps) {
        this.maxSteps = steps;
    }

    /**
     * Sets the must move value to false.
     */
    public void setMustMove() {
        this.mustMove = false;
    }

    /**
     * Sets the isSecDangerous value.
     *
     * @param isDangerous
     *            the new value for isSecDangerous
     */
    public void setIsSecDangerous(boolean isDangerous) {
        this.isSecDangerous = isDangerous;
    }

    /**
     * Change human killed value.
     *
     * @param killed
     *            the number of killed players
     */
    public void changeHumanKilled(int killed) {
        this.humanKilled = humanKilled + killed;
    }
}

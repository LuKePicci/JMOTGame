package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.TurnViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

/**
 * The Class Turn.
 */
public class Turn implements IViewable {

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

    /**
     * The silence forced. Indicates that the player doesn't have to draw a
     * sector card in case he ends his movement on a dangerous sector
     */
    private boolean silenceForced;

    /**
     * Is sector dangerous. Indicates if the effect of a dangerous sector still
     * need to be solved.
     */
    private boolean isSecDangerous;

    /**
     * The human killed. Indicates how many humans have been killed by aliens in
     * this turn.
     */
    private int humanKilled;

    /** The drawn card. */
    private SectorCard drawnCard;

    /**
     * Instantiates a new turn based on the player "player".
     *
     * @param player
     *            the player
     */
    public Turn(Player player) {
        if (PlayerRace.ALIEN == player.getIdentity().getRace()) {// alien
            this.canAttack = true;
            if (player.getKillsCount() > 0) {
                this.maxSteps = 3;// alien who has already killed a human
            } else {
                this.maxSteps = 2;// alien who has not killed a human yet
            }
        } else {// human
            this.canAttack = false;
            this.maxSteps = 1;
        }
        this.mustMove = true;
        this.currentPlayer = player;
        this.silenceForced = false;
        this.mustDiscard = false;
        this.isSecDangerous = false;
        this.humanKilled = 0;
        this.drawnCard = null;
    }

    /**
     * Gets the can attack.
     *
     * @return if the player can attack
     */
    public boolean getCanAttack() {
        return this.canAttack;
    }

    /**
     * Gets the must move.
     *
     * @return if the player must move
     */
    public boolean getMustMove() {
        return this.mustMove;
    }

    /**
     * Gets the max steps.
     *
     * @return the max steps
     */
    public int getMaxSteps() {
        return this.maxSteps;
    }

    /**
     * Gets the silence forced.
     *
     * @return if the silence is forced
     */
    public boolean getSilenceForced() {
        return this.silenceForced;
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Gets the must discard.
     *
     * @return if the player must discard
     */
    public boolean getMustDiscard() {
        return this.mustDiscard;
    }

    /**
     * Gets is sector dangerous.
     *
     * @return if sector is dangerous
     */
    public boolean getIsSecDangerous() {
        return this.isSecDangerous;
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
     * Gets the drawn card.
     *
     * @return the drawn card
     */
    public SectorCard getDrawnCard() {
        return this.drawnCard;
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
     * Change human killed value by increasing it of the value received.
     *
     * @param killed
     *            the number of killed players
     */
    public void changeHumanKilled(int killed) {
        this.humanKilled = humanKilled + killed;
    }

    /**
     * Sets the drawn card.
     *
     * @param drawnCard
     *            the new drawn card
     */
    public void setDrawnCard(SectorCard drawnCard) {
        this.drawnCard = drawnCard;
    }

    @Override
    public ViewModel getViewModel() {
        return new TurnViewModel(this.getCanAttack(), this.getMaxSteps(),
                this.getMustDiscard(), this.getMustMove(),
                this.getSilenceForced());
    }

}

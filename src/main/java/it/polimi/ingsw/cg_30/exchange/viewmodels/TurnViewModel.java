package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class TurnViewModel.
 */
@XmlRootElement(name = "Turn")
public class TurnViewModel extends ViewModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6158366298057289584L;

    /** The max steps. */
    @XmlElement(name = "MaxSteps")
    private int maxSteps;

    /** The can attack. */
    @XmlElement(name = "CanAttack")
    private boolean canAttack;

    /** The must discard. */
    @XmlElement(name = "MustDiscard")
    private boolean mustDiscard;

    /** The must move. */
    @XmlElement(name = "MustMove")
    private boolean mustMove;

    /** The silence forced. */
    @XmlElement(name = "SilenceForced")
    private boolean silenceForced;

    /** The drawn card. */
    @XmlElement(name = "DrawnCard")
    private SectorCard drawnCard;

    /** The turn count. */
    @XmlElement(name = "TurnCount")
    private int turnCount;

    /** The turn start. */
    @XmlElement(name = "TurnStart")
    private Date turnStart;

    /** The current player identity. */
    @XmlElement(name = "PlayerIdentity")
    private PlayerCard currentPlayerIdentity;

    /** The current player name. */
    @XmlElement(name = "PlayerName")
    private String currentPlayerName;

    /** Is sector dangerous. */
    @XmlElement(name = "IsSecDangerous")
    private boolean isSecDangerous;

    /** Can turn over value. */
    @XmlElement(name = "CanTurnOver")
    private boolean canTurnOver;

    /**
     * Instantiates a new turn view model.
     *
     * @param attack
     *            the attack value
     * @param steps
     *            the steps value
     * @param discard
     *            the discard value
     * @param move
     *            the move value
     * @param silence
     *            the silence value
     * @param drawnCard
     *            the drawn card value
     * @param turnCount
     *            the turn count value
     * @param turnStart
     *            the turn start value
     * @param currentPlayerIdentity
     *            the current player identity
     * @param currentPlayerName
     *            the current player name
     * @param secDanger
     *            the sector dangerous value
     */
    public TurnViewModel(boolean attack, int steps, boolean discard,
            boolean move, boolean silence, SectorCard drawnCard, int turnCount,
            Date turnStart, PlayerCard currentPlayerIdentity,
            String currentPlayerName, boolean secDanger) {
        this();
        this.canAttack = attack;
        this.maxSteps = steps;
        this.mustDiscard = discard;
        this.mustMove = move;
        this.silenceForced = silence;
        this.drawnCard = drawnCard;
        this.turnCount = turnCount;
        this.turnStart = turnStart;
        this.currentPlayerIdentity = currentPlayerIdentity;
        this.currentPlayerName = currentPlayerName;
        this.isSecDangerous = secDanger;
        this.canTurnOver = this.checkCanTurnOver(discard, move, drawnCard,
                secDanger);
    }

    /**
     * Instantiates a new turn view model.
     */
    private TurnViewModel() {
        // JAXB handled
        super(ViewType.TURN);
    }

    /**
     * Gets the max steps allowed for this turn.
     *
     * @return the max steps value
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Check if player can attack.
     *
     * @return true, if player can attack
     */
    public boolean canAttack() {
        return canAttack;
    }

    /**
     * Check if player must discard.
     *
     * @return true, if player must discard
     */
    public boolean mustDiscard() {
        return mustDiscard;
    }

    /**
     * Check if player must move.
     *
     * @return true, if the player must move
     */
    public boolean mustMove() {
        return mustMove;
    }

    /**
     * Checks if silence is forced.
     *
     * @return true, if silence is forced
     */
    public boolean isSilenceForced() {
        return silenceForced;
    }

    /**
     * Gets the drawn card.
     *
     * @return the drawn card
     */
    public SectorCard getDrawnCard() {
        return drawnCard;
    }

    /**
     * Gets the turn count.
     *
     * @return the turn count
     */
    public int getTurnCount() {
        return this.turnCount;
    }

    /**
     * Gets the turn start date.
     *
     * @return the turn start date
     */
    public Date getTurnStart() {
        return this.turnStart;
    }

    /**
     * Gets the current player identity.
     *
     * @return the current player identity
     */
    public PlayerCard getCurrentPlayerIdentity() {
        return this.currentPlayerIdentity;
    }

    /**
     * Gets the current player name.
     *
     * @return the current player name
     */
    public String getCurrentPlayerName() {
        return this.currentPlayerName;
    }

    /**
     * Checks if sector is dangerous.
     *
     * @return true, if sec is dangerous
     */
    public boolean isSecDangerous() {
        return this.isSecDangerous;
    }

    /**
     * Can turn over.
     *
     * @return true, if successful
     */
    public boolean canTurnOver() {
        return this.canTurnOver;
    }

    /**
     * Checks if a player can turn over.
     *
     * @param discard
     *            the discard value
     * @param move
     *            the move value
     * @param drawnCard
     *            the drawn card value
     * @param secDanger
     *            the is sector dangerous value
     * @return true, if successful
     */
    private boolean checkCanTurnOver(boolean discard, boolean move,
            SectorCard drawnCard, boolean secDanger) {
        return !move && !discard && !secDanger && drawnCard == null;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TurnViewModel { maxSteps: " + maxSteps + ", canAttack: "
                + canAttack + ", mustDiscard: " + mustDiscard + ", mustMove: "
                + mustMove + ", silenceForced: " + silenceForced
                + ", drawnCard: " + drawnCard + ", turnCount: " + turnCount
                + ", turnStart: " + turnStart + ", currentPlayerIdentity: "
                + currentPlayerIdentity + ", currentPlayerName: "
                + currentPlayerName + ", isSecDangerous: " + isSecDangerous
                + ", canTurnOver: " + canTurnOver + " }";
    }

}

package it.polimi.ingsw.cg_30.exchange.viewmodels;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class TurnViewModel.
 */
@XmlRootElement(name = "Turn")
public class TurnViewModel extends ViewModel {

    private static final long serialVersionUID = 6158366298057289584L;

    @XmlElement(name = "MaxSteps")
    private int maxSteps;

    @XmlElement(name = "CanAttack")
    private boolean canAttack;

    @XmlElement(name = "MustDiscard")
    private boolean mustDiscard;

    @XmlElement(name = "MustMove")
    private boolean mustMove;

    @XmlElement(name = "SilenceForced")
    private boolean silenceForced;

    @XmlElement(name = "DrawnCard")
    private SectorCard drawnCard;

    @XmlElement(name = "TurnCount")
    private int turnCount;

    @XmlElement(name = "TurnStart")
    private Date turnStart;

    @XmlElement(name = "PlayerIdentity")
    private PlayerCard currentPlayerIdentity;

    @XmlElement(name = "PlayerName")
    private String currentPlayerName;

    @XmlElement(name = "IsSecDangerous")
    private boolean isSecDangerous;

    @XmlElement(name = "CanTurnOver")
    private boolean canTurnOver;

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

    private TurnViewModel() {
        // JAXB handled
        super(ViewType.TURN);
    }

    /**
     * Gets the max steps allowed for this turn.
     *
     * @return the max steps
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Check if player can attack.
     *
     * @return true, if successful
     */
    public boolean canAttack() {
        return canAttack;
    }

    /**
     * Check if current player must discard.
     *
     * @return true, if successful
     */
    public boolean mustDiscard() {
        return mustDiscard;
    }

    /**
     * Check if player must move.
     *
     * @return true, if successful
     */
    public boolean mustMove() {
        return mustMove;
    }

    /**
     * Checks if silence is forced.
     *
     * @return true, if is silence forced
     */
    public boolean isSilenceForced() {
        return silenceForced;
    }

    public SectorCard getDrawnCard() {
        return drawnCard;
    }

    public int getTurnCount() {
        return this.turnCount;
    }

    public Date getTurnStart() {
        return this.turnStart;
    }

    public PlayerCard getCurrentPlayerIdentity() {
        return this.currentPlayerIdentity;
    }

    public String getCurrentPlayerName() {
        return this.currentPlayerName;
    }

    public boolean isSecDangerous() {
        return this.isSecDangerous;
    }

    public boolean canTurnOver() {
        return this.canTurnOver;
    }

    private boolean checkCanTurnOver(boolean discard, boolean move,
            SectorCard drawnCard, boolean secDanger) {
        return !move && !discard && !secDanger && drawnCard == null;
    }

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

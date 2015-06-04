package it.polimi.ingsw.cg_30;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class TurnViewModel.
 */
@XmlRootElement(name = "Turn")
public class TurnViewModel extends ViewModel {

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

    /**
     * Instantiates a new turn view model.
     *
     * @param t
     *            the t
     */
    public TurnViewModel(Turn t) {
        this.canAttack = t.getCanAttack();
        this.maxSteps = t.getMaxSteps();
        this.mustDiscard = t.getMustDiscard();
        this.mustMove = t.getMustMove();
        this.silenceForced = t.getSilenceForced();
    }

    private TurnViewModel() {
        // JAXB handled
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

}

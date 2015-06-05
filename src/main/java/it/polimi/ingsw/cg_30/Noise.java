package it.polimi.ingsw.cg_30;

/**
 * The Class Noise.
 */
public class Noise extends ActionController {

    // ci sono più attributi del necessario, nel caso tornino utili per
    // l'implementazione delle parti mancanti di questa classe
    /** The match controller. */
    private MatchController matchController;

    /** The player. */
    private Player player;

    /** The player sector. */
    private Sector playerSector;

    /** The noise. */
    private SectorEvent noise;

    /**
     * Instantiates a new noise action.
     *
     * @param matchController
     *            the match controller
     * @param player
     *            the player
     * @param sector
     *            the sector
     * @param event
     *            the event
     */
    public Noise(MatchController matchController, Player player, Sector sector,
            SectorEvent event) {
        this.matchController = matchController;
        this.player = player;
        this.playerSector = sector;
        this.noise = event;
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        if (noise.equals(SectorEvent.NOISE_YOUR)) {
            // TODO notifica il rumore nel settore sector da parte di player
        } else {
            // TODO proponi la scelta del settore in cui far rumore e poi
            // notifica il rumore
        }
    }

}

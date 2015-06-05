package it.polimi.ingsw.cg_30;

public class Noise extends ActionController {

    // ci sono più attributi del necessario, nel caso tornino utili per
    // l'implementazione delle parti mancanti di questa classe
    private MatchController matchController;
    private Player player;
    private Sector playerSector;
    private SectorEvent noise;

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

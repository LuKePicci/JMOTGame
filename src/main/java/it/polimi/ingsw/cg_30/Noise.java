package it.polimi.ingsw.cg_30;

public class Noise extends ActionController {

    // ci sono più attributi del necessario nel caso tornino utili per
    // l'implementazione delle parti mancanti
    private MatchController matchController;
    private Player player;
    private Sector sector;
    private SectorEvent noise;

    public Noise(MatchController matchController, Player player, Sector target,
            SectorEvent noise) {
        this.matchController = matchController;
        this.player = player;
        this.sector = target;
        this.noise = noise;
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ActionMessage processAction() {
        if (noise.equals(SectorEvent.NOISE_YOUR)) {
            // TO DO notifica il rumore nel settore sector
            // TO DO rimuovere la seguente riga
            return null;
        } else {
            // TO DO proponi la scelta del settore in cui far rumore e poi
            // notifica il rumore
            // TO DO rimuovere la seguente riga
            return null;
        }
    }
}

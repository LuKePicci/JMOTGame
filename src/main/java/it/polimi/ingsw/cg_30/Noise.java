package it.polimi.ingsw.cg_30;

public class Noise extends ActionController {

    // ci sono più attributi del necessario, nel caso tornino utili per
    // l'implementazione delle parti mancanti di questa classe
    private Player player;
    private Sector sector;
    private SectorEvent noise;

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void processAction() {
        if (noise.equals(SectorEvent.NOISE_YOUR)) {
            // TO DO notifica il rumore nel settore sector

        } else {
            // TO DO proponi la scelta del settore in cui far rumore, verifica
            // che sia un settore di tipo DANGEROUS o SAFE e poi notifica il
            // rumore
        }
    }

}

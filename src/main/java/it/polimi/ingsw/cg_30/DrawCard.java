package it.polimi.ingsw.cg_30;

/**
 * The Class DrawCard.
 */
public class DrawCard extends ActionController {

    // costruttore usato da Move
    public DrawCard(MatchController matchController) {
        this.matchController = matchController;
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
    }

    /**
     * Checks the legality of this action.
     * 
     * @return true if the player can draw a card
     */
    @Override
    public boolean isValid() {// funzione utile solo per l'alieno
        if ((SectorType.DANGEROUS.equals(matchController.getZoneController()
                .getCurrentZone().getCell(player).getType()))
                && (matchController.getTurnController().getTurn()
                        .getIsSecDangerous() == true)) {
            return true;
        } else
            return false;
    }

    /**
     * Executes the action.
     */
    @Override
    public void processAction() {
        {
            SectorCard drawnCard = matchController.getMatch().getSectorsDeck()
                    .pickAndThrow();
            matchController.getTurnController().getTurn()
                    .setIsSecDangerous(false);
            matchController.getTurnController().getTurn().setCanAttack(false);
            if (SectorEvent.SILENCE.equals(drawnCard.getEvent())) {
                // TODO notifica SILENZIO
            } else {
                if (SectorEvent.NOISE_YOUR.equals(drawnCard.getEvent())) {
                    // TODO notifica rumore in settore
                    // matchController.getZoneController().getCurrentZone().getCell(player));
                    hasObject(drawnCard);
                } else if (SectorEvent.NOISE_ANY.equals(drawnCard.getEvent())) {
                    // salvo in turno la carta pescata in modo da portela avere
                    // anche nell'action NoiseAny
                    matchController.getTurnController().getTurn()
                            .setDrawnCard(drawnCard);
                    // TODO notifica la richiesta di scegliere in quale settore
                    // fare rumore
                }
            }
        }
    }

}

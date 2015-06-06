package it.polimi.ingsw.cg_30;

import java.util.EmptyStackException;

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
            SectorCard drawnCard = new SectorCard();
            drawnCard = (SectorCard) matchController.getMatch()
                    .getSectorsDeck().pickAndThrow();
            matchController.getTurnController().getTurn()
                    .setIsSecDangerous(false);
            matchController.getTurnController().getTurn().setCanAttack(false);
            if (SectorEvent.SILENCE.equals(drawnCard.getEvent())) {
                // TODO notifica SILENZIO
            } else {
                if (SectorEvent.NOISE_YOUR.equals(drawnCard.getEvent())) {
                    // TODO notifica rumore in settore
                    // matchController.getZoneController().getCurrentZone().getCell(player));
                } else if (SectorEvent.NOISE_ANY.equals(drawnCard.getEvent())) {
                    // TODO notifica la richiesta di scegliere in quale settore
                    // fare rumore
                    // COME GESTIAMO LA SITUAZIONE???
                }
                // controllo la presenza del sibolo oggetto sulla carta
                if (drawnCard.hasObjectSymbol()) {
                    ItemCard icard = new ItemCard();
                    // il mazzo item è l'unico che potrebbe terminare le carte
                    try {
                        icard = (ItemCard) matchController.getMatch()
                                .getItemsDeck().pickCard();
                    } catch (EmptyStackException e) {
                        // TODO informa il giocatore che non ci son più carte
                        // oggetto
                        return;
                    }
                    player.getItemsDeck().getCards().add(icard);
                    // TODO notifica il giocatore sulla carta pescata
                    if (player.getItemsDeck().getCards().size() > 3) {
                        matchController.getTurnController().getTurn()
                                .setMustDiscard(true);
                        // TODO informa il giocatore che dovrà scartare o usare
                        // una carta prima di finire il turno
                    }
                }
            }
        }
    }

}

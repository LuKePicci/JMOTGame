package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class Move extends ActionController {

    private Player player;
    private Sector target;
    private MatchController matchController;

    public Move(MatchController matchController, Sector target) {
        this.player = matchController.getTurnController().getTurn()
                .getCurrentPlayer();
        this.target = target;
        this.matchController = matchController;

    }

    @Override
    public boolean isValid() {
        // TODO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        if (matchController.getTurnController().getTurn().getMustMove()) {
            Set<Sector> reachableSectors = new HashSet<Sector>();
            int maxSteps = matchController.getTurnController().getTurn()
                    .getMaxSteps();
            reachableSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(target, maxSteps);
            if (reachableSectors.contains(target)) {
                // anche se un settore è raggiungibile devo assicurarmi che non
                // sia (scialuppa/)settore umani/settore alieni
                // TODO è possibile che i seguenti test non siano necessari a
                // seconda che reachableTargets ritorni o meno i settori start e
                // sciluppa
                if ((!SectorType.ALIENS_START.equals(target.getType()))
                        && (!SectorType.HUMANS_START.equals(target.getType()))) {
                    if (PlayerRace.HUMAN.equals(player.getIdentity())) {
                        return true; // gli umani posso andare sulle scialuppe
                    } else if (!SectorType.ESCAPE_HATCH
                            .equals(target.getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void processAction() {
        // TODO il controllo con isvalid lo eseguo esternamente prima di
        // chiamare processAction
        // sposto il giocatore
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player, target);
        // segno che il giocatore ha effettuato uno spostamento
        matchController.getTurnController().getTurn().setMustMove();
        if (SectorType.ESCAPE_HATCH.equals(target.getType())) {
            HatchCard drawnCard = new HatchCard();
            drawnCard = (HatchCard) matchController.getMatch().getHatchesDeck()
                    .pickAndThrow();
            // TODO notifica quale carta è stata pescata
            if (HatchChance.FREE.equals(drawnCard.getChance())) {
                matchController.getMatch().getRescuedPlayer().add(player);
                // TODO notifica che il giocatore si è salvato
                // verifico se la partita è finita
                matchController.checkEndGame();
            }
            try {
                matchController.getZoneController()
                        .lockHatch(target.getPoint());
            } catch (NotAnHatchException e) {
                // in teoria ho già verificato il tipo di settore e quindi non
                // dovrebbe mai verificarsi un'eccezione
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (SectorType.DANGEROUS.equals(target.getType())) {
            matchController.getTurnController().getTurn()
                    .setIsSecDangerous(true);// l'alieno dovrà o pescare o
                                             // attaccare
            if ((PlayerRace.HUMAN.equals(player.getIdentity().getRace()))
                    && (matchController.getTurnController().getTurn()
                            .getSilenceForced() == false)) {
                DrawCard forcedDraw = new DrawCard(matchController);// l'umano
                                                                    // deve
                                                                    // pescare
                                                                    // (salvo
                                                                    // uso di
                                                                    // sedativi)
            }
        }
        // else
        // TODO ritorna ActionMessage per settore non pericoloso
    }
}

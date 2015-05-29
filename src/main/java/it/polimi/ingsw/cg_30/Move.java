package it.polimi.ingsw.cg_30;

import java.util.HashSet;
import java.util.Set;

public class Move extends ActionController {

    private Player player;
    private Sector target;
    private MatchController matchController;

    public Move(MatchController matchController, Sector target) {
        this.player = matchController.getCurrentTurn().getTurn()
                .getCurrentPlayer();
        this.target = target;
        this.matchController = matchController;

    }

    @Override
    public boolean isValid() {
        // TO DO non controllo se è il turno del giocatore, lo devo fare prima.
        // se arrivo qui sono già nel turno del giocatore
        if (matchController.getCurrentTurn().getTurn().getMustMove()) {
            Set<Sector> reachableSectors = new HashSet<Sector>();
            int maxSteps = matchController.getCurrentTurn().getTurn()
                    .getMaxSteps();
            reachableSectors = matchController.getZoneController()
                    .getCurrentZone().reachableTargets(target, maxSteps);
            if (reachableSectors.contains(target)) {
                // anche se un settore è raggiungibile devo assicurarmi che non
                // sia (scialuppa/)settore umani/settore alieni
                // TO DO è possibile che i seguenti test non siano necessari a
                // seconda che reachableTargets ritorni o meno i settori start e
                // sciluppa
                if ((!target.getType().equals(SectorType.ALIENS_START))
                        && (!target.getType().equals(SectorType.HUMANS_START))) {
                    if (player.getIdentity().equals(PlayerRace.HUMAN)) {
                        return true; // gli umani posso andare sulle scialuppe
                    } else if (!target.getType()
                            .equals(SectorType.ESCAPE_HATCH)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ActionMessage processAction() {
        // TO DO il controllo con isvalid lo eseguo esternamente prima di
        // chiamare processAction
        // sposto il giocatore
        matchController.getZoneController().getCurrentZone()
                .movePlayer(player, target);
        // segno che il giocatore ha effettuato uno spostamento
        matchController.getCurrentTurn().getTurn().setMustMove();
        if (target.getType().equals(SectorType.ESCAPE_HATCH)) {
            HatchCard drawnCard = new HatchCard();
            drawnCard = (HatchCard) matchController.getHatchesDeck()
                    .pickAndThrow();
            // TO DO notifica quale carta è stata pescata
            if (drawnCard.getChance().equals(HatchChance.FREE)) {
                matchController.getRescuedPlayer().add(player);
                // TO DO notifica che il giocatore si è salvato
                // TO DO rimuovere la seguente riga
                return null;
            }
        } else if (target.getType().equals(SectorType.DANGEROUS)) {
            SectorCard drawnCard = new SectorCard();
            drawnCard = (SectorCard) matchController.getSectorsDeck()
                    .pickAndThrow();
            if (drawnCard.getEvent().equals(SectorEvent.SILENCE)) {
                // TO DO notifica SILENZIO
                // TO DO rimuovere la seguente riga
                return null;
            } else {
                if (drawnCard.getEvent().equals(SectorEvent.NOISE_YOUR)) {
                    Noise noise = new Noise(matchController, player, target,
                            SectorEvent.NOISE_YOUR);
                    noise.processAction();
                } else if (drawnCard.getEvent().equals(SectorEvent.NOISE_ANY)) {
                    Noise noise = new Noise(matchController, player, target,
                            SectorEvent.NOISE_ANY);
                    noise.processAction();
                }
                // controllo la presenza del sibolo oggetto sulla carta
                if (drawnCard.hasObjectSymbol()) {
                    ItemCard icard = new ItemCard();
                    icard = (ItemCard) matchController.getItemsDeck()
                            .pickCard();
                    // TO DO la gestiamo qui l'eccezione nel caso siano finite
                    // le carte Item?
                    player.getItemsDeck().getCards().add(icard);
                    // TO DO notifica il giocatore sulla carta pescata
                    if (player.getItemsDeck().getCards().size() > 3) {
                        matchController.getCurrentTurn().getTurn()
                                .setMustDiscard(true);
                        // TO DO informa il giocatore che dovrà scartare o usare
                        // una carta prima di finire il turno
                    }
                }
                // TO DO ho gestito il rumore, quindi devo terminare qui questo
                // metodo
                // TO DO rimuovere la seguente riga
                return null;
            }
        }
        // TO DO ritorna opportuno ActionMessage (settore non pericoloso)
        // TO DO rimuovere la seguente riga
        return null;
    }
}

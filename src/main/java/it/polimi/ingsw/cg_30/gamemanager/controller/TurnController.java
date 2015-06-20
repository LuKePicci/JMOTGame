package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCharacter;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Class TurnController.
 */
public class TurnController {

    /** The turn. */
    protected Turn turn;

    private Timer turnTimeout;

    private final MatchController currentMatch;

    /** The max turn number. */
    private static final int MAX_TURN = 39;

    /** The maximum turn duration. */
    private static final int MAX_TURN_DURATION = 90 * 1000;

    /**
     * Instance of discard card action needed if a player can't end his turn
     * properly.
     */
    protected DiscardCard forcedDiscard = new DiscardCard();

    public TurnController(MatchController match) {
        this.currentMatch = match;
    }

    /**
     * Gets the turn.
     *
     * @return the turn
     */
    public Turn getTurn() {
        return this.turn;
    }

    /**
     * Sets the turn.
     *
     * @param turn
     *            the new turn
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * Prepares the first turn by assigning it to the player whose index is one,
     * or to the next online player in case player one is offline.
     */
    public void firstTurn() {
        PlayerCard pcard = new PlayerCard(PlayerRace.ALIEN,
                PlayerCharacter.THE_FIRST_ALIEN);
        Player player0 = new Player("player0", 0, pcard);
        this.turn = new Turn(player0);
        this.nextTurn();
        this.startTimeoutTimer();
    }

    /**
     * Gets the players of this party.
     *
     * @return the players of the party
     */
    public Set<Player> getPartyPlayers() {
        // take all party players passing through matchController
        return this.currentMatch.getPartyController().getCurrentParty()
                .getMembers().keySet();// set containing all party players
    }

    /**
     * Assigns the turn to the next player.
     *
     * @param matchController
     *            the match controller
     */
    public synchronized void nextTurn() {
        this.stopTimeoutTimer();

        // in case a player has gone offline and the new turn is called due to a
        // timer expiration, I must guarantee the player doesn't end his turn
        // with more than three item card
        this.checkLegality();
        Set<Player> playerList = getPartyPlayers();
        int playerNumber = playerList.size();
        int index = this.turn.getCurrentPlayer().getIndex();
        for (int i = 0; i < playerNumber; i++) {
            if (index == playerNumber) {
                index = 1;
                this.currentMatch.getMatch().incrementTurnCount();
                if (this.currentMatch.getMatch().getTurnCount() == (MAX_TURN + 1)) {
                    this.currentMatch.endingByTurnController();
                    return;
                }
            } else {
                index++;
            }
            for (Player nextPlayer : playerList) {
                if (nextPlayer.getIndex() == index
                        && !this.checkIfPlayerIsOnline(nextPlayer,
                                this.currentMatch)
                        && !this.currentMatch.getMatch().getDeadPlayer()
                                .contains(nextPlayer)
                        && !this.currentMatch.getMatch().getRescuedPlayer()
                                .contains(nextPlayer)) {
                    // it's nextPlayer's turn
                    this.currentMatch.checkEndGame();
                    this.turn = new Turn(nextPlayer);
                    this.notify(nextPlayer);
                    this.startTimeoutTimer();
                    return;
                }
            }
        }
    }

    /**
     * Checks if a player is ending his turn without having solved the
     * obligation of discarding a card. In case of positive answer, the method
     * discards a card before ending the player's turn.
     *
     * @param matchController
     *            the match controller
     */
    private void checkLegality() {
        if (this.currentMatch.getTurnController().getTurn().getMustDiscard()) {
            Object[] playerCards = this.currentMatch.getTurnController()
                    .getTurn().getCurrentPlayer().getItemsDeck().getCards()
                    .toArray();
            ActionRequest action = new ActionRequest(ActionType.DISCARD_CARD,
                    null, ((ItemCard) playerCards[3]).getItem());
            forcedDiscard.initAction(this.currentMatch, action);
            forcedDiscard.processAction();
        }
    }

    /**
     * Checks if the player is online.
     *
     * @param player
     *            the player
     * @param matchController
     *            the match controller
     * @return true, if the player is offline
     */
    protected boolean checkIfPlayerIsOnline(Player player,
            MatchController matchController) {
        return MessageController
                .getPlayerHandler(
                        matchController.getPartyController().getCurrentParty()
                                .getPlayerUUID(player)).getAcceptPlayer()
                .connectionLost();
    }

    /**
     * Notify the new turn to all players.
     *
     * @param nextPlayer
     *            the next player
     * @param matchController
     *            the match controller
     */
    protected void notify(Player nextPlayer) {
        this.currentMatch.getPartyController()
                .sendMessageToParty(
                        new ChatMessage(new ChatViewModel("It's "
                                + nextPlayer.getName() + "'s turn.",
                                this.currentMatch.serverWordText,
                                ChatVisibility.PARTY)));
        try {
            this.currentMatch.sendViewModelToAPlayer(nextPlayer,
                    this.currentMatch.getTurnController().getTurn()
                            .getViewModel());
        } catch (DisconnectedException e) {
            // the player will receive the view of his turn when he will
            // connect again thanks to modelSender(Player) (if it's
            // still his turn)
            // Otherwise, after a timeout the turn will be given to the
            // next player
        }
    }

    private void startTimeoutTimer() {
        this.turnTimeout = new Timer();
        final TurnController context = this;
        TimerTask timeoutTask = new TimerTask() {
            @Override
            public void run() {
                context.nextTurn();
            }
        };
        this.turnTimeout.schedule(timeoutTask, MAX_TURN_DURATION,
                MAX_TURN_DURATION);
    }

    /**
     * Stop timeout timer.
     */
    public void stopTimeoutTimer() {
        if (this.turnTimeout != null)
            this.turnTimeout.cancel();
    }
}

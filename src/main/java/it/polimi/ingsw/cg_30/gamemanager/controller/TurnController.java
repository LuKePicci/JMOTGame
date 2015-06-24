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

    /** The turn timeout. */
    private Timer turnTimeout;

    /** The current match. */
    private final MatchController currentMatch;

    /** The max turn number. */
    private static final int MAX_TURN = 39;

    /** The maximum turn duration. */
    private static final int MAX_TURN_DURATION = 75 * 1000;

    /**
     * Instance of discard card action needed if a player can't end his turn
     * properly.
     */
    protected DiscardCard forcedDiscard = new DiscardCard();

    /**
     * Instantiates a new turn controller.
     *
     * @param match
     *            the match controller
     */
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
    protected void setTurn(Turn turn) {
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
        this.turn = new Turn(player0, this.currentMatch.getMatch()
                .getTurnCount());
        this.nextTurn();
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
     * Assigns the turn to the next online player.
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
                        && !this.isPlayerOffline(nextPlayer)
                        && !this.currentMatch.getMatch().getDeadPlayer()
                                .contains(nextPlayer)
                        && !this.currentMatch.getMatch().getRescuedPlayer()
                                .contains(nextPlayer)) {
                    // it's nextPlayer's turn
                    this.endingTurn(nextPlayer);
                    this.turn = new Turn(nextPlayer, this.currentMatch
                            .getMatch().getTurnCount());
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
     * Checks if the player is offline.
     *
     * @param player
     *            the player to check
     * @return true, if the player is offline
     */
    protected boolean isPlayerOffline(Player player) {
        return MessageController
                .getPlayerHandler(
                        this.currentMatch.getPartyController()
                                .getCurrentParty().getPlayerUUID(player))
                .getAcceptPlayer().connectionLost();
    }

    /**
     * Notify the new turn to all players.
     *
     * @param nextPlayer
     *            the new current player
     */
    protected void notify(Player nextPlayer) {
        this.currentMatch.getPartyController().sendMessageToParty(
                new ChatMessage(new ChatViewModel("It's "
                        + nextPlayer.getName() + "'s turn.",
                        MatchController.serverWordText, ChatVisibility.PARTY)));
        try {
            this.currentMatch.sendViewModelToAPlayer(nextPlayer,
                    this.currentMatch.getTurnController().getTurn()
                            .getViewModel());
        } catch (DisconnectedException e) {
            LoggerMethods
                    .disconnectedException(
                            e,
                            "the player will receive the view of his turn when he will connect again thanks to modelSender(Player) (if it's still his turn)%nOtherwise, after a timeout the turn will be given to the next player");
        }
    }

    /**
     * Starts timeout timer.
     */
    private void startTimeoutTimer() {
        this.turnTimeout = new Timer();
        final TurnController context = this;
        TimerTask timeoutTask = new TimerTask() {
            @Override
            public void run() {
                context.nextTurn();
            }
        };
        this.turnTimeout.schedule(timeoutTask, MAX_TURN_DURATION);
    }

    /**
     * Stops timeout timer.
     */
    public void stopTimeoutTimer() {
        if (this.turnTimeout != null)
            this.turnTimeout.cancel();
    }

    /**
     * Sends to all party player a turn model which deactivates the turnOver
     * button for the currentPlayer and update the name of the currentPlayer
     * using nextPlayer's name; this method avoids to send nextPlayer's
     * identity.
     *
     * @param nextPlayer
     *            the next player
     */
    private void endingTurn(Player nextPlayer) {
        // in case of ending the turn on a secure sector without having attacked
        this.turn.setCanAttack(false);
        // done in order to deactivate the turnOver button
        this.turn.setMustMove(true);
        this.turn.setIsSecDangerous(true);
        // be sure not to tell anyone about nextPlayer's identity
        Player fakeNextPlayer = new Player(nextPlayer.getName(),
                nextPlayer.getIndex());
        this.turn.setPlayer(fakeNextPlayer);
        this.currentMatch.sendTurnViewModelToParty();
    }

}

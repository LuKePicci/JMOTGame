package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.Message;
import it.polimi.ingsw.cg_30.exchange.messaging.PartyRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Game;
import it.polimi.ingsw.cg_30.gamemanager.model.Party;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.io.Serializable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PartyController implements Serializable {

    private static final long serialVersionUID = -1363976846969598226L;

    private long startDelay;

    private static Map<Party, PartyController> parties = new ConcurrentHashMap<Party, PartyController>();

    private Party currentParty;

    protected MatchController currentMatch;

    protected Timer startTimer = new Timer();

    public PartyController(Party p) {
        this.currentParty = p;
        this.startDelay = 0;
    }

    public static Map<Party, PartyController> getParties() {
        return PartyController.parties;
    }

    private static PartyController joinPrivateParty(UUID newPlayer,
            JoinRequest req) {
        Party found = findFreeParty(req.getGame(), req.getPartyName());
        if (found == null)
            // if given private party not exists create it
            return createPrivateParty(newPlayer, req);
        else
            return parties.get(found.addToParty(newPlayer, req.getNick()));
    }

    private static PartyController joinPublicParty(UUID newPlayer,
            JoinRequest req) {
        Party found = findFreeParty(req.getGame(), null);
        if (found == null)
            // if no available party put him into a new party
            return createPublicParty(newPlayer, req);
        else
            return parties.get(found.addToParty(newPlayer, req.getNick()));
    }

    private static Party findFreeParty(Game g, String privateName) {
        PartyController pc;
        boolean checkPrivate;
        for (Party p : parties.keySet()) {
            pc = parties.get(p);
            checkPrivate = !p.isPrivate()
                    || p.getName().equalsIgnoreCase(privateName);
            if (p.getGame().sameGame(g)
                    && p.getMembers().size() < p.getGame().getMaxPlayers()
                    && !pc.matchInProgress() && checkPrivate)
                return p;
            else
                continue;
        }
        return null;
    }

    private static PartyController createPublicParty(UUID leader,
            JoinRequest req) {
        Party newParty = new Party(req.getNick(), req.getGame(), false);
        return createNewParty(newParty.addToParty(leader, req.getNick()));
    }

    private static PartyController createPrivateParty(UUID leader,
            JoinRequest req) {
        Party newParty = new Party(req.getPartyName(), req.getGame(), true)
                .addToParty(leader, req.getNick());
        return createNewParty(newParty);
    }

    protected static PartyController createNewParty(Party p) {
        PartyController newPc = new PartyController(p);
        parties.put(p, newPc);
        return newPc;
    }

    public static synchronized PartyController processJoinRequest(
            UUID playerClient, JoinRequest request) {
        // TODO check if already joined to a party
        if (request.getGame() == null)
            request.setGame(new EftaiosGame(EftaiosGame.DEFAULT_MAP));

        final PartyController joined;
        if (request.isPrivate())
            joined = joinPrivateParty(playerClient, request);
        else
            joined = joinPublicParty(playerClient, request);
        if (joined.getCurrentParty().getMembers().size() >= 2)
            joined.scheduleMatchStart();
        return joined;
    }

    public synchronized void processPartyRequest(PartyRequest request) {
        throw new UnsupportedOperationException();
    }

    public Party getCurrentParty() {
        return this.currentParty;
    }

    public void sendMessageToParty(Message message) {
        for (UUID memberId : this.currentParty.getMembers().values()) {

            try {
                if (MessageController.connectedClients.get(memberId)
                        .getAcceptPlayer().connectionLost())
                    continue;

                MessageController.connectedClients.get(memberId)
                        .getAcceptPlayer().sendMessage(message);

            } catch (DisconnectedException e) {
                // this member will not receive the message
                ChatRequest offlineNotification = new ChatRequest(
                        "I'm offline", ChatVisibility.PARTY, null);
                offlineNotification.setSender(memberId);
                ChatController.sendToParty(
                        new ChatMessage(offlineNotification), this);
            }

        }
    }

    public MatchController getCurrentMatch() {
        return this.currentMatch;
    }

    public boolean matchInProgress() {
        return this.currentMatch != null;
    }

    private void startIfReady() {
        for (Player p : this.currentParty.getMembers().keySet()) {
            if (!p.isReady())
                return;
        }
        this.startNewMatch();
    }

    private void startNewMatch() {
        try {
            this.currentMatch = new MatchController();
            this.currentMatch.initMatch(this);
        } catch (Exception ex) {
            this.currentMatch = null;
            this.sendMessageToParty(new ChatMessage(new ChatViewModel(
                    "Unable to initialize a new game", "Server",
                    ChatVisibility.PARTY)));
        }
    }

    private void scheduleMatchStart() {

        this.startTimer.cancel();

        this.startDelay = 5 * 1000 * this.getCurrentParty().getMembers().size();

        this.startTimer = new Timer();
        this.startTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startTimer.cancel();
                if (!matchInProgress())
                    startNewMatch();
            }
        }, this.startDelay);
    }
}

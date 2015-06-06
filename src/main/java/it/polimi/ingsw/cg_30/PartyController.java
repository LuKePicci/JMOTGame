package it.polimi.ingsw.cg_30;

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

    private static PartyController joinPrivateParty(UUID newPlayer, Game g,
            String privatePartyName) {
        Party found = findFreeParty(g, privatePartyName);
        if (found == null)
            // if given private party not exists create it
            return createPrivateParty(newPlayer, g, privatePartyName);
        else
            return parties.get(found.addToParty(newPlayer));
    }

    private static PartyController joinPublicParty(UUID newPlayer, Game g) {
        Party found = findFreeParty(g, null);
        if (found == null)
            // if no available party put him into a new party
            return createPublicParty(newPlayer, g);
        else
            return parties.get(found.addToParty(newPlayer));
    }

    private static Party findFreeParty(Game g, String privateName) {
        PartyController pc;
        boolean checkPrivate;
        for (Party p : parties.keySet()) {
            pc = parties.get(p);
            checkPrivate = !p.isPrivate() || p.getName().equals(privateName);
            if (p.getGame().sameGame(g)
                    && p.getMembers().size() < p.getGame().getMaxPlayers()
                    && !pc.matchInProgress() && checkPrivate)
                return p;
            else
                continue;
        }
        return null;
    }

    private static PartyController createPublicParty(UUID leader, Game g) {
        Party newParty = new Party(MessageController.getPlayerHandler(leader)
                .getAcceptPlayer().getNickName(), g, false).addToParty(leader);
        return createNewParty(newParty);
    }

    private static PartyController createPrivateParty(UUID leader, Game g,
            String privateName) {
        Party newParty = new Party(privateName, g, true).addToParty(leader);
        return createNewParty(newParty);
    }

    private static PartyController createNewParty(Party p) {
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
            joined = joinPrivateParty(playerClient, request.getGame(),
                    request.getPartyName());
        else
            joined = joinPublicParty(playerClient, request.getGame());
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
            MessageController.connectedClients.get(memberId).getAcceptPlayer()
                    .sendMessage(message);
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
        this.currentMatch = new MatchController();
        this.currentMatch.initMatch();
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

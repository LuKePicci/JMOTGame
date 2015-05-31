package it.polimi.ingsw.cg_30;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PartyController {

    private static Map<Party, PartyController> parties = new ConcurrentHashMap<Party, PartyController>();

    private Party currentParty;

    protected MatchController currentMatch;

    public PartyController(Party p) {
        this.currentParty = p;
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
                    && pc.currentMatch == null && checkPrivate)
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

        if (request.isPrivate())
            return joinPrivateParty(playerClient, request.getGame(),
                    request.getPartyName());
        else
            return joinPublicParty(playerClient, request.getGame());
    }

    public void processPartyRequest(PartyRequest request) {
        throw new UnsupportedOperationException();
    }

    public Party getCurrentParty() {
        return this.currentParty;
    }

    public MatchController getCurrentMatch() {
        return currentMatch;
    }

}

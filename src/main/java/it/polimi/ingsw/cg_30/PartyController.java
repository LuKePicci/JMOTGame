package it.polimi.ingsw.cg_30;

import java.util.HashMap;
import java.util.Map;

public class PartyController {

    private static Map<Party, PartyController> parties = new HashMap<Party, PartyController>();

    private Party currentParty;

    private MatchController currentMatch;

    public static Map<Party, PartyController> getParties() {
        return PartyController.parties;
    }

    private static PartyController joinPrivateParty(AcceptPlayer newPlayer,
            Game g, String privatePartyName) {
        return createNewParty(newPlayer, g, true, privatePartyName);
    }

    private static PartyController joinPublicParty(AcceptPlayer newPlayer,
            Game g) {
        Party found = findFreeParty(g);
        if (found == null)
            // if no available party
            return createNewParty(newPlayer, g);

        else {
            return parties.get(found.addToParty(newPlayer));
        }

    }

    private static Party findFreeParty(Game g) {
        PartyController pc;
        for (Party p : parties.keySet()) {
            pc = parties.get(p);
            if (p.getGame().sameGame(g)
                    && p.getMembers().size() <= p.getGame().getMaxPlayers()
                    && pc.currentMatch == null)
                return p;
        }
        return null;
    }

    private static PartyController createNewParty(AcceptPlayer leader, Game g) {
        return createNewParty(leader, g, false, null);
    }

    private static PartyController createNewParty(AcceptPlayer leader, Game g,
            boolean isPrivate, String privateName) {
        Party newParty = new Party(isPrivate ? privateName
                : leader.getNickName(), g, isPrivate);
        // newParty.setPrivate(isPrivate);
        PartyController newPc = new PartyController(newParty);
        newParty.addToParty(leader);

        parties.put(newParty, newPc);

        return newPc;
    }

    public static PartyController processJoinRequest(AcceptPlayer playerClient,
            JoinRequest request) {

        if (request.MyGame == null)
            request.MyGame = new EftaiosGame(EftaiosGame.DEFAULT_MAP);

        if (request.IsPrivate)
            return joinPrivateParty(playerClient, request.MyGame,
                    request.PartyName);
        else
            return joinPublicParty(playerClient, request.MyGame);
    }

    public PartyController(Party p) {
        this.currentParty = p;
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

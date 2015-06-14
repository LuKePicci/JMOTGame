package it.polimi.ingsw.cg_30.gameclient.view;

import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;

public class RequestComposer {

    public JoinRequest createJoinRequest(String nick, String mapName,
            String partyName) {
        return new JoinRequest(nick, new EftaiosGame(mapName), partyName);
    }

    public JoinRequest createJoinRequest(String nick, String mapName) {
        return new JoinRequest(nick, new EftaiosGame(mapName));
    }

    public JoinRequest createJoinRequest(String nick) {
        return new JoinRequest(nick, new EftaiosGame());
    }
}

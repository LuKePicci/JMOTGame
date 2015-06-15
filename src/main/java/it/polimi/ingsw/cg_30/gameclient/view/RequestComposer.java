package it.polimi.ingsw.cg_30.gameclient.view;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;

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

    public ChatRequest createChatRequest(ChatVisibility target, String text) {
        return new ChatRequest(text, target, null);
    }

    public ChatRequest createChatRequest(String recipient, String text) {
        return new ChatRequest(text, ChatVisibility.PLAYER, recipient);
    }

    public ChatRequest createChatRequest(String text) {
        return new ChatRequest(text, ChatVisibility.PARTY, null);
    }

    public ActionRequest createActionRequest(ActionType t, HexPoint p, Item i) {
        return new ActionRequest(t, p, i);
    }
}

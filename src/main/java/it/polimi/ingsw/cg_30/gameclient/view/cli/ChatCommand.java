package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.gameclient.view.RequestComposer;

import java.util.StringTokenizer;

public class ChatCommand implements ICliCommand {

    private final RequestComposer composer = new RequestComposer();

    @Override
    public RequestModel makeRequest(StringTokenizer stkn) {

        String target = stkn.nextToken();

        if ("all".equalsIgnoreCase(target))
            return composer.createChatRequest(ChatVisibility.PUBLIC,
                    this.flushTokenizer(stkn));

        else if ("player".equalsIgnoreCase(target))
            return composer.createChatRequest(stkn.nextToken(),
                    this.flushTokenizer(stkn));

        else
            return composer.createChatRequest(target + " "
                    + this.flushTokenizer(stkn));

    }

    private String flushTokenizer(StringTokenizer stkn) {
        String ret = "";
        if (stkn.hasMoreTokens()) {
            ret = stkn.nextToken();
            while (stkn.hasMoreTokens())
                ret += " " + stkn.nextToken();
        }
        return ret;
    }
}

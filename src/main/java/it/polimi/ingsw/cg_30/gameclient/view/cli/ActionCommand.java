package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.gameclient.view.RequestComposer;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ActionCommand implements ICliCommand {

    private static final Map<String, ActionType> actionStrings;

    private final RequestComposer composer = new RequestComposer();

    private ActionType type;

    static {
        actionStrings = new HashMap<String, ActionType>();

        actionStrings.put("attack", ActionType.ATTACK);
        actionStrings.put("move", ActionType.DISCARD_CARD);
        actionStrings.put("useitem", ActionType.DRAW_CARD);
        actionStrings.put("turnover", ActionType.MOVE);
        actionStrings.put("noise", ActionType.NOISE_ANY);
        actionStrings.put("dicard", ActionType.TURN_OVER);
        actionStrings.put("draw", ActionType.USE_ITEM);
    }

    public ActionCommand(ActionType t) {
        this.type = t;
    }

    @Override
    public RequestModel makeRequest(StringTokenizer stkn) {
        return null;
    }

    public static boolean validCommand(String command) {
        return actionStrings.containsKey(command.toLowerCase());
    }

    public static ActionType typeOf(String commandString) {
        return actionStrings.get(commandString.toLowerCase());
    }
}
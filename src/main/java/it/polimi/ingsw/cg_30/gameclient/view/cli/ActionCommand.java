package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.RequestModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.RequestComposer;
import it.polimi.ingsw.cg_30.gamemanager.controller.LoggerMethods;

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
        actionStrings.put("move", ActionType.MOVE);
        actionStrings.put("useitem", ActionType.USE_ITEM);
        actionStrings.put("turnover", ActionType.TURN_OVER);
        actionStrings.put("noise", ActionType.NOISE_ANY);
        actionStrings.put("discard", ActionType.DISCARD_CARD);
        actionStrings.put("draw", ActionType.DRAW_CARD);
    }

    public ActionCommand(ActionType t) {
        this.type = t;
    }

    @Override
    public RequestModel makeRequest(StringTokenizer stkn) {
        switch (this.type) {
            case ATTACK:
            case DRAW_CARD:
            case TURN_OVER:
                return composer.createActionRequest(this.type, null, null);

            case USE_ITEM:
            case DISCARD_CARD:
                Item selectedItem;
                try {
                    selectedItem = Item.valueOf(stkn.nextToken().toUpperCase());

                } catch (IllegalArgumentException ex) {
                    GameClient.getActiveEngine().showError(
                            "bad syntax: invalid item");
                    throw ex;
                }
                return composer.createActionRequest(this.type,
                        this.parsePoint(stkn), selectedItem);

            case MOVE:
            case NOISE_ANY:
                return composer.createActionRequest(this.type,
                        this.parsePoint(stkn), null);
            default:
                throw new IllegalArgumentException();
        }
    }

    private HexPoint parsePoint(StringTokenizer stkn) {
        try {
            int x, y;
            switch (stkn.countTokens()) {
                case 2:
                    x = stkn.nextToken().toUpperCase().charAt(0) - 65;
                    y = Integer.parseInt(stkn.nextToken());
                    break;
                case 1:
                    String uniqueToken = stkn.nextToken().toUpperCase();
                    x = uniqueToken.charAt(0) - 65;
                    y = Integer.parseInt(uniqueToken.substring(1));
                    break;
                default:
                    return null;
            }
            return HexPoint.fromOffset(x, y - 1);
        } catch (Exception ex) {
            LoggerMethods.exception(ex, "");
            return null;
        }

    }

    public static boolean validCommand(String command) {
        return actionStrings.containsKey(command.toLowerCase());
    }

    public static ActionType typeOf(String commandString) {
        return actionStrings.get(commandString.toLowerCase());
    }
}
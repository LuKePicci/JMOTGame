package it.polimi.ingsw.cg_30.gameclient.view;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewEngine {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm");

    private static final View debug = new DebugView();

    private static final Map<ViewType, View> views = new HashMap<ViewType, View>();

    protected static void bind(ViewType t, View v) {
        views.put(t, v);
    }

    public abstract void logonWizard();

    public abstract void runEngine();

    public abstract void registerViews();

    public abstract void chooseProtocol();

    public abstract void chooseGame();

    public abstract void showGames();

    public abstract void showError(String errorMessage);

    public View getViewByType(ViewType type) {
        View targetView = views.get(type);

        if (targetView == null)
            targetView = debug;

        return targetView;
    }

}

package it.polimi.ingsw.cg_30.gameclient;

import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;
import it.polimi.ingsw.cg_30.gameclient.view.cli.CliEngine;
import it.polimi.ingsw.cg_30.gameclient.view.gui.GuiEngine;

public class GameClient {

    private static ViewEngine currentEngine;

    public static void main(String[] args) {
        GameClient gc = new GameClient();
        switch (args[0].toLowerCase()) {
            case "cli":
                gc.startCli();
                break;

            case "gui":
            default:
                gc.startGui();
                break;
        }
    }

    private void startGui() {
        currentEngine = new GuiEngine();
        currentEngine.runEngine();

    }

    private void startCli() {
        currentEngine = new CliEngine();
        currentEngine.runEngine();
    }
}

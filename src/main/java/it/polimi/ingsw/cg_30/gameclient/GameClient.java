package it.polimi.ingsw.cg_30.gameclient;

import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;
import it.polimi.ingsw.cg_30.gameclient.view.cli.CliEngine;
import it.polimi.ingsw.cg_30.gameclient.view.gui.GuiEngine;

public class GameClient {

    private static ViewEngine activeEngine;

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

        while (!Thread.interrupted()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static ViewEngine getActiveEngine() {
        return GameClient.activeEngine;
    }

    private void startGui() {
        activeEngine = new GuiEngine();
        this.startEngine();
    }

    private void startCli() {
        activeEngine = new CliEngine();
        this.startEngine();
    }

    private void startEngine() {
        activeEngine.logonWizard();
        activeEngine.runEngine();
    }

    private void switchEngine() {
        activeEngine = new GuiEngine();
        activeEngine.runEngine();
    }
}

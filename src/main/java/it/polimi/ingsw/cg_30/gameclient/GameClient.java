package it.polimi.ingsw.cg_30.gameclient;

import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;
import it.polimi.ingsw.cg_30.gameclient.view.cli.CliEngine;
import it.polimi.ingsw.cg_30.gameclient.view.gui.GuiEngine;

import javax.swing.SwingUtilities;

public class GameClient {

    private static ViewEngine activeEngine;

    public static void main(String[] args) {
        GameClient gc = new GameClient();
        String vEngine = args.length > 0 ? args[0].toLowerCase() : "";
        switch (vEngine) {
            case "cli":
                gc.startCli();
                break;
            default:
            case "gui":
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                activeEngine = new GuiEngine();
                activeEngine.logonWizard();
            }
        });
    }

    private void startCli() {
        activeEngine = new CliEngine();
        activeEngine.logonWizard();
        activeEngine.runEngine();
    }

    public static void switchEngine() {
        if (activeEngine instanceof GuiEngine) {
            activeEngine = new CliEngine();
            activeEngine.runEngine();
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    activeEngine = new GuiEngine();
                    activeEngine.runEngine();
                }
            });
        }
    }
}

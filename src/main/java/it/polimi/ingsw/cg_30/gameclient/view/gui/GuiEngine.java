package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;
import it.polimi.ingsw.cg_30.gameclient.network.ClientMessenger;
import it.polimi.ingsw.cg_30.gameclient.view.RequestComposer;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class GuiEngine extends ViewEngine {

    private GameView gv;
    private final RequestComposer composer = new RequestComposer();

    // this flag will become true when a player click the discard button
    private static boolean discardCard = false;

    // it's unlikely to use a spotlight card, so by default this flag is false
    private static boolean spolightCard = false;

    // only when needed this flag will be turn to true, by default a player
    // selects a sector in order to move, not to make a noise.
    private static boolean noise = false;

    private static boolean move = true;

    public GuiEngine() {
        // enable anti-aliased text:
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        setLookAndFeel();

        this.gv = new GameView();
        this.registerViews();
        this.gv.initialize();
    }

    @Override
    public void logonWizard() {
        // TODO Auto-generated method stub

    }

    @Override
    public void runEngine() {
        this.gv.setVisible(true);
    }

    @Override
    public void registerViews() {
        for (Map.Entry<ViewType, GuiView> view : this.gv.getSubViews())
            bind(view.getKey(), view.getValue());
    }

    @Override
    public void chooseProtocol() {
        // TODO Auto-generated method stub

    }

    @Override
    public void chooseGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void showGames() {
        // TODO Auto-generated method stub

    }

    @Override
    public void showError(String text) {
        // TODO Auto-generated method stub

    }

    public static Font loadCustomFont(String fontName) {
        try {
            return Font
                    .createFont(
                            Font.TRUETYPE_FONT,
                            GameView.class.getResourceAsStream("/" + fontName
                                    + ".ttf"));
        } catch (Exception ioEx) {
            LoggerMethods.exception(ioEx, "");
            return new Font("Calibri", 0, 18);
        }
    }

    public static Image loadImage(String imageName) {
        try {
            return ImageIO.read(GameView.class.getResourceAsStream("/"
                    + imageName));
        } catch (Exception ioEx) {
            LoggerMethods.exception(ioEx, "");
            return null;
        }
    }

    public static Dimension getResponsive(double propX, double propY) {
        return new Dimension((int) Math.round(Toolkit.getDefaultToolkit()
                .getScreenSize().getWidth()
                / propX),

        (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize()
                .getHeight()
                / propY));
    }

    public static int getResponsive(double prop) {
        return (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth()
                / prop);
    }

    public static void setLookAndFeel() {
        try {
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            if (screenWidth >= 1600)
                HiFiLookAndFeel.setTheme("Giant-Font");
            else if (screenWidth >= 1200)
                HiFiLookAndFeel.setTheme("Large-Font");
            else
                HiFiLookAndFeel.setTheme("Small-Font");
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (Exception e) {
            LoggerMethods.exception(e, "use default lookAndFeel");
        }
    }

    public void cardProcessor(Item icard) {
        if (Item.SPOTLIGHT.equals(icard)) {
            spolightCard = true;
            // wait for sector selection, than the action will be processed
            // by sectorProcessor
            JOptionPane.showMessageDialog(null,
                    "Click on the sector where you'd like to make a noise.");
            return;
        }

        ActionRequest request;
        if (discardCard) {
            // discard the card
            request = composer.createActionRequest(ActionType.DISCARD_CARD,
                    null, icard);

        } else {
            // use the card
            request = composer.createActionRequest(ActionType.USE_ITEM, null,
                    icard);
        }

        ClientMessenger.getCurrentMessenger().executeRequestTask(request);
    }

    public void sectorProcessor(HexPoint hp) {
        ActionRequest request;
        if (noise) {
            // action noise
            request = composer.createActionRequest(ActionType.NOISE_ANY, hp,
                    null);

        } else if (spolightCard) {
            // use spotlight
            request = composer.createActionRequest(ActionType.USE_ITEM, hp,
                    Item.SPOTLIGHT);
            spolightCard = false;
        } else if (move) {
            // action move
            request = composer.createActionRequest(ActionType.MOVE, hp, null);
        } else
            return;

        ClientMessenger.getCurrentMessenger().executeRequestTask(request);
    }

    public void attackProcessor() {
        ActionRequest request = composer.createActionRequest(ActionType.ATTACK,
                null, null);
        ClientMessenger.getCurrentMessenger().executeRequestTask(request);
    }

    public void drawProcessor() {
        ActionRequest request = composer.createActionRequest(
                ActionType.DRAW_CARD, null, null);
        ClientMessenger.getCurrentMessenger().executeRequestTask(request);
    }

    public void turnoverProcessor() {
        ActionRequest request = composer.createActionRequest(
                ActionType.TURN_OVER, null, null);
        ClientMessenger.getCurrentMessenger().executeRequestTask(request);
    }

    public static void setDiscardCard(boolean value) {
        discardCard = value;
    }

    public static void setNoise(boolean value) {
        noise = value;
    }

    public static void setMove(boolean value) {
        move = value;
    }

}

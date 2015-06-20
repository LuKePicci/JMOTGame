package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class GuiEngine extends ViewEngine {

    private GameView gv;

    public GuiEngine() {
        // enable anti-aliased text:
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        setLookAndFeel();

        this.gv = new GameView(this);
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
            ioEx.printStackTrace();
            return new Font("Calibri", 0, 18);
        }
    }

    public static Image loadImage(String imageName) {
        try {
            return ImageIO.read(GameView.class.getResourceAsStream("/"
                    + imageName));
        } catch (Exception ioEx) {
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
            // use default lookAndFeel
        }
    }
}

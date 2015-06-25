package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ActionType;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;
import it.polimi.ingsw.cg_30.exchange.messaging.JoinRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;
import it.polimi.ingsw.cg_30.gameclient.network.ClientMessenger;
import it.polimi.ingsw.cg_30.gameclient.view.RequestComposer;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class GuiEngine extends ViewEngine {

    private GameView gv;
    private final RequestComposer composer = new RequestComposer();

    private static final Map<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();
    private static final Map<String, Font> fontCache = new HashMap<String, Font>();

    // this flag will become true when a player click the discard button
    private static boolean discardCard = false;

    // it's unlikely to use a spotlight card, so by default this flag is false
    private static boolean spotlightCard = false;

    // only when needed this flag will be turn to true, by default a player
    // selects a sector in order to move, not to make a noise.
    private static boolean noise = false;

    private static boolean move = true;

    private static String myNickName = "";

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
        this.chooseProtocol();

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
        final ConnectionView cv = new ConnectionView();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                cv.initialize();
                cv.setVisible(true);
            }
        });
    }

    @Override
    public void chooseGame() {
        final ChooseZoneView czv = new ChooseZoneView();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                czv.initialize();
                czv.setVisible(true);
            }
        });
    }

    @Override
    public void showGames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showError(String text) {
        JOptionPane
                .showMessageDialog(null, text, "", JOptionPane.ERROR_MESSAGE);
    }

    public static Font loadCustomFont(String fontName) {
        if (fontCache.containsKey(fontName)) {
            return fontCache.get(fontName);
        } else {
            try {
                Font newFont = Font.createFont(
                        Font.TRUETYPE_FONT,
                        GameView.class.getResourceAsStream("/gameclient/"
                                + fontName + ".ttf"));
                fontCache.put(fontName, newFont);
                return newFont;
            } catch (Exception ioEx) {
                LoggerMethods.exception(ioEx, "");
                return new Font("Calibri", 0, 18);
            }
        }
    }

    public static BufferedImage loadImage(String imageName) {
        if (fontCache.containsKey(imageName)) {
            return imageCache.get(imageName);
        } else {
            try {
                BufferedImage newImage = ImageIO.read(GameView.class
                        .getResourceAsStream("/gameclient/" + imageName));
                imageCache.put(imageName, newImage);
                return newImage;
            } catch (Exception ioEx) {
                LoggerMethods.exception(ioEx, "");
                return null;
            }
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

    public boolean connect(String schema, String hostName, String portNumber) {
        URI serverURI;
        try {
            serverURI = new URI(schema.toLowerCase(), null, hostName,
                    Integer.parseInt(portNumber), null, null, null);
            ClientMessenger.connectToServer(serverURI);
            this.chooseGame();
            return true;
        } catch (NumberFormatException | URISyntaxException e) {
            this.showError("Invalid hostname or port number");
        } catch (NotBoundException | IOException e) {
            this.showError("Connection failed");
        }
        return false;
    }

    public boolean join(String nick, String mapName, String partyName) {
        if (!nick.equals("")) {

            JoinRequest req = composer.createJoinRequest(nick, mapName
                    .equals("") ? null : mapName, partyName.equals("") ? null
                    : partyName);
            ClientMessenger.getCurrentMessenger().loadToken(req.getNick());
            ClientMessenger.getCurrentMessenger().executeRequestTask(req);
            this.runEngine();
            return true;
        }
        return false;
    }

    public void cardProcessor(Item icard) {

        ActionRequest request;
        if (discardCard) {
            // discard the card
            request = composer.createActionRequest(ActionType.DISCARD_CARD,
                    null, icard);

        } else {
            if (Item.SPOTLIGHT.equals(icard)) {
                spotlightCard = true;
                // wait for sector selection, than the action will be processed
                // by sectorProcessor
                JOptionPane.showMessageDialog(null,
                        "Click on the sector around you'd like to spot");
                return;
            }

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

        } else if (spotlightCard) {
            // use spotlight
            request = composer.createActionRequest(ActionType.USE_ITEM, hp,
                    Item.SPOTLIGHT);
            spotlightCard = false;
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

    public void chatProcessor(String tabTitle, String text) {
        ChatRequest request;
        switch (tabTitle.toLowerCase()) {
            case "+":
            case "match":
                return;
            case "public":
                request = composer.createChatRequest(ChatVisibility.PUBLIC,
                        text);
                break;
            case "party":
                request = composer
                        .createChatRequest(ChatVisibility.PARTY, text);
                break;
            default:
                request = composer.createChatRequest(tabTitle, text);
        }
        ClientMessenger.getCurrentMessenger().executeRequestTask(request);
    }

    public static void setDiscardCard(boolean value) {
        discardCard = value;
    }

    public static void setNoise(boolean value) {
        noise = value;
        if (noise)
            JOptionPane.showMessageDialog(null,
                    "Click on the sector where you'd like to make a noise");
    }

    public static void setMove(boolean value) {
        move = value;
    }

    public static String getMyNickName() {
        return myNickName;
    }

    public static void setMyNickName(String nick) {
        myNickName = nick;
    }

}

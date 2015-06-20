package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewType;
import it.polimi.ingsw.cg_30.gameclient.view.gui.eventhandlers.MouseHoverMagnify;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class GameView {

    public static final Dimension FRAME_SIZE = GuiEngine.getResponsive(1.5610,
            1.1676);

    public static final Dimension PARTY_SIZE = GuiEngine.getResponsive(0.6204,
            0.1406);
    public static final Dimension MAP_SIZE = GuiEngine.getResponsive(2, 1.62);

    public static final int SECTOR_WIDTH = GuiEngine.getResponsive(36.2264);

    private JFrame mainFrame;

    private JScrollPane mapScrollPane;

    private final Map<ViewType, GuiView> subViews = new HashMap<ViewType, GuiView>();

    /**
     * Create the game form.
     */
    public GameView(GuiEngine gui) {
        this.subViews.put(ViewType.ZONE, new GuiZoneView());
        this.subViews.put(ViewType.CHAT, new GuiChatView());
    }

    public Set<Entry<ViewType, GuiView>> getSubViews() {
        return this.subViews.entrySet();
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        mainFrame = new JFrame();
        // mainFrame.setTitle("Escape From The Aliens Into Outer Space - EFTAIOS");
        mainFrame.setIconImage(GuiEngine.loadImage("eftaios_icon.jpg"));
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getCenterPoint();

        // set position and size
        mainFrame.setBounds((int) (center.x - FRAME_SIZE.getWidth() / 2),
                (int) (center.y - FRAME_SIZE.getHeight() / 2),
                (int) FRAME_SIZE.getWidth(), (int) FRAME_SIZE.getHeight());

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BorderLayout());

        JSplitPane topDownSplit = new JSplitPane();
        topDownSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainFrame.getContentPane().add(topDownSplit);

        JSplitPane bottomSplit = new JSplitPane();
        topDownSplit.setBottomComponent(bottomSplit);

        JScrollPane deckScrollPane = new JScrollPane();
        bottomSplit.setRightComponent(deckScrollPane);

        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.X_AXIS));
        cardsPanel.add(Box.createHorizontalGlue());

        deckScrollPane.setViewportView(cardsPanel);

        JLabel cardLabel1 = new JLabel("AN ITEM CARD");
        cardLabel1.addMouseListener(new MouseHoverMagnify());
        cardLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardLabel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardLabel1.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, cardLabel1.getFont().getSize()));
        cardsPanel.add(cardLabel1);

        JLabel cardLabel2 = new JLabel("AN ITEM CARD");
        cardLabel2.addMouseListener(new MouseHoverMagnify());
        cardLabel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardLabel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardLabel2.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, cardLabel2.getFont().getSize()));
        cardsPanel.add(cardLabel2);

        JLabel cardLabel3 = new JLabel("AN ITEM CARD");
        cardLabel3.addMouseListener(new MouseHoverMagnify());
        cardLabel3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardLabel3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardLabel3.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, cardLabel3.getFont().getSize()));
        cardsPanel.add(cardLabel3);

        cardsPanel.add(Box.createHorizontalGlue());

        bottomSplit
                .setLeftComponent(subViews.get(ViewType.CHAT).getComponent());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));
        topDownSplit.setTopComponent(topPanel);

        mapScrollPane = new JScrollPane();
        mapScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapScrollPane.setPreferredSize(new Dimension(
                (int) MAP_SIZE.getWidth() + 2, (int) MAP_SIZE.getHeight() + 2));
        mapScrollPane.setViewportView(subViews.get(ViewType.ZONE)
                .getComponent());
        topPanel.add(mapScrollPane, BorderLayout.CENTER);

        JPanel topRightPane = new JPanel();
        topRightPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topRightPane.setLayout(new BorderLayout(0, 0));
        topPanel.add(topRightPane, BorderLayout.EAST);

        JPanel matchInfoPane = new JPanel();
        matchInfoPane.setAlignmentY(Component.TOP_ALIGNMENT);
        matchInfoPane.setLayout(new BorderLayout(0, 0));
        topRightPane.add(matchInfoPane, BorderLayout.NORTH);

        JLabel matchInfo = new JLabel("MATCH_INFO");
        matchInfo.setHorizontalAlignment(SwingConstants.CENTER);
        matchInfo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        matchInfoPane.add(matchInfo, BorderLayout.CENTER);

        JScrollPane partyScrollPane = new JScrollPane();
        partyScrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        partyScrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        topRightPane.add(partyScrollPane, BorderLayout.CENTER);

        JList partyList = new JList();
        partyList.setMaximumSize(PARTY_SIZE);
        partyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partyScrollPane.setViewportView(partyList);
    }

    /**
     * Sets the visibility state of game window.
     *
     * @param visible
     *            the new visibility state
     */
    public void setVisible(boolean visible) {
        this.mainFrame.setVisible(visible);
    }

}

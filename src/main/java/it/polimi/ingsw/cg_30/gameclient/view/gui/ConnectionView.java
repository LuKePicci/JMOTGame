package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.JAntiAliasedLabel;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.PlaceholderTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ConnectionView {

    private JFrame connFrame;
    private JLabel topImage, bottomImage;
    private PlaceholderTextField hostName, portNumber;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private BufferedImage floor, roof, imageBackBuffer;
    private Timer animation;
    private Map<BufferedImage, BufferedImage[]> animationCache = new HashMap<BufferedImage, BufferedImage[]>();
    private String titilliumTextXb = "TitilliumText22L-Xb";
    private String titilliumText = "TitilliumText22L";

    /**
     * @wbp.parser.entryPoint
     */
    public void initialize() {
        connFrame = new JFrame();
        connFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        connFrame.setMaximumSize(new Dimension(470, 325));
        connFrame.setMinimumSize(new Dimension(470, 325));
        connFrame.setPreferredSize(new Dimension(470, 325));
        connFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent arg0) {
                if (animation != null)
                    animation.stop();
            }
        });
        connFrame.setIconImage(GuiEngine.loadImage("custom_icon.png"));
        connFrame.setResizable(false);
        connFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connFrame.getContentPane().setLayout(new BorderLayout(0, 0));
        connFrame.setBounds(GameView.CENTER.x - connFrame.getWidth() / 2,
                GameView.CENTER.y - connFrame.getHeight() / 2,
                connFrame.getWidth(), connFrame.getHeight());

        JLayeredPane layeredPane = new JLayeredPane();
        connFrame.getContentPane().add(layeredPane, BorderLayout.CENTER);
        layeredPane.setLayout(new BorderLayout(0, 0));

        topImage = new JAntiAliasedLabel();
        layeredPane.setLayer(topImage, 2);
        layeredPane.add(topImage, BorderLayout.NORTH);
        topImage.setPreferredSize(new Dimension(0, 75));
        topImage.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel centerPane = new JPanel();
        layeredPane.setLayer(centerPane, 1);
        layeredPane.add(centerPane);
        GridBagLayout gblCenterPane = new GridBagLayout();
        centerPane.setLayout(gblCenterPane);

        JPanel panel1 = new JPanel();
        GridBagConstraints gbcPanel1 = new GridBagConstraints();
        gbcPanel1.weightx = 0.05;
        gbcPanel1.gridheight = 3;
        gbcPanel1.fill = GridBagConstraints.BOTH;
        gbcPanel1.gridx = 0;
        gbcPanel1.gridy = 0;
        centerPane.add(panel1, gbcPanel1);

        JPanel panel2 = new JPanel();
        GridBagConstraints gbcPanel2 = new GridBagConstraints();
        gbcPanel2.weightx = 0.05;
        gbcPanel2.gridheight = 3;
        gbcPanel2.fill = GridBagConstraints.BOTH;
        gbcPanel2.gridx = 2;
        gbcPanel2.gridy = 0;
        centerPane.add(panel2, gbcPanel2);

        JPanel panel = new JPanel();
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.weightx = 0.05;
        gbcPanel.gridheight = 3;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 4;
        gbcPanel.gridy = 0;
        centerPane.add(panel, gbcPanel);

        JLabel lblEscape = new JAntiAliasedLabel("ESCAPE");
        lblEscape.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEscape.setHorizontalAlignment(SwingConstants.CENTER);
        lblEscape.setFont(GuiEngine.loadCustomFont(titilliumTextXb)
                .deriveFont(0, 64));
        GridBagConstraints gbcLblEscape = new GridBagConstraints();
        gbcLblEscape.fill = GridBagConstraints.BOTH;
        gbcLblEscape.weightx = 0.3;
        gbcLblEscape.insets = new Insets(10, 0, 5, 5);
        gbcLblEscape.gridx = 1;
        gbcLblEscape.gridy = 0;
        centerPane.add(lblEscape, gbcLblEscape);

        JLabel lblFromTheAliens = new JAntiAliasedLabel("FROM THE ALIENS");
        lblFromTheAliens.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFromTheAliens.setHorizontalAlignment(SwingConstants.CENTER);
        lblFromTheAliens.setFont(GuiEngine
                .loadCustomFont(titilliumTextXb).deriveFont(0, 26));
        GridBagConstraints gbcLblFromTheAliens = new GridBagConstraints();
        gbcLblFromTheAliens.fill = GridBagConstraints.BOTH;
        gbcLblFromTheAliens.insets = new Insets(0, 0, 5, 5);
        gbcLblFromTheAliens.gridx = 1;
        gbcLblFromTheAliens.gridy = 1;
        centerPane.add(lblFromTheAliens, gbcLblFromTheAliens);

        JLabel lblInOuterSpace = new JAntiAliasedLabel("IN OUTER SPACE");
        lblInOuterSpace.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInOuterSpace.setHorizontalAlignment(SwingConstants.CENTER);
        lblInOuterSpace.setFont(GuiEngine.loadCustomFont(titilliumTextXb)
                .deriveFont(0, 30));
        GridBagConstraints gbcLblInOuterSpace = new GridBagConstraints();
        gbcLblInOuterSpace.fill = GridBagConstraints.BOTH;
        gbcLblInOuterSpace.insets = new Insets(0, 0, 8, 5);
        gbcLblInOuterSpace.gridx = 1;
        gbcLblInOuterSpace.gridy = 2;
        centerPane.add(lblInOuterSpace, gbcLblInOuterSpace);

        JPanel controlsPane = new JPanel();
        GridBagConstraints gbcControlsPane = new GridBagConstraints();
        gbcControlsPane.insets = new Insets(0, 0, 0, 5);
        gbcControlsPane.weightx = 0.65;
        gbcControlsPane.gridheight = 3;
        gbcControlsPane.fill = GridBagConstraints.BOTH;
        gbcControlsPane.gridx = 3;
        gbcControlsPane.gridy = 0;
        centerPane.add(controlsPane, gbcControlsPane);
        GridBagLayout gblControlsPane = new GridBagLayout();
        controlsPane.setLayout(gblControlsPane);

        hostName = new PlaceholderTextField();
        hostName.setMinimumSize(new Dimension(4, 25));
        hostName.setPlaceholder("Hostname");
        hostName.setFont(GuiEngine.loadCustomFont(titilliumText)
                .deriveFont(0, 16));
        GridBagConstraints gbcHostName = new GridBagConstraints();
        gbcHostName.weighty = 0.33;
        gbcHostName.weightx = 0.6;
        gbcHostName.fill = GridBagConstraints.HORIZONTAL;
        gbcHostName.gridx = 0;
        gbcHostName.gridy = 0;
        controlsPane.add(hostName, gbcHostName);
        hostName.setColumns(15);

        JLabel lblSeparator = new JAntiAliasedLabel(":");
        lblSeparator.setHorizontalTextPosition(SwingConstants.CENTER);
        lblSeparator.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcLblSeparator = new GridBagConstraints();
        gbcLblSeparator.fill = GridBagConstraints.HORIZONTAL;
        gbcLblSeparator.gridx = 1;
        gbcLblSeparator.gridy = 0;
        controlsPane.add(lblSeparator, gbcLblSeparator);

        portNumber = new PlaceholderTextField();
        portNumber.setMinimumSize(new Dimension(4, 25));
        portNumber.setPlaceholder("Port");
        portNumber.setFont(GuiEngine.loadCustomFont(titilliumText)
                .deriveFont(0, 16));
        GridBagConstraints gbcPortNumber = new GridBagConstraints();
        gbcPortNumber.weightx = 0.4;
        gbcPortNumber.fill = GridBagConstraints.HORIZONTAL;
        gbcPortNumber.gridx = 2;
        gbcPortNumber.gridy = 0;
        controlsPane.add(portNumber, gbcPortNumber);
        portNumber.setColumns(5);

        JRadioButton rdbtnSocket = new JRadioButton("Socket");
        rdbtnSocket.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtnSocket.setHorizontalTextPosition(SwingConstants.LEADING);
        rdbtnSocket.setFont(GuiEngine.loadCustomFont(titilliumText).deriveFont(
                0, 13));
        buttonGroup.add(rdbtnSocket);
        rdbtnSocket.setSelected(true);
        GridBagConstraints gbcRdbtnSocket = new GridBagConstraints();
        gbcRdbtnSocket.weighty = 0.33;
        gbcRdbtnSocket.gridheight = 2;
        gbcRdbtnSocket.weightx = 0.5;
        gbcRdbtnSocket.fill = GridBagConstraints.BOTH;
        gbcRdbtnSocket.gridx = 0;
        gbcRdbtnSocket.gridy = 1;
        controlsPane.add(rdbtnSocket, gbcRdbtnSocket);

        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (GameClient.getActiveEngine() instanceof GuiEngine) {
                            GuiEngine activeEngine = (GuiEngine) GameClient
                                    .getActiveEngine();
                            for (Enumeration<AbstractButton> buttons = buttonGroup
                                    .getElements(); buttons.hasMoreElements();) {
                                AbstractButton button = buttons.nextElement();

                                if (!button.isSelected())
                                    continue;

                                if (activeEngine.connect(button.getText(),
                                        hostName.getText(),
                                        portNumber.getText())) {
                                    animation.stop();
                                    connFrame.dispose();
                                }

                                return;
                            }
                        }
                    }
                });
            }
        });

        JLabel label = new JAntiAliasedLabel("-");
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridheight = 2;
        gbcLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcLabel.gridx = 1;
        gbcLabel.gridy = 1;
        controlsPane.add(label, gbcLabel);

        JRadioButton rdbtnRmi = new JRadioButton("RMI");
        rdbtnRmi.setHorizontalAlignment(SwingConstants.CENTER);
        rdbtnRmi.setFont(GuiEngine.loadCustomFont(titilliumText).deriveFont(0,
                13));
        buttonGroup.add(rdbtnRmi);
        GridBagConstraints gbcRdbtnRmi = new GridBagConstraints();
        gbcRdbtnRmi.gridheight = 2;
        gbcRdbtnRmi.fill = GridBagConstraints.BOTH;
        gbcRdbtnRmi.gridx = 2;
        gbcRdbtnRmi.gridy = 1;
        controlsPane.add(rdbtnRmi, gbcRdbtnRmi);
        btnConnect.setFont(GuiEngine.loadCustomFont(titilliumText)
                .deriveFont(0, 17));
        GridBagConstraints gbcBtnConnect = new GridBagConstraints();
        gbcBtnConnect.weighty = 0.33;
        gbcBtnConnect.gridheight = 2;
        gbcBtnConnect.fill = GridBagConstraints.HORIZONTAL;
        gbcBtnConnect.gridwidth = 3;
        gbcBtnConnect.gridx = 0;
        gbcBtnConnect.gridy = 3;
        controlsPane.add(btnConnect, gbcBtnConnect);
        JRootPane rootPane = SwingUtilities.getRootPane(btnConnect);
        rootPane.setDefaultButton(btnConnect);

        bottomImage = new JAntiAliasedLabel();
        layeredPane.add(bottomImage, BorderLayout.SOUTH);
        bottomImage.setPreferredSize(new Dimension(0, 75));
        bottomImage.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setVisible(boolean visible) {
        if (visible)
            this.animateEffect();
        this.connFrame.setVisible(visible);
    }

    private BufferedImage toRGB(BufferedImage source) {
        BufferedImage rgb = new BufferedImage(source.getWidth(),
                source.getHeight(), BufferedImage.TYPE_INT_ARGB);

        rgb.getGraphics().drawImage(source, 0, 0, null);
        return rgb;
    }

    private BufferedImage rescaleEffect(BufferedImage image,
            BufferedImage backBuffer, float factor) {
        BufferedImageOp op = new RescaleOp(factor, 0, null);
        op.filter(image, backBuffer);
        return backBuffer;
    }

    private void animateEffect() {
        floor = toRGB(GuiEngine.loadImage("rsz_floor.png"));
        roof = toRGB(GuiEngine.loadImage("rsz_roof.png"));
        final BufferedImage[] fMap = new BufferedImage[100];
        final BufferedImage[] rMap = new BufferedImage[100];
        this.animationCache.put(floor, fMap);
        this.animationCache.put(roof, rMap);
        float factor;

        final float a = 0.5F;
        final float w = 1F;
        float c = 0;
        for (int i = 0; i < 100; i++) {
            factor = 1 + (float) (a * Math.sin(w * c * Math.PI));
            imageBackBuffer = toRGB(floor);
            fMap[i] = rescaleEffect(floor, imageBackBuffer, factor);
            imageBackBuffer = toRGB(roof);
            rMap[i] = rescaleEffect(roof, imageBackBuffer, factor);
            c += 0.02;
        }

        this.animation = new Timer(40, new ActionListener() {
            float counter = 0;
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        index = (int) (counter / 0.02);
                        bottomImage.setIcon(new ImageIcon(fMap[index]));
                        topImage.setIcon(new ImageIcon(rMap[index]));
                        counter += 0.02;
                        counter %= 2;
                    }
                });
            }
        });
        this.animation.start();
    }
}

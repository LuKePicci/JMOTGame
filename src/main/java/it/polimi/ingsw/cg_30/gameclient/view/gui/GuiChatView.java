package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.GameClient;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.PlaceholderTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

public class GuiChatView extends GuiView {

    private JTextArea party, match, server;
    private Map<String, JTextArea> privates = new HashMap<String, JTextArea>();
    private String stringToPrint = "%s  %s%n";

    private JPanel chatPane;
    private JTabbedPane chatTabs;
    private JButton btnSendchat;
    private PlaceholderTextField textMessage;

    @Override
    public JPanel getComponent() {
        if (this.chatPane == null)
            this.createComponents();
        return this.chatPane;
    }

    @Override
    protected void createComponents() {
        chatPane = new JPanel();
        chatPane.setLayout(new BorderLayout(0, 0));

        chatTabs = new JTabbedPane(JTabbedPane.TOP);
        chatTabs.setAlignmentY(Component.TOP_ALIGNMENT);
        chatTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        chatTabs.setFont(GuiEngine.loadCustomFont("TitilliumText22L")
                .deriveFont(0, chatTabs.getFont().getSize()));
        chatPane.add(chatTabs, BorderLayout.CENTER);

        JPanel chatBottomPanel = new JPanel();
        chatBottomPanel.setLayout(new BorderLayout(0, 0));
        chatPane.add(chatBottomPanel, BorderLayout.SOUTH);

        textMessage = new PlaceholderTextField(35);
        textMessage.setPlaceholder("Type here your message");
        chatBottomPanel.add(textMessage, BorderLayout.CENTER);

        btnSendchat = new JButton("Send");
        btnSendchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (GameClient.getActiveEngine() instanceof GuiEngine) {
                            GuiEngine activeEngine = (GuiEngine) GameClient
                                    .getActiveEngine();
                            if (textMessage.getText().equals(""))
                                return;
                            activeEngine.chatProcessor(chatTabs
                                    .getTitleAt(chatTabs.getSelectedIndex()),
                                    textMessage.getText());
                            textMessage.setText("");
                        }
                    }
                });

            }
        });
        chatBottomPanel.add(btnSendchat, BorderLayout.EAST);

        match = this.newTab("Match", 0);
        party = this.newTab("Party", 1);
        server = this.newTab("Public", 2);

        JPanel newChatPane = new JPanel();
        newChatPane.setLayout(new BoxLayout(newChatPane, BoxLayout.X_AXIS));

        newChatPane.add(Box.createHorizontalGlue());

        JScrollPane newChatScroll = new JScrollPane(newChatPane);

        chatTabs.addTab("+", null, newChatScroll,
                "Open a chat tab with a player");

        final PlaceholderTextField playerNick = new PlaceholderTextField(12);
        playerNick.setPlaceholder("Nickname");
        newChatPane.add(playerNick);

        JButton newTabButton = new JButton("+");
        newTabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        // check if a tab with this name already exists
                        if (playerNick.getText().equals("")
                                || chatTabs.indexOfTab(playerNick.getText()) >= 0)
                            return;

                        privates.put(playerNick.getText(),
                                insertTab(playerNick.getText()));
                    }
                });
            }
        });
        newChatPane.add(newTabButton);

        playerNick.setMaximumSize(playerNick.getPreferredSize());

        newChatPane.add(Box.createHorizontalGlue());
    }

    @Override
    public synchronized void applyUpdate(ViewModel model) {
        ChatViewModel chatMsg = (ChatViewModel) model;
        if (chatMsg.getDate() == null)
            chatMsg.setDate(new Date());
        switch (chatMsg.getAudience()) {
            case PUBLIC:
                server.append(String.format("%s %s : %s%n",
                        ViewEngine.SDF.format(chatMsg.getDate()),
                        chatMsg.getSenderNick(), chatMsg.getText()));
                this.selectLastMessageTab(server);
                break;

            case PARTY:
                if ("server".equalsIgnoreCase(chatMsg.getSenderNick())) {
                    match.append(String.format(this.stringToPrint,
                            ViewEngine.SDF.format(chatMsg.getDate()),
                            chatMsg.getText()));
                    this.selectLastMessageTab(match);
                } else {
                    party.append(String.format("%s %s : %s%n",
                            ViewEngine.SDF.format(chatMsg.getDate()),
                            chatMsg.getSenderNick(), chatMsg.getText()));
                    this.selectLastMessageTab(party);
                }
                break;

            case PLAYER:
                if ("server".equalsIgnoreCase(chatMsg.getSenderNick())) {
                    match.append(String.format(this.stringToPrint,
                            ViewEngine.SDF.format(chatMsg.getDate()),
                            chatMsg.getText()));
                    this.selectLastMessageTab(match);
                    break;
                }
                if (chatMsg.getSenderNick().equals(GuiEngine.getMyNickName()))
                    break;
                if (!privates.containsKey(chatMsg.getSenderNick())) {
                    privates.put(chatMsg.getSenderNick(),
                            this.insertTab(chatMsg.getSenderNick()));
                }
                JTextArea privateArea = privates.get(chatMsg.getSenderNick());
                privateArea.append(String.format("%s %s : %s\r\n",
                                ViewEngine.SDF.format(chatMsg.getDate()),
                        chatMsg.getSenderNick(), chatMsg.getText()));
                this.selectLastMessageTab(privateArea);
                break;
        }
    }

    private JTextArea insertTab(String title) {
        // should not go out of bound since this is called while other tabs
        // exists
        int newTabLocation = chatTabs.getTabCount() - 1;
        JTextArea ret = this.newTab(title, newTabLocation);
        chatTabs.setSelectedIndex(newTabLocation);
        return ret;
    }

    private JTextArea newTab(String title, int index) {
        JTextArea newArea = new JTextArea();
        newArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) newArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane areaScrollPane = new JScrollPane(newArea);
        areaScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        chatTabs.insertTab(title, null, areaScrollPane, null, index);
        return newArea;
    }

    @Override
    public void registerDefaultButton() {
        if (btnSendchat != null) {
            JRootPane rootPane = SwingUtilities.getRootPane(btnSendchat);
            rootPane.setDefaultButton(btnSendchat);
        }
    }

    private void selectLastMessageTab(JComponent tabContent) {
        // this.chatTabs.setSelectedIndex(this.chatTabs
        // .indexOfComponent(tabContent.getParent()));
    }
}

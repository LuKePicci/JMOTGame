package it.polimi.ingsw.cg_30.gameclient.view.gui;

import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.ViewEngine;
import it.polimi.ingsw.cg_30.gameclient.view.gui.components.PlaceholderTextField;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class GuiChatView extends GuiView {

    private JTextArea party, match, server;
    private Map<String, JTextArea> privates = new HashMap<String, JTextArea>();

    private JPanel chatPane;
    private JTabbedPane chatTabs;

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

        JButton btnSendchat = new JButton("Send");
        chatBottomPanel.add(btnSendchat, BorderLayout.EAST);

        PlaceholderTextField textMessage = new PlaceholderTextField(35);
        textMessage.setPlaceholder("Type here your message");
        chatBottomPanel.add(textMessage, BorderLayout.CENTER);

        match = this.newTab("Match");

        party = this.newTab("Party");

        server = this.newTab("Public");

    }

    @Override
    public synchronized void applyUpdate(ViewModel model) {
        ChatViewModel chatMsg = (ChatViewModel) model;
        if (chatMsg.getDate() == null)
            chatMsg.setDate(new Date());
        switch (chatMsg.getAudience()) {
            case PUBLIC:
                server.append(String.format("%s %s : %s\r\n",
                        ViewEngine.SDF.format(chatMsg.getDate()),
                        chatMsg.getSenderNick(), chatMsg.getText()));
                break;

            case PARTY:
                if ("server".equals(chatMsg.getSenderNick().toLowerCase())) {
                    match.append(String.format("%s  %s\r\n",
                            ViewEngine.SDF.format(chatMsg.getDate()),
                            chatMsg.getText()));
                } else {
                    party.append(String.format("%s %s : %s\r\n",
                            ViewEngine.SDF.format(chatMsg.getDate()),
                            chatMsg.getSenderNick(), chatMsg.getText()));
                }
                break;

            case PLAYER:
                if ("server".equals(chatMsg.getSenderNick().toLowerCase())) {
                    match.append(String.format("%s  %s\r\n",
                            ViewEngine.SDF.format(chatMsg.getDate()),
                            chatMsg.getText()));
                    break;
                }
                if (!privates.containsKey(chatMsg.getSenderNick())) {
                    privates.put(chatMsg.getSenderNick(),
                            this.newTab(chatMsg.getSenderNick()));
                }
                privates.get(chatMsg.getSenderNick()).append(
                        String.format("%s  %s\r\n",
                                ViewEngine.SDF.format(chatMsg.getDate()),
                                chatMsg.getText()));
                break;
        }
    }

    private JTextArea newTab(String title) {
        JTextArea newArea = new JTextArea();
        newArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) newArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane areaScrollPane = new JScrollPane(newArea);
        areaScrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        chatTabs.addTab(title, null, areaScrollPane, null);
        return newArea;
    }
}

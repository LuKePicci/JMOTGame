package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.UUID;

public class ChatController {

    private static final String CANNOT_CONTACT = " is offline.";

    public static void sendToParty(ChatMessage msg, PartyController pc) {
        if (pc != null)
            pc.sendMessageToParty(msg);
    }

    public static void sendToAll(ChatMessage msg, PartyController pc) {

        MessageController.sendMessageToAll(msg);
    }

    public static void sendToPlayer(ChatMessage msg, PartyController pc,
            String target) throws DisconnectedException {
        if (pc == null || target == null)
            return;

        UUID targetUUID = pc.getCurrentParty().getUUIDOfNick(target);

        if (targetUUID != null)
            MessageController.getPlayerHandler(targetUUID).getAcceptPlayer()
                    .sendMessage(msg);

    }

    public static void processChatRequest(ChatRequest chatReq,
            PartyController pc) {

        String senderNick;
        if (pc == null)
            senderNick = "Player-" + chatReq.getSender().hashCode();
        else
            senderNick = pc.getCurrentParty()
                    .getNickOfUUID(chatReq.getSender());

        ChatMessage msg = new ChatMessage(new ChatViewModel(chatReq.getText(),
                senderNick, chatReq.getAudience()));

        switch (chatReq.getAudience()) {
            case PLAYER:
                try {
                    sendToPlayer(msg, pc, chatReq.getRecipient());
                    sendToPlayer(msg, pc, senderNick);
                } catch (DisconnectedException e) {
                    ChatMessage offlineMsg = new ChatMessage(new ChatViewModel(
                            chatReq.getRecipient() + CANNOT_CONTACT,
                            senderNick, chatReq.getAudience()));
                    try {
                        sendToPlayer(offlineMsg, pc, senderNick);
                    } catch (DisconnectedException e1) {
                        // nothing to be done
                    }
                }
                break;

            case PUBLIC:
                sendToAll(msg, pc);
                break;

            case PARTY:
            default:
                sendToParty(msg, pc);
        }
    }

}

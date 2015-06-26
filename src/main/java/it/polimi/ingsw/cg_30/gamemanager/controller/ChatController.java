package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatMessage;
import it.polimi.ingsw.cg_30.exchange.messaging.ChatRequest;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ChatViewModel;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.util.UUID;

/**
 * The Class ChatController.
 */
public class ChatController {

    /** The Constant CANNOT_CONTACT. */
    private static final String CANNOT_CONTACT = " is offline.";

    /**
     * Instantiates a new chat controller.
     */
    private ChatController() {
    }

    /**
     * Sends the message to party.
     *
     * @param msg
     *            the message
     * @param pc
     *            the party controller
     */
    public static void sendToParty(ChatMessage msg, PartyController pc) {
        if (pc != null)
            pc.sendMessageToParty(msg);
    }

    /**
     * Sends the message to all players who are connected to the server.
     *
     * @param msg
     *            the message
     * @param pc
     *            the party controller
     */
    public static void sendToAll(ChatMessage msg, PartyController pc) {

        MessageController.sendMessageToAll(msg);
    }

    /**
     * Sends the message to player.
     *
     * @param msg
     *            the message
     * @param pc
     *            the party controller
     * @param target
     *            the target
     * @throws DisconnectedException
     *             the disconnected exception
     */
    public static void sendToPlayer(ChatMessage msg, PartyController pc,
            String target) throws DisconnectedException {
        if (pc == null || target == null)
            return;

        UUID targetUUID = pc.getCurrentParty().getUUIDOfNick(target);

        if (targetUUID != null)
            MessageController.getPlayerHandler(targetUUID)
                    .dispatchOutgoing(msg);

    }

    /**
     * Processes chat request.
     *
     * @param chatReq
     *            the chat request
     * @param pc
     *            the party controller
     */
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
                    LoggerMethods.disconnectedException(e, "");
                    try {
                        sendToPlayer(offlineMsg, pc, senderNick);
                    } catch (DisconnectedException e1) {
                        LoggerMethods.disconnectedException(e1,
                                "nothing to be done");
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

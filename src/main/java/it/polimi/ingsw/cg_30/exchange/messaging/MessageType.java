package it.polimi.ingsw.cg_30.exchange.messaging;

/**
 * The possible types of a Message.
 */
public enum MessageType {

    /** The chat message. */
    CHAT_MESSAGE,
    /** The action message. */
    ACTION_MESSAGE,
    /** The party message. */
    PARTY_MESSAGE,
    /** The join message. */
    JOIN_MESSAGE,
    /** The view model message. */
    VIEW_MESSAGE;
}
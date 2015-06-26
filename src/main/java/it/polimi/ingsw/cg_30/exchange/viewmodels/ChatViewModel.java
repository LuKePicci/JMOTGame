package it.polimi.ingsw.cg_30.exchange.viewmodels;

import it.polimi.ingsw.cg_30.exchange.messaging.ChatVisibility;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class ChatViewModel.
 */
@XmlRootElement(name = "Chat")
public class ChatViewModel extends ViewModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2792460984430627321L;

    /** The text. */
    @XmlElement(name = "Text")
    private String text;

    /** The sender nick. */
    @XmlAttribute(name = "From")
    private String senderNick;

    /** The audience. */
    @XmlAttribute(name = "Audience")
    private ChatVisibility audience;

    /**
     * Instantiates a new chat view model.
     */
    private ChatViewModel() {
        // JAXB handled
        super(ViewType.CHAT);
    }

    /**
     * Instantiates a new chat view model.
     *
     * @param textMessage
     *            the text message
     * @param from
     *            the string "sender"
     * @param target
     *            the target
     */
    public ChatViewModel(String textMessage, String from, ChatVisibility target) {
        this();
        this.text = textMessage;
        this.senderNick = from;
        this.audience = target;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Gets the sender nick.
     *
     * @return the sender nick
     */
    public String getSenderNick() {
        return this.senderNick;
    }

    /**
     * Gets the audience.
     *
     * @return the audience
     */
    public ChatVisibility getAudience() {
        return this.audience;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ChatViewModel { text: " + text + ", senderNick: " + senderNick
                + ", audience: " + audience + " }";
    }

}

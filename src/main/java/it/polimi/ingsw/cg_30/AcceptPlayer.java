package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public abstract class AcceptPlayer {

    private static final long serialVersionUID = -7240265207942528345L;

    protected UUID sessionId;
    protected transient MessageController mc = new MessageController(this);
    protected Date lastMessage;

    protected AcceptPlayer(UUID sid) {
        this.sessionId = sid;
    }

    public Date getLastMessageDate() {
        return lastMessage;
    }

    public UUID getUUID() {
        return this.sessionId;
    }

    public abstract void sendMessage(Message message);

    protected abstract Message receiveMessage() throws IOException;

    public abstract void ping();

}

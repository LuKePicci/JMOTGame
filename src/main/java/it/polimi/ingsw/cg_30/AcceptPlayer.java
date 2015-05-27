package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class AcceptPlayer implements Serializable {

    private static final long serialVersionUID = -7240265207942528345L;
    private String myNickName;
    protected UUID sessionId;
    protected transient MessageController mc = new MessageController(this);
    protected Date lastMessage;

    protected AcceptPlayer(UUID sid) {
        this.sessionId = sid;
    }

    public String getNickName() {
        return this.myNickName;
    }

    public void setNickName(String name) {
        this.myNickName = name;
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

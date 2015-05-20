package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public abstract class AcceptPlayer {

    private String myNickName;

    protected UUID sessionId;

    public String getNickName() {
        return this.myNickName;
    }

    public void setNickName(String name) {
        this.myNickName = name;
    }

    protected Date lastMessage;

    public Date getLastMessageDate() {
        return lastMessage;
    }

    protected MessageController mc = new MessageController(this);

    protected AcceptPlayer(UUID sid) {
        this.sessionId = sid;
    }

    abstract public void sendMessage(Message message);

    abstract protected Message receiveMessage() throws IOException;

    abstract public void ping();

    public UUID getUUID() {
        return this.sessionId;
    }
}

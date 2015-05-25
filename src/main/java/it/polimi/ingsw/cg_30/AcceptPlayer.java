package it.polimi.ingsw.cg_30;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public abstract class AcceptPlayer {

    private String myNickName;
    protected UUID sessionId;
    protected MessageController mc = new MessageController(this);
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

    abstract public void sendMessage(Message message);

    abstract protected Message receiveMessage() throws IOException;

    abstract public void ping();
}

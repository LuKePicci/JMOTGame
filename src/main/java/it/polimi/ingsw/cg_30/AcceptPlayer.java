package it.polimi.ingsw.cg_30;

import java.util.*;


public abstract class AcceptPlayer extends Thread
{
	
	private String myNickName;
	
	public String getNickName() {
		return this.myNickName;
	}

	public void setNickName(String name) {
		this.myNickName = name;
	}
	
	
	private Date lastMessage;
	
	public Date getLastMessageDate()
	{
		return lastMessage;
	}

	protected MessageController mc = new MessageController(this); 
	
	protected AcceptPlayer(){
		
	}
	
	abstract public void sendMessage(IMessage message);

	abstract protected void receiveMessage();
	
	@Override
	final public void run(){
		while(true){
			receiveMessage();
		}
	}
}


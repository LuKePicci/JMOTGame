package it.polimi.ingsw.cg_30.exchange.messaging;

public interface IDelivery {
    public void deliver(JoinRequest req);

    public void deliver(ActionRequest req);

    public void deliver(ChatRequest req);

    public void deliver(PartyRequest req);
}

package it.polimi.ingsw.cg_30;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class ActionRequest extends RequestModel implements Serializable {

    private static final long serialVersionUID = 6425875533542791509L;

    @XmlElement(name = "ActionType")
    private ActionType type;

    @XmlElement(name = "ActionTarget")
    private HexPoint target;

    @XmlElement(name = "ActionItem")
    private Item item;

    private ActionRequest() {
        // JAXB handled
        super();
    }

    public void getSettingByName(Object settingName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void process(MessageController mc) {
        mc.deliver(this);
    }

    public ActionType getActionType() {
        return this.type;
    }

    public HexPoint getActionTarget() {
        return this.target;
    }

    public Item getActionItem() {
        return this.item;
    }

    // TODO metodo da eliminare (inserito solo come utlity per il testing))
    public ActionRequest(ActionType type, HexPoint target, Item item) {
        this.type = type;
        this.target = target;
        this.item = item;
    }

}

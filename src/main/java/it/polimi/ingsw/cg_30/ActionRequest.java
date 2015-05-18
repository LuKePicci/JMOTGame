package it.polimi.ingsw.cg_30;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Content")
public class ActionRequest extends RequestModel
{
	@XmlElementWrapper(name="Settings")
	@XmlElement(name="Setting")
	public Collection<Object> currentTurnSettings;
	
	public ActionRequest() {
		// JAXB handled
		super();
	}
	
	public void getSettingByName(Object settingName)
	{
		throw new UnsupportedOperationException();
	}
	
}

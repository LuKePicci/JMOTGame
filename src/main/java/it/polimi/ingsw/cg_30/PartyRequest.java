package it.polimi.ingsw.cg_30;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Content")
public class PartyRequest extends RequestModel
{
	@XmlElementWrapper(name="Informations")
	@XmlElement(name="Info")
	public Collection<Object> playersInfo;
	
	public PartyRequest() {
		// JAXB handled
		super();
	}
	
	public void getInfoByName(Object infoName)
	{
		throw new UnsupportedOperationException();
	}

	
}


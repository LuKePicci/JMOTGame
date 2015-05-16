package it.polimi.ingsw.cg_30;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ ActionRequest.class, ChatRequest.class, PartyRequest.class })
public abstract class RequestModel {
	protected Date updatedAt;

	@XmlAttribute(name = "Date")
	public Date getDate() {
		return this.updatedAt;
	}

	public void setDate(Date d) {
		this.updatedAt = d;
	}

	protected RequestModel() {
		this.setDate(new Date());
	}

}

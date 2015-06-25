package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.exchange.LoggerMethods;
import it.polimi.ingsw.cg_30.gamemanager.model.Zone;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TemplateZoneFactory extends ZoneFactory {

    private InputStream zoneTemplate;

    public TemplateZoneFactory(String mapName) throws FileNotFoundException {
        this.zoneTemplate = getClass().getResourceAsStream(
                "/gamemanager/" + mapName + ".xml");
        if (this.zoneTemplate == null)
            throw new FileNotFoundException(String.format(
                    "Map XML template not found for '%s'", mapName));
    }

    @Override
    public Zone newMap() {
        return this.jaxbXMLToZone(this.zoneTemplate);
    }

    private Zone jaxbXMLToZone(InputStream xmlFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(Zone.class);
            Unmarshaller un = context.createUnmarshaller();
            return (Zone) un.unmarshal(xmlFile);
        } catch (JAXBException e) {
            LoggerMethods.jAXBException(e);
        }
        return null;
    }
}

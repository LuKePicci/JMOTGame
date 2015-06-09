package it.polimi.ingsw.cg_30.gamemanager.controller;

import it.polimi.ingsw.cg_30.gamemanager.model.Zone;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TemplateZoneFactory extends ZoneFactory {

    private File zoneTemplate;

    public TemplateZoneFactory(String mapName) throws FileNotFoundException,
            URISyntaxException {
        URL resourceUrl = getClass().getResource("/" + mapName + ".xml");
        if (resourceUrl == null)
            throw new FileNotFoundException();
        Path resourcePath;

        resourcePath = Paths.get(resourceUrl.toURI());
        zoneTemplate = resourcePath.toFile();
        if (!zoneTemplate.exists()) {
            throw new FileNotFoundException();
        }

    }

    @Override
    public Zone newMap() {
        return this.jaxbXMLToZone(zoneTemplate);
    }

    private Zone jaxbXMLToZone(File xmlFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(Zone.class);
            Unmarshaller un = context.createUnmarshaller();
            Zone obj = (Zone) un.unmarshal(xmlFile);
            return obj;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}

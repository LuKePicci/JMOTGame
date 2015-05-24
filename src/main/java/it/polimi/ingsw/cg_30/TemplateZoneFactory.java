package it.polimi.ingsw.cg_30;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TemplateZoneFactory extends ZoneFactory {

    private File zoneTemplate;

    public TemplateZoneFactory(String templateName)
            throws FileNotFoundException {
        zoneTemplate = new File(templateName + ".xml");
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

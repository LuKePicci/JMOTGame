package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;
import it.polimi.ingsw.cg_30.gamemanager.controller.TemplateZoneFactory;
import it.polimi.ingsw.cg_30.gamemanager.controller.ZoneFactory;
import it.polimi.ingsw.cg_30.gamemanager.model.Zone;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class TestTemplateZoneFactory {
    private static final String TEST_MAP = "galilei";

    @Test
    public void shouldCreateZoneFromTemplate() throws FileNotFoundException,
            URISyntaxException {
        URL resourceUrl = getClass().getResource("/" + TEST_MAP + ".xml");
        Path resourcePath = Paths.get(resourceUrl.toURI());
        ZoneFactory zf = new TemplateZoneFactory(resourcePath);
        Zone z = zf.newMap();
        assertEquals(TEST_MAP, z.getMapName());
        Sector s = z.getMap().get(HexPoint.fromOffset(10, 10));
        assertNotNull(s);
        assertEquals(HexPoint.fromOffset(10, 10), s.getPoint());
        assertEquals(SectorType.SECURE, s.getType());
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldNotFindTemplateFile() throws MalformedURLException,
            URISyntaxException, FileNotFoundException {
        URL notExisting = new URL("file:/NOT_EXISTING_FILE.xml");
        Path p = Paths.get(notExisting.toURI());
        new TemplateZoneFactory(p);
    }
}

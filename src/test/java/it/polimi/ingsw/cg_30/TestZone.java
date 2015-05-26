package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestZone {

    private static final String TEST_MAP = "galilei";

    private Zone testZone;

    @Before
    public void initZone() throws URISyntaxException, FileNotFoundException {
        URL resourceUrl = getClass().getResource("/" + TEST_MAP + ".xml");
        Path resourcePath = Paths.get(resourceUrl.toURI());
        ZoneFactory zf = new TemplateZoneFactory(resourcePath);
        this.testZone = zf.newMap();
    }

    @Test
    public void shouldFindSectorNeighbors() {
        Sector f09 = testZone.getMap().get(HexPoint.fromOffset(5, 8));
        Set<Sector> targets = this.testZone.reachableTargets(f09, 1);
        assertEquals(6, targets.size());
    }
}

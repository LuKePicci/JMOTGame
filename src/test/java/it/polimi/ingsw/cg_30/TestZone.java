package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    // TODO fixare il seguente test
    // @Test
    public void shouldFindSectorNeighbors() {
        Sector f09 = testZone.getMap().get(HexPoint.fromOffset(5, 8));
        Set<Sector> targets = this.testZone.reachableTargets(f09, 1);
        assertEquals(6, targets.size());
    }

    @Test
    public void findPlayer() {
        Player pl1 = new Player();
        Player pl2 = new Player();
        Zone zone = new Zone();
        HexPoint point = new HexPoint(3, 4);
        Sector sec = new Sector(null, point);
        zone.movePlayer(pl1, sec);
        zone.movePlayer(pl2, sec);
        Set<Player> players = zone.getPlayersInSector(sec);
        assertTrue(players.contains(pl1));
        assertTrue(players.contains(pl2));
        assertEquals(2, players.size(), 0);
        HexPoint point2 = new HexPoint(1, 2);
        Sector sec2 = new Sector(null, point2);
        Set<Player> players2 = zone.getPlayersInSector(sec2);
        assertEquals(0, players2.size(), 0);
    }

}

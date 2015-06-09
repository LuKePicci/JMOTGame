package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.gamemanager.controller.TemplateZoneFactory;
import it.polimi.ingsw.cg_30.gamemanager.controller.ZoneFactory;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestZone {

    private static final String TEST_MAP = "galilei";

    private Zone testZone;

    @Before
    public void initZone() throws URISyntaxException, FileNotFoundException {
        ZoneFactory zf = new TemplateZoneFactory(TEST_MAP);
        this.testZone = zf.newMap();
    }

    @Test
    public void shouldFindSectorNeighbors() {
        Sector f09 = testZone.getMap().get(HexPoint.fromOffset(5, 8));
        Set<Sector> targets = this.testZone.reachableTargets(f09, 1);
        assertEquals(6, targets.size());

        Sector a09 = testZone.getMap().get(HexPoint.fromOffset(0, 8));
        targets = this.testZone.reachableTargets(a09, 3);
        assertEquals(15, targets.size());

        Sector k08 = testZone.getMap().get(HexPoint.fromOffset(10, 7));
        targets = this.testZone.reachableTargets(k08, 1);
        assertEquals(2, targets.size());

        Sector l04 = testZone.getMap().get(HexPoint.fromOffset(11, 3));
        targets = this.testZone.reachableTargets(l04, 2);
        assertEquals(17, targets.size());
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

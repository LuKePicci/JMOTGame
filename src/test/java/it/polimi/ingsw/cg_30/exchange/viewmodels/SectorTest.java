package it.polimi.ingsw.cg_30.exchange.viewmodels;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SectorTest {

    @Test
    public void sectorTester() {
        HexPoint he = HexPoint.fromCubic(1, 2);
        Sector ex = new Sector(SectorType.SECURE, he);
        assertEquals(SectorType.SECURE, ex.getType());
        assertEquals(he, ex.getPoint());
    }

}

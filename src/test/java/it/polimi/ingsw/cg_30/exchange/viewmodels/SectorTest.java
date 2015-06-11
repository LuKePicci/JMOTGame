package it.polimi.ingsw.cg_30.exchange.viewmodels;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;

import org.junit.Test;

public class SectorTest {

    @Test
    public void SectorTester() {
        HexPoint he = HexPoint.fromCubic(1, 2);
        Sector ex = new Sector(SectorType.SECURE, he);
        assertEquals(SectorType.SECURE, ex.getType());
        assertEquals(he, ex.getPoint());
    }

}

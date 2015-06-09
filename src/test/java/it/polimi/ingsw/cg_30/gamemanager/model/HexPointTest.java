package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;

import org.junit.Test;

public class HexPointTest {

    @Test
    public void HexPointCubicTest() {
        HexPoint ex = HexPoint.fromCubic(3, -1);
        assertEquals(3, ex.getCubicX(), 0);
        assertEquals(-1, ex.getCubicY(), 0);
        assertEquals(-2, ex.getCubicZ(), 0);
    }

    @Test
    public void HexPointAxialTest() {
        HexPoint ex = HexPoint.fromAxial(3, -1);
        assertEquals(3, ex.getAxialX(), 0);
        assertEquals(-1, ex.getAxialY(), 0);
    }

    @Test
    public void HexPointOffsetTest() {
        HexPoint ex = HexPoint.fromOffset(3, -1);
        assertEquals(3, ex.getX(), 0);
        assertEquals(-1, ex.getY(), 0);
    }

    @Test
    public void shouldNotEquals() {
        assertFalse(HexPoint.fromOffset(3, -1).equals(this));
        assertFalse(HexPoint.fromOffset(3, -1).equals(null));
        assertFalse(HexPoint.fromCubic(3, -1).equals(HexPoint.fromCubic(3, 0)));
        assertFalse(HexPoint.fromCubic(3, -1).equals(HexPoint.fromCubic(2, -1)));
    }

    @Test
    public void shouldEquals() {
        assertTrue(HexPoint.fromCubic(3, -1).equals(HexPoint.fromCubic(3, -1)));
    }

}

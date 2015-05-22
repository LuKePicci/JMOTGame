package it.polimi.ingsw.cg_30;

import static org.junit.Assert.*;

import org.junit.Test;

public class HexPointTest {

    @Test
    public void HexPointCubicTest() {
        HexPoint ex = HexPoint.hexPointCubic(3, -1);
        assertEquals(3, ex.getCubicX());
        assertEquals(-1, ex.getCubicY());
        assertEquals(-2, ex.getCubicZ());
    }

    @Test
    public void HexPointAxialTest() {
        HexPoint ex = HexPoint.hexPointAxial(3, -1);
        assertEquals(3, ex.getAxialX());
        assertEquals(-1, ex.getAxialY());
    }

    @Test
    public void HexPointOffsetTest() {
        HexPoint ex = HexPoint.hexPointOffset(3, -1);
        assertEquals(3, ex.getOffsetX());
        assertEquals(-1, ex.getOffsetY());
    }

}

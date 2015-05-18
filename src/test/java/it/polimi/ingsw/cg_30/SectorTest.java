package it.polimi.ingsw.cg_30;

import static org.junit.Assert.*;

import org.junit.Test;

public class SectorTest {

	@Test
	public void SectorTest() {
		HexPoint he = HexPoint.hexPointCubic(1, 2);
		Sector ex = new Sector(SectorType.Secure, he);
		assertEquals(SectorType.Secure, ex.getType());
		assertEquals(he, ex.getPoint());
	}

}

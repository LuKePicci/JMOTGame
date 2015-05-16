package it.polimi.ingsw.cg_30;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void incrementKillsCountTest() {
		Player ex = new Player();
		assertEquals(0, ex.getKillsCount());
		ex.incrementKillsCount();
		assertEquals(1,ex.getKillsCount());
	}
	
	@Test
	public void getterTest() {
		Player ex = new Player();
		assertEquals(0, ex.getIndex());
		assertEquals("unknown player",ex.getName());
		assertEquals(null, ex.getIdentity());
	}

}
package it.polimi.ingsw.cg_30;

import static org.junit.Assert.*;

import org.junit.Test;

public class BackupTest {

	@Test
	public void BackupTest() {
		Backup ex = new Backup(null);
		assertEquals(null, ex.getActiveParty());
	}

}

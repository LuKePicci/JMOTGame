package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BackupTest {

    @Test
    public void backupTester() {
        Backup ex = new Backup(null);
        assertEquals(null, ex.getActiveParty());
    }

}

package it.polimi.ingsw.cg_30.gamemanager.model;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.cg_30.gamemanager.model.Backup;

import org.junit.Test;

public class BackupTest {

    @Test
    public void BackupTester() {
        Backup ex = new Backup(null);
        assertEquals(null, ex.getActiveParty());
    }

}

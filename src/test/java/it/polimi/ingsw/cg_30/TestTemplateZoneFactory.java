package it.polimi.ingsw.cg_30;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

public class TestTemplateZoneFactory {
    private static final String TEST_MAP = "galilei";

    @Test
    public void shouldCreateZoneFromTemplate() throws FileNotFoundException {
        ZoneFactory zf = new TemplateZoneFactory(TEST_MAP);
        Zone z = zf.newMap();
        assertEquals(TEST_MAP, z.getMapName());
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldNotFindTemplateFile() throws FileNotFoundException {
        new TemplateZoneFactory("NOT_A_VALID_TEMPLATE_FILE");
    }
}

package it.polimi.ingsw.cg_30;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple GameServer.
 */
public class GameServerTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameServerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GameServerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testGameServer()
    {
        String[] args=new String[] {"Ciao"};
    	GameServer.main(args);
    	assertTrue( true );
    }
}

package it.polimi.ingsw.cg_30;

import it.polimi.ingsw.cg_30.gamemanager.network.TestAcceptRmiPlayer;
import it.polimi.ingsw.cg_30.gamemanager.network.TestAcceptSocketPlayer;
import it.polimi.ingsw.cg_30.gamemanager.network.TestSocketAcceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestSocketAcceptance.class, TestAcceptSocketPlayer.class,
        TestAcceptRmiPlayer.class })
public class GameManagerTestSuite {

}

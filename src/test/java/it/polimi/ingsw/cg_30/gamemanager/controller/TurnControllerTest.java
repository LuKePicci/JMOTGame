package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.gamemanager.model.Match;
import it.polimi.ingsw.cg_30.gamemanager.model.Party;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.model.Zone;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class TurnControllerTest {

    @Test
    public void firstTurnTest() throws FileNotFoundException,
            URISyntaxException {
        MatchController matchController = new MatchController() {

            @Override
            public void initMatch(PartyController partyController) {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController();
                Zone zone = new Zone();
                this.zoneController = new ZoneController(zone);
            }
        };
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        party.addToParty(UUID.randomUUID(), "player3");
        party.addToParty(UUID.randomUUID(), "player4");
        party.addToParty(UUID.randomUUID(), "player5");
        party.addToParty(UUID.randomUUID(), "player6");
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);
        matchController.initMatch(partyController);
        Player player0 = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 1) {
                player0 = nextPlayer;
            }
        }
        // eseguo
        matchController.getTurnController().firstTurn(players);
        // verifico
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(player0));
    }

    // eseguo un giro del party
    @Test
    public void partyTour() throws FileNotFoundException, URISyntaxException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController) {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController() {
                    @Override
                    protected boolean checkIfPlayerIsOnline(Player player,
                            MatchController matchController) {
                        return true;
                    }

                    @Override
                    protected void notify(Player nextPlayer,
                            MatchController matchController) {
                    }

                };
                Zone zone = new Zone();
                this.zoneController = new ZoneController(zone);
            }

            @Override
            public void checkEndGame() {
            }

        };
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        party.addToParty(UUID.randomUUID(), "player3");
        party.addToParty(UUID.randomUUID(), "player4");
        party.addToParty(UUID.randomUUID(), "player5");
        party.addToParty(UUID.randomUUID(), "player6");
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);
        matchController.initMatch(partyController);

        // turno di partenza
        Player startPlayer = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 7) {
                startPlayer = nextPlayer;
            }
        }
        Turn turn = new Turn(startPlayer);
        matchController.getTurnController().setTurn(turn);
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(startPlayer));
        int num = matchController.getMatch().getTurnCount();

        // cerco il player successivo
        Player endPlayer = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 1) {
                endPlayer = nextPlayer;
            }
        }

        // eseguo
        matchController.getTurnController().nextTurn(matchController);

        // verifico
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(endPlayer));
        assertTrue(matchController.getMatch().getTurnCount() == num + 1);
    }

    // tutto ok
    @Test
    public void allGood() throws FileNotFoundException, URISyntaxException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController) {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController() {
                    @Override
                    protected boolean checkIfPlayerIsOnline(Player player,
                            MatchController matchController) {
                        return true;
                    }

                    @Override
                    protected void notify(Player nextPlayer,
                            MatchController matchController) {
                    }

                };
                Zone zone = new Zone();
                this.zoneController = new ZoneController(zone);
            }

            @Override
            public void checkEndGame() {
            }

        };
        PlayerCard alien = new PlayerCard(PlayerRace.ALIEN, null);
        PlayerCard human = new PlayerCard(PlayerRace.HUMAN, null);
        Party party = new Party("test", new EftaiosGame(), false);
        PartyController partyController = PartyController.createNewParty(party);
        party.addToParty(UUID.randomUUID(), "player1");
        party.addToParty(UUID.randomUUID(), "player2");
        party.addToParty(UUID.randomUUID(), "player3");
        party.addToParty(UUID.randomUUID(), "player4");
        party.addToParty(UUID.randomUUID(), "player5");
        party.addToParty(UUID.randomUUID(), "player6");
        party.addToParty(UUID.randomUUID(), "player7");
        List<Player> players = new ArrayList<Player>(party.getMembers()
                .keySet());
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        Player player6 = players.get(5);
        Player player7 = players.get(6);
        player1.setIdentity(alien);
        player2.setIdentity(alien);
        player3.setIdentity(alien);
        player4.setIdentity(alien);
        player5.setIdentity(human);
        player6.setIdentity(human);
        player7.setIdentity(human);
        matchController.initMatch(partyController);

        // turno di partenza
        Player startPlayer = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 3) {
                startPlayer = nextPlayer;
            }
        }
        Turn turn = new Turn(startPlayer);
        matchController.getTurnController().setTurn(turn);
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(startPlayer));
        int num = matchController.getMatch().getTurnCount();

        // cerco il player successivo
        Player endPlayer = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 4) {
                endPlayer = nextPlayer;
            }
        }

        // eseguo
        matchController.getTurnController().nextTurn(matchController);

        // verifico
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(endPlayer));
        assertTrue(matchController.getMatch().getTurnCount() == num);
    }

}

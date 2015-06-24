package it.polimi.ingsw.cg_30.gamemanager.controller;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg_30.exchange.viewmodels.EftaiosGame;
import it.polimi.ingsw.cg_30.exchange.viewmodels.Item;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ItemCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerCard;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PlayerRace;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.gamemanager.model.Match;
import it.polimi.ingsw.cg_30.gamemanager.model.Party;
import it.polimi.ingsw.cg_30.gamemanager.model.Player;
import it.polimi.ingsw.cg_30.gamemanager.model.Turn;
import it.polimi.ingsw.cg_30.gamemanager.network.DisconnectedException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

public class TurnControllerTest {

    @Test
    public void firstTurnTest() throws FileNotFoundException,
            URISyntaxException, DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            protected void sendViewModelToAPlayer(Player p, ViewModel content) {
            }

            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this) {
                    @Override
                    protected boolean isPlayerOffline(Player player) {
                        return false;
                    }

                    @Override
                    protected void notify(Player nextPlayer) {
                    }

                };
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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
        Player player9 = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 1) {
                player9 = nextPlayer;
            }
        }
        // eseguo
        matchController.getTurnController().firstTurn();
        // verifico
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(player9));
    }

    // eseguo un giro del party
    @Test
    public void partyTour() throws FileNotFoundException, URISyntaxException,
            DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this) {
                    @Override
                    protected boolean isPlayerOffline(Player player) {
                        return false;
                    }

                    @Override
                    protected void notify(Player nextPlayer) {
                    }

                };
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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
        Turn turn = new Turn(startPlayer, matchController.getMatch()
                .getTurnCount());
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
        matchController.getTurnController().nextTurn();

        // verifico
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(endPlayer));
        assertTrue(matchController.getMatch().getTurnCount() == num + 1);
    }

    // tutto ok
    @Test
    public void allGood() throws FileNotFoundException, URISyntaxException,
            DisconnectedException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this) {
                    @Override
                    protected boolean isPlayerOffline(Player player) {
                        return false;
                    }

                    @Override
                    protected void notify(Player nextPlayer) {
                    }

                };
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
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
            if (nextPlayer.getIndex() == 2) {
                startPlayer = nextPlayer;
            }
        }
        Turn turn = new Turn(startPlayer, matchController.getMatch()
                .getTurnCount());
        matchController.getTurnController().setTurn(turn);
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(startPlayer));
        int num = matchController.getMatch().getTurnCount();

        // rimozione player morti/fuggiti
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 3) {
                matchController.getMatch().getDeadPlayer().add(nextPlayer);
            }
        }
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 4) {
                matchController.getMatch().getDeadPlayer().add(nextPlayer);
            }
        }
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 5) {
                matchController.getMatch().getDeadPlayer().add(nextPlayer);
            }
        }
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 6) {
                matchController.getMatch().getRescuedPlayer().add(nextPlayer);
            }
        }

        // cerco il player successivo
        Player endPlayer = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 7) {
                endPlayer = nextPlayer;
            }
        }

        // eseguo
        matchController.getTurnController().nextTurn();

        // verifico
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(endPlayer));
        assertTrue(matchController.getMatch().getTurnCount() == num);
    }

    @Test
    public void checkLegalityTest() throws DisconnectedException,
            FileNotFoundException, URISyntaxException {
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this) {
                    @Override
                    protected boolean isPlayerOffline(Player player) {
                        return false;
                    }

                    @Override
                    protected void notify(Player nextPlayer) {
                    }

                };
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
            }

            @Override
            public void checkEndGame() {
            }

            @Override
            protected void updateDeckView(Player player) {
            }

            @Override
            protected void sendTurnViewModel() {
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
        Turn turn = new Turn(startPlayer, matchController.getMatch()
                .getTurnCount());
        matchController.getTurnController().setTurn(turn);
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(startPlayer));
        int num = matchController.getMatch().getTurnCount();
        ItemCard card1 = new ItemCard(Item.ADRENALINE);
        ItemCard card2 = new ItemCard(Item.DEFENSE);
        ItemCard card3 = new ItemCard(Item.ATTACK);
        ItemCard card4 = new ItemCard(Item.SEDATIVES);
        startPlayer.getItemsDeck().getCards().add(card1);
        startPlayer.getItemsDeck().getCards().add(card2);
        startPlayer.getItemsDeck().getCards().add(card3);
        startPlayer.getItemsDeck().getCards().add(card4);
        matchController.getTurnController().getTurn().setMustDiscard(true);

        // cerco il player successivo
        Player endPlayer = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 4) {
                endPlayer = nextPlayer;
            }
        }

        // eseguo
        matchController.getTurnController().forcedDiscard = new DiscardCard() {
            @Override
            protected void notifyInChatByCurrentPlayer(String what) {
            }
        };
        assertTrue(matchController.getMatch().getItemsDeck().getBucket().size() == 0);
        assertTrue(startPlayer.getItemsDeck().getCards().size() == 4);
        matchController.getTurnController().nextTurn();

        // verifico
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(endPlayer));
        assertTrue(matchController.getMatch().getTurnCount() == num);
        assertTrue(matchController.getMatch().getItemsDeck().getBucket().size() == 1);
        assertTrue(startPlayer.getItemsDeck().getCards().size() == 3);
    }

    // turno 39 finito
    @Test
    public void endOf39Turn() throws FileNotFoundException, URISyntaxException,
            DisconnectedException {
        // preparo il terreno
        final Set<Player> vincitori = new HashSet<Player>();
        final Set<Player> perdenti = new HashSet<Player>();
        MatchController matchController = new MatchController() {
            @Override
            public void initMatch(PartyController partyController)
                    throws FileNotFoundException, URISyntaxException {
                this.partyController = partyController;
                this.match = new Match();
                this.turnController = new TurnController(this) {
                    @Override
                    protected boolean isPlayerOffline(Player player) {
                        return false;
                    }

                    @Override
                    protected void notify(Player nextPlayer) {
                    }
                };
                ZoneFactory zf = new TemplateZoneFactory(
                        EftaiosGame.DEFAULT_MAP);
                this.zoneController = new ZoneController(zf);
            }

            @Override
            public void checkEndGame() {
            }

            @Override
            protected void updateDeckView(Player player) {
            }

            @Override
            protected void sayYouWin(Set<Player> winners) {
                vincitori.addAll(winners);
            }

            @Override
            protected void sayYouLose(Set<Player> losers) {
                perdenti.addAll(losers);
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

        matchController.getMatch().getDeadPlayer().add(player3);
        matchController.getMatch().getDeadPlayer().add(player4);
        matchController.getMatch().getDeadPlayer().add(player5);
        matchController.getMatch().getRescuedPlayer().add(player6);
        for (int i = 1; i < 39; i++) {
            matchController.getMatch().incrementTurnCount();
        }
        // eseguo l'azione

        // turno di partenza
        Player startPlayer = new Player("", 9);
        for (Player nextPlayer : players) {
            if (nextPlayer.getIndex() == 7) {
                startPlayer = nextPlayer;
            }
        }
        Turn turn = new Turn(startPlayer, matchController.getMatch()
                .getTurnCount());
        matchController.getTurnController().setTurn(turn);
        assertTrue(matchController.getTurnController().getTurn()
                .getCurrentPlayer().equals(startPlayer));

        matchController.getTurnController().nextTurn();
        // verifico gli esiti
        assertTrue(perdenti.size() == 2);
        assertTrue(vincitori.size() == 5);
        assertTrue(perdenti.contains(player5));
        assertTrue(perdenti.contains(player7));
        assertTrue(vincitori.contains(player1));
        assertTrue(vincitori.contains(player2));
        assertTrue(vincitori.contains(player3));
        assertTrue(vincitori.contains(player4));
        assertTrue(vincitori.contains(player6));
    }

}

package it.polimi.ingsw.cg_30.gamemanager.model;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Game;
import it.polimi.ingsw.cg_30.exchange.viewmodels.PartyViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class Party.
 */
public class Party implements IViewable, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 228808363452233075L;

    /** The members map. */
    private Map<Player, UUID> members;

    /** The IDs map. */
    private Map<UUID, Player> ids;

    /** The nicknames mapping to UUID. */
    private Map<String, UUID> nicksToUUID;

    /** The UUIDs mapping to nickname. */
    private Map<UUID, String> uuidToNickName;

    /** The name of this party. */
    private String name;

    /** The current running game. */
    private Game currentGame;

    /** Privateness of this party. */
    private Boolean privateParty;

    /**
     * Instantiates a new party.
     *
     * @param name
     *            the name of the new party
     * @param g
     *            the game to play in this party
     * @param privateParty
     *            whether it is a private party
     */
    public Party(String name, Game g, Boolean privateParty) {
        this.name = name;
        this.privateParty = privateParty;
        this.currentGame = g;
        this.members = new HashMap<Player, UUID>();
        this.ids = new HashMap<UUID, Player>();
        this.nicksToUUID = new HashMap<String, UUID>();
        this.uuidToNickName = new HashMap<UUID, String>();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the game.
     *
     * @return the game
     */
    public Game getGame() {
        return this.currentGame;
    }

    /**
     * Checks if is private.
     *
     * @return the boolean
     */
    public Boolean isPrivate() {
        return this.privateParty;
    }

    /**
     * Gets the members.
     *
     * @return the members
     */
    public Map<Player, UUID> getMembers() {
        return members;
    }

    /**
     * Gets the player's uuid.
     *
     * @param p
     *            the player
     * @return the given player's uuid
     */
    public UUID getPlayerUUID(Player p) {
        return this.members.get(p);
    }

    /**
     * Gets the player by its uuid.
     *
     * @param p
     *            the player's uuid
     * @return the player with given uuid
     */
    public Player getPlayerByUUID(UUID id) {
        return this.ids.get(id);
    }

    /**
     * Gets the uuid by nickname.
     *
     * @param nickName
     *            the nick name
     * @return the nick uuid
     */
    public UUID getUUIDOfNick(String nickName) {
        return this.nicksToUUID.get(nickName);
    }

    /**
     * Gets the nickname of uuid.
     *
     * @param id
     *            the id
     * @return the nick of uuid
     */
    public String getNickOfUUID(UUID id) {
        return this.uuidToNickName.get(id);
    }

    /**
     * Adds the to party.
     *
     * @param clientId
     *            the client id
     * @param nickName
     *            the nick name
     * @return the party
     */
    public Party addToParty(UUID clientId, String nickName) {
        if (nicksToUUID.containsKey(nickName))
            nickName = nickName.concat("-" + clientId.hashCode());

        Player newPlayer = new Player(nickName, this.members.size() + 1);
        members.put(newPlayer, clientId);
        ids.put(clientId, newPlayer);
        nicksToUUID.put(nickName, clientId);
        uuidToNickName.put(clientId, nickName);
        return this;
    }

    @Override
    public ViewModel getViewModel() {
        List<ViewModel> pvmList = new ArrayList<ViewModel>();
        for (Player p : this.getMembers().keySet())
            pvmList.add(p.getViewModel());

        return new PartyViewModel(this.getName(), pvmList);
    }
}
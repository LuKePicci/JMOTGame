package it.polimi.ingsw.cg_30;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Zone extends GameTable<Sector> implements Serializable {

    private static final long serialVersionUID = -2636229005380853458L;

    private Iterable<Sector> sectors;

    public Map<Player, Sector> playersLocation;

    private List<Boolean> hatchesStatus = new ArrayList<Boolean>();

    public Iterable<Sector> getSectors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Sector getCell(Player player) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void movePlayer(Player who, Sector where) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Sector> reachableTargets(Sector from, Integer maxSteps) {
        throw new UnsupportedOperationException();
    }

    public Iterable<Player> getPlayersInSector(Sector sec) {
        throw new UnsupportedOperationException();
    }

    private void lockHatch(int hatchNumber) {
        throw new UnsupportedOperationException();
    }

    public void isHatchLocked(int hatchNumber) {
        throw new UnsupportedOperationException();
    }

    public boolean noMoreHatches() {
        throw new UnsupportedOperationException();
    }

}

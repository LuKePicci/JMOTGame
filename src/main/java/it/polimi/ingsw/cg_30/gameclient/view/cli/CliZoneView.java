package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ZoneViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliZoneView extends View {
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        ZoneViewModel viewModel = (ZoneViewModel) model;
        String textZone = "\r\nThis map contains the following sectors:\r\n";
        for (Sector sec : viewModel.getSectorsMap().values()) {
            textZone += this.getSectorRep(sec);
        }
        CliEngine.printLineToCli(textZone);
    }

    private String getSectorRep(Sector sec) {
        String rep = getCharFromNumber(sec.getPoint().getOffsetX() + 1)
                + String.format("%02d", (sec.getPoint().getOffsetY() + 1));
        switch (sec.getType()) {
            case DANGEROUS:
                rep += " dangerous";
                break;
            case SECURE:
                rep += " secure";
                break;
            case ALIENS_START:
                rep += " aliens' start";
                break;
            case HUMANS_START:
                rep += " humans' start";
                break;
            case ESCAPE_HATCH:
                rep += " escape hatch";
                break;
            case EMPTY:
                rep += " empty";
                break;
        }

        return rep + "\r\n";
    }
}

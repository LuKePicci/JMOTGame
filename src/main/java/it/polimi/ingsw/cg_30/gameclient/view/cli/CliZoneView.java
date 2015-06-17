package it.polimi.ingsw.cg_30.gameclient.view.cli;

import it.polimi.ingsw.cg_30.exchange.viewmodels.Sector;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ViewModel;
import it.polimi.ingsw.cg_30.exchange.viewmodels.ZoneViewModel;
import it.polimi.ingsw.cg_30.gameclient.view.View;

public class CliZoneView extends View {
    @Override
    public synchronized void applyUpdate(ViewModel model) {
        ZoneViewModel viewModel = (ZoneViewModel) model;
        CliEngine.printLineToCli("This map contains the following sectors:");
        for (Sector sec : viewModel.getSectorsMap().values()) {
            this.printSector(sec);
        }
    }

    private void printSector(Sector sec) {
        CliEngine.printToCli(""
                + this.getCharFromNumber(sec.getPoint().getOffsetX()));
        CliEngine.printToCli("" + sec.getPoint().getOffsetY() + 1);
        switch (sec.getType()) {
            case DANGEROUS:
                CliEngine.printLineToCli(" dangerous");
                break;
            case SECURE:
                CliEngine.printLineToCli(" secure");
                break;
            case ALIENS_START:
                CliEngine.printLineToCli(" aliens' start");
                break;
            case HUMANS_START:
                CliEngine.printLineToCli(" humans' start");
                break;
            case ESCAPE_HATCH:
                CliEngine.printLineToCli(" escape hatch");
                break;
            case EMPTY:
                CliEngine.printLineToCli(" empty");
                break;
        }
    }

}

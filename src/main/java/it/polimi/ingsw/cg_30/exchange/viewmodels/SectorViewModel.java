package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Sector")
public class SectorViewModel extends ViewModel {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "SectorOfInterest")
    private Sector sectorOfInterest;

    @XmlElement(name = "Highlight")
    private SectorHighlight highlight;

    public SectorViewModel(Sector sec, SectorHighlight highlight) {
        this();
        this.sectorOfInterest = sec;
        this.highlight = highlight;
    }

    private SectorViewModel() {
        // JAXB handled
        super(ViewType.SECTOR);
    }

    public Sector getSector() {
        return this.sectorOfInterest;
    }

    public SectorHighlight getHighlight() {
        return this.highlight;
    }

    @Override
    public String toString() {
        return "SectorViewModel{ sectorOfInterest: " + sectorOfInterest
                + ", highlight: " + highlight + "}";
    }

}

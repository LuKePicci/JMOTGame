package it.polimi.ingsw.cg_30.exchange.viewmodels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class SectorViewModel.
 */
@XmlRootElement(name = "Sector")
public class SectorViewModel extends ViewModel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The sector of interest. */
    @XmlElement(name = "SectorOfInterest")
    private Sector sectorOfInterest;

    /** The highlight. */
    @XmlElement(name = "Highlight")
    private SectorHighlight highlight;

    /**
     * Instantiates a new sector view model.
     *
     * @param sec
     *            the sector
     * @param highlight
     *            the highlight
     */
    public SectorViewModel(Sector sec, SectorHighlight highlight) {
        this();
        this.sectorOfInterest = sec;
        this.highlight = highlight;
    }

    /**
     * Instantiates a new sector view model.
     */
    private SectorViewModel() {
        // JAXB handled
        super(ViewType.SECTOR);
    }

    /**
     * Gets the sector.
     *
     * @return the sector
     */
    public Sector getSector() {
        return this.sectorOfInterest;
    }

    /**
     * Gets the highlight.
     *
     * @return the highlight
     */
    public SectorHighlight getHighlight() {
        return this.highlight;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SectorViewModel{ sectorOfInterest: " + sectorOfInterest
                + ", highlight: " + highlight + "}";
    }

}

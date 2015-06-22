package it.polimi.ingsw.cg_30.gameclient.view.gui.components;

import it.polimi.ingsw.cg_30.exchange.viewmodels.HexPoint;
import it.polimi.ingsw.cg_30.exchange.viewmodels.SectorType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

/**
 * A Swing compatible component used for represent an EFTAIOS zone's sector.
 * Basically a flat-topped hexagon with a type that determine its color;
 */
public class JSector extends JComponent {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4709887326177325508L;

    /** The Constant ColorMap. */
    public static final Map<SectorType, Color> ColorMap = new HashMap<SectorType, Color>();
    static {
        ColorMap.put(SectorType.EMPTY, Color.BLACK);
        ColorMap.put(SectorType.ALIENS_START, Color.RED);
        ColorMap.put(SectorType.HUMANS_START, Color.GREEN);
        ColorMap.put(SectorType.SECURE, Color.WHITE);
        ColorMap.put(SectorType.DANGEROUS, Color.LIGHT_GRAY);
        ColorMap.put(SectorType.ESCAPE_HATCH, Color.CYAN);
    }

    /** The position. */
    private Point position;

    /** The size. */
    private int size;

    /** The type. */
    private SectorType type = SectorType.EMPTY;

    /** The my shape. */
    private Polygon myShape;

    private final HexPoint myHp;

    /**
     * Instantiates a new j sector.
     *
     * @param position
     *            the position
     * @param size
     *            the size
     */
    public JSector(HexPoint hp, Point gridPosition, int size) {
        this.myHp = hp;
        this.position = gridPosition;
        this.size = size;
        this.setType(SectorType.DANGEROUS);
    }

    /**
     * Instantiates a new j sector.
     *
     * @param positionX
     *            the position x
     * @param positionY
     *            the position y
     * @param size
     *            the size
     */
    public JSector(HexPoint hp, int positionX, int positionY, int size) {
        this(hp, new Point(positionX, positionY), size);
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public SectorType getType() {
        return this.type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType(SectorType type) {
        this.type = type;
        this.setForeground(JSector.ColorMap.get(this.type));
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        // Set anti-alias!
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Set anti-alias for text
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Point xy;
        int[] x = new int[6];
        int[] y = new int[6];
        for (byte i = 0; i < 6; i++) {
            xy = this.hexCorner(this.position, this.size, i);
            x[i] = xy.x;
            y[i] = xy.y;
        }
        myShape = new Polygon(x, y, x.length);
        g.drawPolygon(myShape);
        g.fillPolygon(myShape);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(position.x, position.y * 2);
    }

    /**
     * Hex corner.
     *
     * @param center
     *            the center
     * @param size
     *            the size
     * @param i
     *            the i
     * @return the point
     */
    private Point hexCorner(Point center, int size, byte i) {
        int angleDeg = 60 * i;
        double angleRad = Math.PI / 180 * angleDeg;
        return new Point(
                (int) Math.round(center.x + size * Math.cos(angleRad)),
                (int) Math.round(center.y + size * Math.sin(angleRad)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#contains(java.awt.Point)
     */
    @Override
    public boolean contains(Point p) {
        return this.myShape.contains(p);
    }

    public HexPoint getHexPoint() {
        return this.myHp;
    }
}

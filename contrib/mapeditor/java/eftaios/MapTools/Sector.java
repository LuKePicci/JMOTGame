package eftaios.MapTools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

public class Sector extends JComponent {
	private static final long serialVersionUID = 1L;

	public static final Map<SectorType, Color> ColorMap = new HashMap<SectorType, Color>();
	static {
		ColorMap.put(SectorType.Empty, Color.BLACK);
		ColorMap.put(SectorType.AliensStart, Color.RED);
		ColorMap.put(SectorType.HumansStart, Color.GREEN);
		ColorMap.put(SectorType.Secure, Color.WHITE);
		ColorMap.put(SectorType.Dangerous, Color.LIGHT_GRAY);
		ColorMap.put(SectorType.EscapeHatch, Color.CYAN);
	}

	private PairXY position;
	private int size;
	private SectorType type = SectorType.Empty;

	public Sector(PairXY position, int size) {
		this.position = position;
		this.size = size;
		this.setType(SectorType.Dangerous);
	}

	public SectorType getType() {
		return this.type;
	}

	public void setType(SectorType type) {
		this.type = type;
		this.setForeground(Sector.ColorMap.get(this.type));
	}

	public Sector(int positionX, int positionY, int size) {
		this(new PairXY(positionX, positionY), size);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		PairXY xy;
		int[] x = new int[6];
		int[] y = new int[6];
		for (byte i = 0; i < 6; i++) {
			xy = this.hexCorner(this.position, this.size, i);
			x[i] = xy.X;
			y[i] = xy.Y;
		}
		Polygon p = new Polygon(x, y, x.length);
		g.drawPolygon(p);
		g.fillPolygon(p);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(position.X, position.Y * 2);
	}

	private PairXY hexCorner(PairXY center, int size, byte i) {
		int angleDeg = 60 * i;
		double angleRad = Math.PI / 180 * angleDeg;
		return new PairXY(center.X + size * Math.cos(angleRad), center.Y + size
				* Math.sin(angleRad));
	}
}

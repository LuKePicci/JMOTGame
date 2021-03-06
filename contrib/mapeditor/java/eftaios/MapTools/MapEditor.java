package eftaios.MapTools;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

import eftaios.forms.GameView;

public class MapEditor {
	public Sector[][] sectors;

	public static void main(String[] a) {
		try {
			HiFiLookAndFeel.setTheme("Giant-Font");
			UIManager.setLookAndFeel(new HiFiLookAndFeel());
		} catch (Exception e) {
			// use default lookAndFeel
		}
		MapEditor me = new MapEditor(new File(a.length > 0 ? a[0] : "map.xml"));
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				me.loadEditor();
			}
		});
	}

	private final int DIM_X, DIM_Y;
	private File xmlFile;

	public MapEditor(File xmlFile) {
		this(23, 14, xmlFile);
	}

	public MapEditor(int dimX, int dimY, File xmlFile) {
		this.DIM_X = dimX;
		this.DIM_Y = dimY;
		this.xmlFile = xmlFile;
		this.sectors = new Sector[DIM_X][DIM_Y];
	}

	private void loadEditor() {
		//
		// JFrame frame = new JFrame();
		// frame.setResizable(false);
		// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel mapPanel = new JPanel();
		mapPanel.setLayout(null);
		GameView gv = new GameView();

		if (this.xmlFile.isFile())
			loadMapFromXML(this.xmlFile, mapPanel);
		else
			drawBlankMap(mapPanel);

		// frame.getContentPane().add(mapPanel).setBackground(Color.BLACK);
		//
		// frame.setSize(1410, 1040);
		// frame.setLocationRelativeTo(null);
		//
		// frame.setTitle("EFTAIOS - Map Editor");
		// frame.setVisible(true);

		gv.fillMapPanel(mapPanel);
		gv.setVisible(true);
	}

	private void loadMapFromXML(File xmlFile, JPanel mapPanel) {
		MapModel mapFromFile = jaxbXMLToObject(xmlFile);
		// System.out.println(mapFromFile.toString());

		for (PairXY xy : mapFromFile.getSectors().keySet()) {
			Sector s = createSector(xy.X, xy.Y);
			s.setType(mapFromFile.getSectors().get(xy).getType());
			mapPanel.add(s);
		}
	}

	private void drawBlankMap(JPanel mapPanel) {
		for (int i = 0; i < DIM_X; i++)
			for (int j = 0; j < DIM_Y; j++) {
				mapPanel.add(createSector(i, j));
			}
	}

	private Sector createSector(int i, int j) {
		return createSector(i, j, 80);
	}

	private Sector createSector(int i, int j, int width) {
		int height = (int) Math.round(width * Math.sqrt(3) / 2);
		int horiz = Math.round((width * 3) / 4);
		Sector sector = new Sector(width / 2, height / 2 + 1, width / 2);
		sector.setSize(width, height);
		sector.setLocation(horiz * i, height * j + ((i % 2) * height / 2));

		// JLabel label = new JLabel(i + "/" + j);
		JLabel label = new JLabel(getCharForNumber(i + 1)
				+ String.format("%02d", (j + 1)));
		// label.setFont(new Font("TitilliumText22L Rg", 0, 25));
		label.setFont(loadCustomFont("TitilliumText22L"));

		label.setSize(width, height);
		label.setHorizontalAlignment(JLabel.CENTER);

		sector.add(label);

		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				super.mouseClicked(me);

				if (sector.contains(me.getPoint())) {
					System.out.println("Clicked sector " + sector.hashCode());
					sector.setType(SectorType.values()[(sector.getType()
							.ordinal() + 1) % SectorType.values().length]);
					System.out.println("Type switched to "
							+ sector.getType().toString());
					saveMapToXML(xmlFile);
				}
			}
		};
		sector.addMouseListener(ma);

		this.sectors[i][j] = sector;

		return sector;
	}

	private Font loadCustomFont(String fontName) {
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, this.getClass()
					.getResourceAsStream("/" + fontName + ".ttf"));
			return f.deriveFont(0, 25);
		} catch (Exception ioEx) {
			ioEx.printStackTrace();
			return new Font("Calibri", 0, 25);
		}
	}

	private void saveMapToXML(File xmlFile) {
		Map<PairXY, SectorModel> sectorMap = new TreeMap<PairXY, SectorModel>(
				new Comparator<PairXY>() {
					public int compare(PairXY o1, PairXY o2) {
						int comp = o1.X - o2.X;
						return comp == 0 ? o1.Y - o2.Y : comp;
					}
				});
		for (int i = 0; i < DIM_X; i++)
			for (int j = 0; j < DIM_Y; j++) {
				if (sectors[i][j] != null
						&& sectors[i][j].getType() != SectorType.EMPTY) {
					PairXY current = new PairXY(i, j);
					sectorMap.put(current,
							new SectorModel(sectors[i][j].getType()));
				}
			}
		MapModel map = new MapModel();
		map.setSectors(sectorMap);
		jaxbObjectToXML(xmlFile, map);
	}

	private static MapModel jaxbXMLToObject(File xmlFile) {
		try {
			JAXBContext context = JAXBContext.newInstance(MapModel.class);
			Unmarshaller un = context.createUnmarshaller();
			MapModel obj = (MapModel) un.unmarshal(xmlFile);
			return obj;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void jaxbObjectToXML(File xmlFile, MapModel obj) {

		try {
			JAXBContext context = JAXBContext.newInstance(MapModel.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(emp, System.out);

			// Write to File
			m.marshal(obj, xmlFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
	}
}

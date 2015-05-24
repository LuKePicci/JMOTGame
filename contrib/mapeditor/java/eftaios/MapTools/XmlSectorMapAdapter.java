package eftaios.MapTools;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlSectorMapAdapter extends
		XmlAdapter<MapElement, Map<PairXY, SectorModel>> {

	@Override
	public MapElement marshal(Map<PairXY, SectorModel> v) throws Exception {
		if (v == null || v.isEmpty()) {
			return null;
		}

		MapElement map = new MapElement();

		for (PairXY key : v.keySet()) {
			map.addEntry(key, v.get(key));
		}

		return map;

	}

	@Override
	public Map<PairXY, SectorModel> unmarshal(MapElement v) throws Exception {
		if (v == null) {
			return null;
		}

		Map<PairXY, SectorModel> map = new TreeMap<PairXY, SectorModel>(
				new Comparator<PairXY>() {
					public int compare(PairXY o1, PairXY o2) {
						int comp = o1.X - o2.X;
						return comp == 0 ? o1.Y - o2.Y : comp;
					}
				});

		for (MapEntry entry : v.entries) {
			map.put(entry.key, entry.value);
		}

		return map;
	}

}

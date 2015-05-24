package it.polimi.ingsw.cg_30;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlZoneMapAdapter extends
        XmlAdapter<XmlZoneMapElement, Map<HexPoint, Sector>> {

    @Override
    public XmlZoneMapElement marshal(Map<HexPoint, Sector> v) throws Exception {
        if (v == null || v.isEmpty()) {
            return null;
        }

        XmlZoneMapElement map = new XmlZoneMapElement();

        for (HexPoint key : v.keySet()) {
            map.addEntry(key, v.get(key));
        }

        return map;

    }

    @Override
    public Map<HexPoint, Sector> unmarshal(XmlZoneMapElement v)
            throws Exception {
        if (v == null) {
            return null;
        }

        Map<HexPoint, Sector> map = new HashMap<HexPoint, Sector>();

        for (XmlZoneMapEntry entry : v.entries) {
            map.put(entry.key, entry.value);
        }

        return map;
    }
}

package it.polimi.ingsw.cg_30;

import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlPartyMembersAdapter extends
        XmlAdapter<XmlPartyMemberElement, Map<Player, UUID>> {

    @Override
    public XmlPartyMemberElement marshal(Map<Player, UUID> v) throws Exception {
        if (v == null || v.isEmpty()) {
            return null;
        }

        XmlPartyMemberElement map = new XmlPartyMemberElement();

        for (Player key : v.keySet()) {
            map.addEntry(key, v.get(key));
        }

        return map;
    }

    @Override
    public Map<Player, UUID> unmarshal(XmlPartyMemberElement v)
            throws Exception {
        throw new UnsupportedOperationException();
    }

}

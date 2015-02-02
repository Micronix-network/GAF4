/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.util.xml.XMLObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kobo
 */
public class LayoutConfig extends XMLObject {

    private static final long serialVersionUID = 1L;

    public static final String TAG_NAME = "layout";
    public static final String name_att = "name";

    public LayoutConfig() {
        super(TAG_NAME);
    }

    public String getName() {
        return getAttribute(name_att);
    }

    public void setName(String name) {
        addAttribute(name_att, name);
    }

    public void addZone(ZoneConf zone) {
        this.addContent(zone);
    }

    ZoneConf getZone(String name) {
        if (name == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        List<XMLObject> wds = getChildren("zone");
        for (XMLObject zone : wds) {
            if (name.equals(((ZoneConf) zone).getName())) {
                return (ZoneConf) zone;
            }
        }
        return null;
    }

    void removeZone(String name) {
        ZoneConf wd = getZone(name);
        if (wd != null) {
            this.removeContent(wd);
        }
    }

    List<ZoneConf> getZones() {
        @SuppressWarnings("unchecked")
        List<ZoneConf> result = new ArrayList<ZoneConf>();
        List<XMLObject> wds = getChildren("zone");
        for (XMLObject xmlObject : wds) {
            result.add((ZoneConf) xmlObject);
        }
        return result;
    }

    public List<CardStatus> getCards() {
        List<CardStatus> result = new ArrayList<CardStatus>();
        List<ZoneConf> zones = getZones();
        if (zones != null && !zones.isEmpty()) {
            for (ZoneConf zoneConf : zones) {
                List<CardStatus> cards = zoneConf.getCards();
                if (cards != null && !cards.isEmpty()) {
                    result.addAll(cards);
                }
            }
        }
        return result;
    }

    public List<CardStatus> getPlacedCards() {
        List<CardStatus> result = new ArrayList<CardStatus>();
        List<ZoneConf> zones = getZones();
        if (zones != null && !zones.isEmpty()) {
            for (ZoneConf zoneConf : zones) {
                if (!zoneConf.getName().equals(LayoutConfigLoader.PARKING_ZONE)) {
                    List<CardStatus> cards = zoneConf.getCards();
                    if (cards != null && !cards.isEmpty()) {
                        result.addAll(cards);
                    }
                }
            }
        }
        return result;
    }

    public HashMap<String, CardStatus> getCardsMap() {
        HashMap<String, CardStatus> result = new HashMap<String, CardStatus>();
        List<ZoneConf> zones = getZones();
        if (zones != null && !zones.isEmpty()) {
            for (ZoneConf zoneConf : zones) {
                List<CardStatus> cards = zoneConf.getCards();
                if (cards != null && !cards.isEmpty()) {
                    for (CardStatus card : cards) {
                        result.put(card.getName(), card);
                    }

                }
            }
        }
        return result;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.domain.GafZone;
import it.micronixnetwork.gaf.domain.GafZoneCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kobo
 */
public class LayoutConfig {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer domain;

    private Map<String, GafZone> zones = new Hashtable<>();

    public LayoutConfig() {
    }

    public String getName() {
        return name;
    }

    public Integer getDomain() {
        return domain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDomain(Integer domain) {
        this.domain = domain;
    }
    
    

    public void addZone(GafZone zone) {
        zones.put(zone.getName(), zone);
    }

    GafZone getZone(String name) {
        return zones.get(name);
    }

    void removeZone(String name) {
        zones.remove(name);
    }

    Collection<GafZone> getZones() {
        return zones.values();
    }

    public List<GafZoneCard> getCards() {
        List<GafZoneCard> result = new ArrayList<>();
        Collection<GafZone> zones = getZones();
        if (zones != null && !zones.isEmpty()) {
            for (GafZone zoneConf : zones) {
                Collection<GafZoneCard> cards = zoneConf.getCards().values();
                if (cards != null && !cards.isEmpty()) {
                    result.addAll(cards);
                }
            }
        }
        return result;
    }

    public List<GafZoneCard> getPlacedCards() {
        List<GafZoneCard> result = new ArrayList<>();
        Collection<GafZone> zones = getZones();
        if (zones != null && !zones.isEmpty()) {
            for (GafZone zoneConf : zones) {
                if (!zoneConf.getName().equals(LayoutConfigLoader.PARKING_ZONE)) {
                    Collection<GafZoneCard> cards = zoneConf.getCards().values();
                    if (cards != null && !cards.isEmpty()) {
                        result.addAll(cards);
                    }
                }
            }
        }
        return result;
    }

    public HashMap<String, GafZoneCard> getCardsMap() {
        HashMap<String, GafZoneCard> result = new HashMap<String, GafZoneCard>();
        Collection<GafZone> zones = getZones();
        if (zones != null && !zones.isEmpty()) {
            for (GafZone zoneConf : zones) {
                Collection<GafZoneCard> cards = zoneConf.getCards().values();
                if (cards != null && !cards.isEmpty()) {
                    for (GafZoneCard card : cards) {
                        result.put(card.getCardname(), card);
                    }
                }
            }
        }
        return result;
    }
    
    public GafZoneCard getCardFromName(String cardname){
        Collection<GafZone> zones = getZones();
        GafZoneCard res=null;
        if (zones != null && !zones.isEmpty()) {
            for (GafZone zone : zones) {
                res=zone.getCards().get(cardname);
                if(res!=null)
                    return res;
            }
        }
        return null;
    }
}

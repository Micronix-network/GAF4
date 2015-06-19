package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.domain.Domain;
import it.micronixnetwork.gaf.domain.GafZone;
import it.micronixnetwork.gaf.domain.GafZoneCard;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.DomainService;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class LayoutDBConfigLoader extends HibernateSupport implements LayoutConfigLoader {

    private DomainService domainService;

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }

    private Hashtable<String, LayoutConfig> layouts = null;

    @Override
    public Hashtable<String, LayoutConfig> getLayouts() {
        return layouts;
    }

    @Override
    public void load() throws LayoutLoaderException {
        layouts = new Hashtable<String, LayoutConfig>();
        String hql = "From GafZone";
        Query query = createQuery(hql);
        List<GafZone> zones = (List<GafZone>) query.list();

        //Caricamento layout
        for (GafZone gafZone : zones) {
            LayoutConfig layout = layouts.get(gafZone.getDomain().getName());
            if (layout == null) {
                layout = new LayoutConfig();
                layout.setName(gafZone.getDomain().getName());
                layout.setDomain(gafZone.getIdDdomain());
                layouts.put(layout.getName(), layout);
            }
            //Creazione Zone config
            layout.addZone(gafZone);
        }
    }

    @Override
    public void reloadDomain(String layoutName) throws LayoutLoaderException {
        if (layouts != null && layoutName != null) {
            Domain domain = null;
            try {
                domain = domainService.retriveDomain(layoutName);
            } catch (ServiceException ex) {
                throw new LayoutLoaderException(ex);
            }
            if (domain == null) {
                return;
            }
            String hql = "From GafZone gf where gf.domain=:idDomain";
            Query query = createQuery(hql);
            query.setInteger("idDomain", domain.getId());
            List<GafZone> zones = (List<GafZone>) query.list();
            layouts.remove(domain.getName());
            LayoutConfig layout = new LayoutConfig();
            layout.setName(domain.getName());
            layout.setDomain(domain.getId());
            layouts.put(layout.getName(), layout);
            //Caricamento layout
            for (GafZone gafZone : zones) {
                layout.addZone(gafZone);
            }

        }

    }

    @Override
    public void save(String layoutName) throws LayoutLoaderException {
        synchronized (this) {
            LayoutConfig layout = layouts.get(layoutName);
            if (layout != null) {
                for (GafZone zone : layout.getZones()) {
                    getCurrentSession().saveOrUpdate(zone);
                }
            }
        }
    }

    @Override
    public LayoutConfig getLayoutConfig(String layoutName) {
        LayoutConfig lconf = layouts.get(layoutName);
        return lconf;
    }

    @Override
    public void removeCardFromZone(String layout, String zone, String card) {
        if (card == null) {
            return;
        }
        LayoutConfig lycfg = getLayoutConfig(layout);
        if (lycfg == null) {
            return;
        }
        GafZone zc = lycfg.getZone(zone);
        if (zc == null) {
            return;
        }
        zc.removeCard(card);
    }

    @Override
    public List<GafZoneCard> getLayOutPlacedCARD(String layoutName) {
        List<GafZoneCard> result = new ArrayList<>();
        LayoutConfig lconf = layouts.get(layoutName);
        if (lconf != null) {
            result = lconf.getPlacedCards();
        }
        return result;
    }

    @Override
    public boolean existZone(String layout, String zone) {
        if (layout == null || zone == null) {
            return false;
        }
        LayoutConfig lc = getLayoutConfig(layout);
        if (lc != null) {
            GafZone zc = lc.getZone(zone);
            if (zc != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GafZoneCard> getZoneCARDS(String layout, String zone) {
        List<GafZoneCard> result = new ArrayList<>();
        if (layout == null || zone == null) {
            return result;
        }
        LayoutConfig lc = getLayoutConfig(layout);
        if (lc != null) {
            GafZone zc = lc.getZone(zone);
            if (zc != null) {
                result.addAll(zc.getCards().values());
            }
        }
        return result;
    }

    @Override
    public void addCARDConfiguration(String layout, String zone, String cardname) throws LayoutLoaderException {
        Domain domain = null;
        try {
            domain = domainService.retriveDomain(layout);
        } catch (ServiceException ex) {
            throw new LayoutLoaderException(ex);
        }
        if (domain == null) {
            return;
        }
        LayoutConfig lc = getLayoutConfig(layout);
        if (lc == null) {
            // il layout non esiste lo creo
            lc = new LayoutConfig();
            lc.setName(layout);
            layouts.put(lc.getName(), lc);
        }
        GafZone zc = lc.getZone(zone);
        
        if(zc==null){
            zc = new GafZone(zone);
            zc.setIdDdomain(domain.getId());
            lc.addZone(zc);
        }
        
        GafZoneCard card = lc.getCardFromName(cardname);
        if (card != null) {
            card.getZone().removeCard(cardname);
        } else {
            card = new GafZoneCard();
            card.setCardname(cardname);
            card.setHidden(false);
            card.setPublished(false);
        }
        card.setZone(zc);
        zc.addCard(card);
    }

    public void afterPropertiesSet() throws Exception {
        load();
    }

    @Override
    public void addZone(String layout, String zone) throws LayoutLoaderException {
        Domain domain = null;
        try {
            domain = domainService.retriveDomain(layout);
        } catch (ServiceException ex) {
            throw new LayoutLoaderException(ex);
        }
        if (domain == null) {
            return;
        }
        LayoutConfig lc = getLayoutConfig(layout);
        if (lc == null) {
            // il layout non esiste lo creo
            lc = new LayoutConfig();
            lc.setName(layout);
            layouts.put(lc.getName(), lc);
        }
        GafZone zc = lc.getZone(zone);
        if (zc == null) {
            GafZone new_zc = new GafZone(zone);
            new_zc.setIdDdomain(domain.getId());
            lc.addZone(new_zc);
        }
    }

    @Override
    public void clearZone(String layout, String zone) {
        if (layout == null || zone == null) {
            return;
        }
        LayoutConfig lc = getLayoutConfig(layout);
        if (lc != null) {
            GafZone zc = lc.getZone(zone);
            if (zc != null) {
                zc.removeContents();
            }
        }
    }

    @Override
    public GafZone getLayoutZone(String layout, String zone) {
        if (layout == null || zone == null) {
            return null;
        }
        LayoutConfig lc = getLayoutConfig(layout);
        if (lc != null) {
            return lc.getZone(zone);
        }
        return null;
    }

    @Override
    public GafZoneCard getLayOutCARD(String layoutName, String cardName) {
        HashMap<String, GafZoneCard> cardMap = getLayoutCardMap(layoutName);
        if (cardMap != null) {
            return cardMap.get(cardName);
        }
        return null;
    }

    @Override
    public boolean existCardInLayout(String layout, String cardName) {
        HashMap<String, GafZoneCard> cardMap = getLayoutCardMap(layout);
        if (cardMap == null) {
            return false;
        }
        GafZoneCard wConf = cardMap.get(cardName);
        if (wConf != null) {
            return true;
        }
        return false;
    }

    @Override
    public Collection<String> getLayoutNames() {
        return layouts.keySet();
    }

    @Override
    public HashMap<String, GafZoneCard> getLayoutCardMap(String layout) {
        HashMap<String, GafZoneCard> result = new HashMap<>();
        LayoutConfig lconf = layouts.get(layout);
        if (lconf != null) {
            result = lconf.getCardsMap();
        }
        return result;
    }

    @Override
    public boolean isLoaded() {
        if (layouts != null) {
            return true;
        }
        return false;
    }
}

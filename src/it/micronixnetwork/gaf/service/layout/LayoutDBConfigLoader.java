package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.domain.GafZone;
import it.micronixnetwork.gaf.domain.GafZoneCard;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class LayoutDBConfigLoader extends HibernateSupport implements LayoutConfigLoader {
    
    private Hashtable<String, LayoutConfig> layouts = null;
    
    @Override
    public void setLayouts(Hashtable<String, LayoutConfig> layouts) {
       this.layouts=layouts;
    }
    
    @Override
    public Hashtable<String, LayoutConfig> getLayouts() {
	return layouts;
    }

    @Override
    public void load() throws LayoutLoaderException {
	layouts = new Hashtable<String, LayoutConfig>();
	String hql="From GafZone";
	Query query=createQuery(hql);
	List<GafZone> zones=(List<GafZone>)query.list();
	
	//Caricamento layout
	for (GafZone gafZone : zones) {
	    LayoutConfig layout= layouts.get(gafZone.getIdDomain());
	    if(layout==null){
		layout=new LayoutConfig();
		layout.setName(gafZone.getIdDomain());
		layouts.put(gafZone.getIdDomain(), layout);
	    }
	    //Creazione Zone config
	    ZoneConf zone=new ZoneConf();
	    layout.addZone(zone);
	    zone.setName(gafZone.getName());
	    if(gafZone.getWidth()!=null){
		zone.setWidth(gafZone.getWidth()+"");
	    }
	    if(gafZone.getHeight()!=null){
		zone.setHeight(gafZone.getHeight()+"");
	    }
	    if(gafZone.getClosed()!=null){
		zone.setClosed(gafZone.getClosed()+"");
	    }
	    for (GafZoneCard gafZoneCard : gafZone.getCards()) {
		    CardStatus card= new CardStatus();
		    if (gafZoneCard.getCardname()!=null) {
			card.setName(gafZoneCard.getCardname());
		    }
		    if (gafZoneCard.getHidden()!=null) {
			card.setHidden(gafZoneCard.getHidden().toString());
		    }
		    if (gafZoneCard.getPublished()!=null) {
			card.setPublished(gafZoneCard.getPublished().toString());
		    }
		    zone.addCard(card);
	    }
	}
    }
    
    @Override
    public void reloadDomain(String domain) throws LayoutLoaderException {
	if(layouts!=null){
	    String hql="From GafZone gf where gf.idDomain=:idDomain";
		Query query=createQuery(hql);
		query.setString("idDomain", domain);
		List<GafZone> zones=(List<GafZone>)query.list();
		layouts.remove(domain);
		LayoutConfig layout= new LayoutConfig();
		layout.setName(domain);
		layouts.put(domain, layout);
		
		//Caricamento layout
		for (GafZone gafZone : zones) {
		    //Creazione Zone config
		    ZoneConf zone=new ZoneConf();
		    layout.addZone(zone);
		    zone.setName(gafZone.getName());
		    if(gafZone.getWidth()!=null){
			zone.setWidth(gafZone.getWidth()+"");
		    }
		    if(gafZone.getHeight()!=null){
			zone.setHeight(gafZone.getHeight()+"");
		    }
		    if(gafZone.getClosed()!=null){
			zone.setClosed(gafZone.getClosed()+"");
		    }
		    for (GafZoneCard gafZoneCard : gafZone.getCards()) {
			    CardStatus card= new CardStatus();
			    if (gafZoneCard.getCardname()!=null) {
				card.setName(gafZoneCard.getCardname());
			    }
			    if (gafZoneCard.getHidden()!=null) {
				card.setHidden(gafZoneCard.getHidden().toString());
			    }
			    if (gafZoneCard.getPublished()!=null) {
				card.setPublished(gafZoneCard.getPublished().toString());
			    }
			    zone.addCard(card);
		    }
		}
	    
	}
	
    }

    @Override
    public void save() throws LayoutLoaderException {
        synchronized(this){
	//Clear
	String hql="TRUNCATE TABLE gaf_zone_cards";
	Query query=createQueryBySQL(hql);
	query.executeUpdate();
	hql="TRUNCATE TABLE gaf_zones";
	query=createQueryBySQL(hql);
	query.executeUpdate();
	
	//Write
	for (LayoutConfig layout : layouts.values()) {
	    for (ZoneConf zone : layout.getZones()) {
		GafZone gafZone=new GafZone();
		gafZone.setIdDomain(layout.getName());
		Boolean closed=zone.getClosed()!=null?zone.getClosed().equals("true"):null;
		gafZone.setClosed(closed);
		Integer width=zone.getWidth()!=null?Integer.parseInt(zone.getWidth()):null;
		gafZone.setWidth(width);
		Integer height=zone.getHeight()!=null?Integer.parseInt(zone.getHeight()):null;
		gafZone.setHeight(height);
		gafZone.setName(zone.getName());
		List<GafZoneCard> cards=new ArrayList<GafZoneCard>();
		for (CardStatus card : zone.getCards()) {
		    GafZoneCard gafZoneCard=new GafZoneCard();
		    gafZoneCard.setCardname(card.getName());
		    Boolean publish=card.getPublished()!=null?card.getPublished().equals("true"):null;
		    gafZoneCard.setPublished(publish);
		    Boolean hidden=card.getHidden()!=null?card.getHidden().equals("true"):null;
		    gafZoneCard.setHidden(hidden);
		    cards.add(gafZoneCard);
		    gafZoneCard.setZone(gafZone);
		}
		gafZone.setCards(cards);
		getCurrentSession().save(gafZone);
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
	if (card == null)
	    return;
	LayoutConfig lycfg = getLayoutConfig(layout);
	if (lycfg == null)
	    return;
	ZoneConf zc = lycfg.getZone(zone);
	if (zc == null)
	    return;
	zc.removeCard(card);
    }

    @Override
    public List<CardStatus> getLayOutPlacedCARD(String layoutName) {
	List<CardStatus> result = new ArrayList<CardStatus>();
	LayoutConfig lconf = layouts.get(layoutName);
	if(lconf!=null){
	    result=lconf.getPlacedCards();
	}
	return result;
    }

    @Override
    public boolean existZone(String layout, String zone) {
	if (layout == null || zone == null)
	    return false;
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc != null) {
	    ZoneConf zc = lc.getZone(zone);
	    if (zc != null) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public List<CardStatus> getZoneCARDS(String layout, String zone) {
	List<CardStatus> result = new ArrayList<CardStatus>();
	if (layout == null || zone == null)
	    return result;
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc != null) {
	    ZoneConf zc = lc.getZone(zone);
	    if (zc != null) {
		return zc.getCards();
	    }
	}
	return result;
    }

    @Override
    public void addCARDConfiguration(String layout, String zone, CardStatus card, boolean createZone) {
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc == null) {
	    // il layout non esiste lo creo
	    lc = new LayoutConfig();
	    lc.setName(layout);
	    layouts.put(lc.getName(), lc);
	}
	if (lc != null) {
	    ZoneConf zc = lc.getZone(zone);
	    if (zc != null) {
		zc.addCard(card);
	    } else {
		if (createZone) {
		    ZoneConf new_zc = new ZoneConf(zone);
		    lc.addZone(new_zc);
		    new_zc.addCard(card);
		}
	    }
	}
    }
    
   
  
    public void afterPropertiesSet() throws Exception {
	load();
    }
    
    @Override
    public void addZone(String layout, String zone) {
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc == null) {
	    // il layout non esiste lo creo
	    lc = new LayoutConfig();
	    lc.setName(layout);
	    layouts.put(lc.getName(), lc);
	}
	ZoneConf zc = lc.getZone(zone);
	if (zc == null) {
	    ZoneConf new_zc = new ZoneConf(zone);
	    lc.addZone(new_zc);
	}
    }

    @Override
    public void clearZone(String layout, String zone) {
	if (layout == null || zone == null)
	    return;
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc != null) {
	    ZoneConf zc = lc.getZone(zone);
	    if (zc != null) {
		zc.removeContents();
	    }
	}
    }

    @Override
    public ZoneConf getLayoutZone(String layout, String zone) {
	if (layout == null || zone == null)
	    return null;
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc != null) {
	    return lc.getZone(zone);
	}
	return null;
    }

    @Override
    public CardStatus getLayOutCARD(String layoutName, String cardName) {
	HashMap<String, CardStatus> cardMap = getLayoutCardMap(layoutName);
	if (cardMap != null)
	    return cardMap.get(cardName);
	return null;
    }

    @Override
    public boolean existCardInLayout(String layout, String cardName) {
	HashMap<String, CardStatus> cardMap = getLayoutCardMap(layout);
	if (cardMap == null)
	    return false;
	CardStatus wConf = cardMap.get(cardName);
	if (wConf != null)
	    return true;
	return false;
    }

    @Override
    public Collection<String> getLayoutNames() {
	return layouts.keySet();
    }

    @Override
    public HashMap<String, CardStatus> getLayoutCardMap(String layout) {
	HashMap<String, CardStatus> result = new HashMap<String, CardStatus>();
	LayoutConfig lconf = layouts.get(layout);
	if(lconf!=null){
	    result=lconf.getCardsMap();
	}
	return result;
    }

    @Override
    public boolean isLoaded() {
	if(layouts!=null) return true;
	return false;
    }

   

}

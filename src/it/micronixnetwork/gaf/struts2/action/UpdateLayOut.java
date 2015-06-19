package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.domain.Domain;
import it.micronixnetwork.gaf.domain.GafZone;
import it.micronixnetwork.gaf.domain.GafZoneCard;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.service.layout.LayoutConfig;
import it.micronixnetwork.gaf.service.layout.LayoutConfigLoader;
import it.micronixnetwork.gaf.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UpdateLayOut extends CardAction {

    private static final long serialVersionUID = 1L;

    private static final int ZONE_ORDER = 1;
    private static final int CARD = 3;
    private static final int ZONE_SIZE = 2;

    private List<String> cardsOrder;
    private String layout;
    private String zoneName;
    private String zoneWidth;
    private String zoneHeight;
    private String zoneClosed;
    private String hidden;
    private String publish;
    private String cardName;
    private Integer optType;

    public List<String> getCardsOrder() {
	return cardsOrder;
    }

    public void setCardsOrder(String cardsOrder) {
	this.cardsOrder = StringUtil.stringToList(cardsOrder);
    }

    public String getLayout() {
	return layout;
    }

    public void setLayout(String layout) {
	this.layout = layout;
    }

    public String getZoneName() {
	return zoneName;
    }

    public void setZoneName(String zoneName) {
	this.zoneName = zoneName;
    }

    public String getZoneClosed() {
	return zoneClosed;
    }

    public void setZoneClosed(String zoneClosed) {
	this.zoneClosed = zoneClosed;
    }

    public String getHidden() {
	return hidden;
    }

    public void setHidden(String hidden) {
	this.hidden = hidden;
    }

    public String getPublish() {
	return publish;
    }

    public void setPublish(String publish) {
	this.publish = publish;
    }

    public String getCardName() {
	return cardName;
    }

    public void setCardName(String cardName) {
	this.cardName = cardName;
    }

    public void setZoneHeight(String zoneHeight) {
	this.zoneHeight = zoneHeight;
    }

    public String getZoneHeight() {
	return zoneHeight;
    }

    public void setZoneWidth(String zoneWidth) {
	this.zoneWidth = zoneWidth;
    }

    public String getZoneWidth() {
	return zoneWidth;
    }

    public Integer getOptType() {
	return optType;
    }

    public void setOptType(Integer optType) {
	this.optType = optType;
    }

    @Override
    protected String exe() throws ApplicationException {
	debugXML(this, 4);
	if (optType == null)
	    return NONE;
	LayoutConfigLoader lcl = getLayoutConfigLoader();

	if (lcl == null) {
	    throw new ApplicationException("<b>LayOutConfigLoader not configured check Spring's context file</b>");
	}
        
        Domain domain=domainService.retriveDomain(layout);
        
        if (domain == null) {
	    throw new ApplicationException("<b>Domain not defined</b>");
	}

	debug("Prima della cura: \n" + lcl.getLayoutConfig(layout));

	switch (optType) {
	case ZONE_ORDER:
	    Iterator<String> names = cardsOrder.iterator();
	    while (names.hasNext()) {
		String cardName = names.next();
		if (cardName != null && !cardName.trim().equals("")) {
		    lcl.addCARDConfiguration(layout, zoneName, filterName(cardName));
		}
	    }
	    break;
	case ZONE_SIZE:
	    GafZone zone = lcl.getLayoutZone(layout, zoneName);
	    // La zona non è ancora stata configurata;
	    if (zone == null) {
		zone = new GafZone(zoneName);
                zone.setIdDdomain(domain.getId());
		LayoutConfig lc = lcl.getLayoutConfig(layout);
		if (lc != null) {
		    lc.addZone(zone);
		}
	    }

	    if (zoneHeight != null) {
                try{
                    int h=Integer.parseInt(zoneHeight);
                    zone.setHeight(h);
                }catch(Exception ex){}
		
	    }
	    if (zoneWidth != null) {
                try{
                    int w=Integer.parseInt(zoneWidth);
                    zone.setWidth(w);
                }catch(Exception ex){}
	    }
	    if (zoneClosed != null) {
		if (zoneClosed.equals("true")) {
		    zone.setClosed(Boolean.TRUE);
		} else {
		    zone.setClosed(Boolean.FALSE);
		}
	    }

	    break;
	case CARD:
	    GafZoneCard wc = lcl.getLayOutCARD(layout, cardName);
	    debug(wc);
	    if (wc != null) {
		if (hidden != null) {
		    wc.setHidden(hidden.equals("true"));
		}
		if (publish != null) {
		    wc.setPublished(publish.equals("true"));
		}
	    }
	    break;

	default:
	    break;
	}

	debug("Dopo la cura: \n" + lcl.getLayoutConfig(layout));
        
	lcl.save(layout);
	return NONE;
    }

    private String filterName(String cardName) {
	if (cardName == null) {
	    return null;
	}
	return cardName.replaceAll("_card", "");
    }

   
}

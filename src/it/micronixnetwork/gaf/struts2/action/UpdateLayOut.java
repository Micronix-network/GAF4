package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.service.layout.CardStatus;
import it.micronixnetwork.gaf.service.layout.LayoutConfig;
import it.micronixnetwork.gaf.service.layout.LayoutConfigLoader;
import it.micronixnetwork.gaf.service.layout.ZoneConf;
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
	    throw new ApplicationException(errorMessage());
	}

	debug("Prima della cura: \n" + lcl.getLayoutConfig(layout));

	switch (optType) {
	case ZONE_ORDER:
	    Iterator<String> names = cardsOrder.iterator();
	    List<CardStatus> new_card_list = new ArrayList<CardStatus>();
	    while (names.hasNext()) {
		String wName = names.next();
		if (wName != null && !wName.trim().equals("")) {
		    CardStatus wc = lcl.getLayOutCARD(layout, filterName(wName));
		    if (wc == null) {
			wc = new CardStatus();
			wc.setName(filterName(wName));
			wc.setHidden("false");
		    }
		    new_card_list.add(wc);
		}
	    }
	    // Elimina le CARD dalla zono da modificare
	    lcl.clearZone(layout, zoneName);
	    if (!new_card_list.isEmpty()) {
		for (CardStatus cardConf : new_card_list) {
		    lcl.addCARDConfiguration(layout, zoneName, cardConf, true);
		}
	    }
	    break;
	case ZONE_SIZE:
	    ZoneConf zone = lcl.getLayoutZone(layout, zoneName);
	    // La zona non è ancora stata configurata;
	    if (zone == null) {
		zone = new ZoneConf(zoneName);
		LayoutConfig lc = lcl.getLayoutConfig(layout);
		if (lc != null) {
		    lc.addZone(zone);
		}
	    }

	    if (zoneHeight != null) {
		zone.setHeight(zoneHeight);
	    }
	    if (zoneWidth != null) {
		zone.setWidth(zoneWidth);
	    }
	    if (zoneClosed != null) {
		if (zoneClosed.equals("true")) {
		    zone.setClosed("true");
		} else {
		    zone.setClosed("false");
		}
	    }

	    break;
	case CARD:
	    CardStatus wc = lcl.getLayOutCARD(layout, cardName);
	    debug(wc);
	    if (wc != null) {
		if (hidden != null) {
		    wc.setHidden(hidden);
		}
		if (publish != null) {
		    wc.setPublished(publish);
		}
	    }
	    break;

	default:
	    break;
	}

	debug("Dopo la cura: \n" + lcl.getLayoutConfig(layout));
	lcl.save();
	return NONE;
    }

    private String filterName(String cardName) {
	if (cardName == null) {
	    return null;
	}
	return cardName.replaceAll("_card", "");
    }

    private String errorMessage() {
	StringBuilder sbuild = new StringBuilder("<b>LayOutConfigLoader not configured check Spring's context file</b>");
	return sbuild.toString();
    }
}

package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.CardConfService;
import it.micronixnetwork.gaf.service.GAFService;
import it.micronixnetwork.gaf.util.xml.XMLDocument;
import it.micronixnetwork.gaf.util.xml.XMLObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class LayoutXMLConfigLoader extends GAFService implements ContentHandler, LayoutConfigLoader {

    /**
     * SAX Parser
     */
    private XMLReader parser = null;
    private Resource configFile;
    /**
     * HashTable contenente i layoutCaricati
     */
    private Hashtable<String, LayoutConfig> layouts = null;
    
    @Override
    public void setLayouts(Hashtable<String, LayoutConfig> layouts) {
        this.layouts=layouts;
    }
    
    @Override
    public Hashtable<String, LayoutConfig> getLayouts() {
	return layouts;
    }
    
    private Hashtable<String, Map> layoutCards = null;
    private HashMap<String, CardStatus> actualCardMap = null;
    private LayoutConfig actualLayOut;
    private CardStatus actualCard;
    private ZoneConf actualZone;
    private StringBuilder actualText;
    private CardConfService cardConfService;

    public void setConfigFile(Resource configFile) {
	this.configFile = configFile;
    }

    public void setCardConfService(CardConfService cardConfService) {
	this.cardConfService = cardConfService;
    }

    private LayoutXMLConfigLoader() throws LayoutLoaderException {
	SAXParserFactory factory = SAXParserFactory.newInstance();
	factory.setValidating(false);
	factory.setNamespaceAware(true);
	SAXParser saxParser;
	try {
	    saxParser = factory.newSAXParser();
	    parser = saxParser.getXMLReader();
	    parser.setContentHandler(this);
	} catch (ParserConfigurationException e) {
	    throw new LayoutLoaderException("Errore nell'instanza del QueryLoader", e);
	} catch (SAXException e) {
	    throw new LayoutLoaderException("Errore nell'instanza del QueryLoader", e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#load()
     */
    @Override
    public void load() throws LayoutLoaderException {
	layouts = new Hashtable<String, LayoutConfig>();
	layoutCards = new Hashtable<String, Map>();
	InputStream stream = null;
	try {
	    if (configFile != null) {
		debug("Getting layouts info from file: " + configFile.getURI().toString());
		stream = configFile.getInputStream();
	    } else {
		debug("Getting layouts info from db (domain:site  cardname:site  propname:layout)");
		String layout_xml = cardConfService.getProperty("-1", "site", "site", "layout");
		if (layout_xml == null) {
		    throw new FileNotFoundException();
		}
		stream = new ByteArrayInputStream(layout_xml.getBytes());
	    }
	    parser.parse(new InputSource(stream));
	    stream.close();
	} catch (IOException ex) {
	    if (ex instanceof FileNotFoundException) {
		try {
		    save();
		} catch (ServiceException e) {
		    throw new LayoutLoaderException("Errore nella creazione del nuovo file " + configFile, ex);
		}
	    } else {
		throw new LayoutLoaderException("Errore nel caricamento della risorsa" + configFile, ex);
	    }
	} catch (SAXException sex) {
	    throw new LayoutLoaderException("Errore nel parsing del file " + configFile, sex);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#save()
     */
    @Override
    synchronized public void save() throws LayoutLoaderException {
	XMLDocument doc = new XMLDocument();
	XMLObject root = new XMLObject("layouts");
	doc.setXMLRoot(root);
	Iterator<LayoutConfig> lys = layouts.values().iterator();
	while (lys.hasNext()) {
	    LayoutConfig layoutConfig = lys.next();
	    root.addContent(layoutConfig);
	}
	try {
	    if (configFile != null) {
		debug("Save layouts info into file: " + configFile.getURI().toString());
		File toSave = configFile.getFile();
		if (toSave.exists()) {
		    toSave.delete();
		}
		FileOutputStream fo = new FileOutputStream(toSave);
		fo.write(doc.describe().getBytes());
		fo.close();
	    } else {
		debug("Save layouts info into db (domain:site  cardname:site  propname:layout)");
		cardConfService.saveProperty("-1", "site", "site", "layout", null, doc.describe());
	    }
	} catch (IOException e) {
	    throw new LayoutLoaderException(e);
	}
	// resourceAgent.saveResource(configFile, doc.describe().getBytes());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#startElement
     * (java.lang.String, java.lang.String, java.lang.String,
     * org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String name, String fullname, Attributes list) throws SAXException {
	if (name.equals(LayoutConfig.TAG_NAME)) {
	    actualLayOut = new LayoutConfig();
	    actualLayOut.setName(list.getValue(LayoutConfig.name_att));
	    actualCardMap = new HashMap<String, CardStatus>();
	}
	if (name.equals(ZoneConf.TAG_NAME)) {
	    actualZone = new ZoneConf();
	    if (list.getValue(ZoneConf.name_att) != null) {
		actualZone.setName(list.getValue(ZoneConf.name_att));
	    }
	    if (list.getValue(ZoneConf.width_att) != null) {
		actualZone.setWidth(list.getValue(ZoneConf.width_att));
	    }
	    if (list.getValue(ZoneConf.height_att) != null) {
		actualZone.setHeight(list.getValue(ZoneConf.height_att));
	    }
	    if (list.getValue(ZoneConf.closed_att) != null) {
		actualZone.setClosed(list.getValue(ZoneConf.closed_att));
	    }
	}
	if (name.equals(CardStatus.TAG_NAME)) {
	    actualCard = new CardStatus();
	    if (list.getValue(CardStatus.name_att) != null) {
		actualCard.setName(list.getValue(CardStatus.name_att));
	    }
	    if (list.getValue(CardStatus.hidden_att) != null) {
		actualCard.setHidden(list.getValue(CardStatus.hidden_att));
	    }
	    if (list.getValue(CardStatus.published_att) != null) {
		actualCard.setPublished(list.getValue(CardStatus.published_att));
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#endElement(java
     * .lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public void endElement(String uri, String name, String fullName) throws SAXException {
	if (name.equals(LayoutConfig.TAG_NAME)) {
	    layouts.put(actualLayOut.getName(), actualLayOut);
	    if (!actualCardMap.isEmpty()) {
		layoutCards.put(actualLayOut.getName(), actualCardMap);
	    }
	    debug("Loaded layouts: " + actualLayOut.getName() + "\n" + actualLayOut);
	    actualLayOut = null;
	    actualCardMap = null;
	}

	if (name.equals(ZoneConf.TAG_NAME)) {
	    if (actualLayOut != null) {
		if (actualZone != null) {
		    actualLayOut.addZone(actualZone);
		    actualZone = null;
		    return;
		}
	    }
	}

	if (name.equals(CardStatus.TAG_NAME)) {
	    if (actualZone != null) {
		if (actualCard != null) {
		    if (actualZone.getName().equals(PARKING_ZONE))
			actualCard.setHidden("true");
		    actualZone.addCard(actualCard);
		    actualCardMap.put(actualCard.getName(), actualCard);
		    actualCard = null;
		    return;
		}
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#characters(char
     * [], int, int)
     */
    @Override
    public void characters(char buffer[], int start, int length) throws SAXException {
	String text = new String(buffer, start, length);
	if (text.trim().length() > 0) {
	    if (actualText == null) {
		actualText = new StringBuilder(text);
	    } else {
		actualText.append(text);
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#getLayoutConfig
     * (java.lang.String)
     */
    @Override
    public LayoutConfig getLayoutConfig(String layoutName) {
	LayoutConfig lconf = layouts.get(layoutName);
	return lconf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#removeCardFromZone
     * (java.lang.String, java.lang.String, java.lang.String)
     */
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
	HashMap<String, CardStatus> map = (HashMap<String, CardStatus>) layoutCards.get(layout);
	if (map != null) {
	    map.remove(card);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#getLayOutPlacedCARD
     * (java.lang.String)
     */
    @Override
    public List<CardStatus> getLayOutPlacedCARD(String layoutName) {
	List<CardStatus> result = new ArrayList<CardStatus>();
	LayoutConfig lycfg = getLayoutConfig(layoutName);
	if (lycfg != null) {
	    List<ZoneConf> zones = lycfg.getZones();
	    if (zones != null && !zones.isEmpty()) {
		for (ZoneConf zoneConf : zones) {
		    List<CardStatus> cards = zoneConf.getCards();
		    if (cards != null && !cards.isEmpty()) {
			result.addAll(cards);
		    }
		}
	    }
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#existZone(java
     * .lang.String, java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#getZoneCARDS
     * (java.lang.String, java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#addCARDConfiguration
     * (java.lang.String, java.lang.String,
     * it.micronixnetwork.gaf.service.layout.CardStatus, boolean)
     */
    @Override
    public void addCARDConfiguration(String layout, String zone, CardStatus card, boolean createZone) {
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc == null) {
	    // il layouts non esiste lo creo
	    lc = new LayoutConfig();
	    lc.setName(layout);
	    addLayoutConfig(lc);
	}
	if (lc != null) {
	    ZoneConf zc = lc.getZone(zone);
	    if (zc != null) {
		zc.addCard(card);
		addLayOutCARDConfiguration(layout, card);
	    } else {
		if (createZone) {
		    ZoneConf new_zc = new ZoneConf(zone);
		    lc.addZone(new_zc);
		    new_zc.addCard(card);
		    addLayOutCARDConfiguration(layout, card);
		}
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#addZone(java
     * .lang.String, java.lang.String)
     */
    @Override
    public void addZone(String layout, String zone) {
	LayoutConfig lc = getLayoutConfig(layout);
	if (lc == null) {
	    // il layouts non esiste lo creo
	    lc = new LayoutConfig();
	    lc.setName(layout);
	    addLayoutConfig(lc);
	}
	ZoneConf zc = lc.getZone(zone);
	if (zc == null) {
	    ZoneConf new_zc = new ZoneConf(zone);
	    lc.addZone(new_zc);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#clearZone(java
     * .lang.String, java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#getLayoutZone
     * (java.lang.String, java.lang.String)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#getLayOutCARD
     * (java.lang.String, java.lang.String)
     */
    @Override
    public CardStatus getLayOutCARD(String layoutName, String cardName) {
	HashMap<String, CardStatus> cardMap = getLayoutCardMap(layoutName);
	if (cardMap != null)
	    return cardMap.get(cardName);
	return null;
    }

    private void addLayOutCARDConfiguration(String layout, CardStatus card) {
	if (card == null || card.getName() == null)
	    return;
	if (!existCardInLayout(layout, card.getName())) {
	    HashMap<String, CardStatus> cardMap = getLayoutCardMap(layout);
	    if (cardMap == null) {
		cardMap = new HashMap<String, CardStatus>();
		layoutCards.put(layout, cardMap);
	    }
	    cardMap.put(card.getName(), card);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#existCardInLayout
     * (java.lang.String, java.lang.String)
     */
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

    private void addLayoutConfig(LayoutConfig lc) {
	layouts.put(lc.getName(), lc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#getLayoutConfigName
     * ()
     */
    @Override
    public Collection<String> getLayoutNames() {
	return layouts.keySet();
    }

    public static void main(String[] args) {
	try {
	    LayoutConfigLoader runner = new LayoutXMLConfigLoader();
	    // runner.setConfigFile("layouts.xml");
	    try {
		runner.load();
		Iterator<String> iters = runner.getLayoutNames().iterator();
		while (iters.hasNext()) {
		    String lay = iters.next();
		    System.out.println(runner.getLayoutConfig(lay));
		}
		// runner.save();
	    } catch (Exception e) {
		e.printStackTrace();
		System.exit(100);
	    }
	} catch (LayoutLoaderException ex) {
	    ex.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#getLayoutCardMap
     * (java.lang.String)
     */
    @Override
    public HashMap<String, CardStatus> getLayoutCardMap(String layout) {
	return (HashMap<String, CardStatus>) layoutCards.get(layout);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#endDocument()
     */

    @Override
    public void endDocument() throws SAXException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#endPrefixMapping
     * (java.lang.String)
     */

    @Override
    public void endPrefixMapping(String arg0) throws SAXException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#ignorableWhitespace
     * (char[], int, int)
     */

    @Override
    public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#
     * processingInstruction(java.lang.String, java.lang.String)
     */

    @Override
    public void processingInstruction(String arg0, String arg1) throws SAXException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#setDocumentLocator
     * (org.xml.sax.Locator)
     */

    @Override
    public void setDocumentLocator(Locator arg0) {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#skippedEntity
     * (java.lang.String)
     */

    @Override
    public void skippedEntity(String arg0) throws SAXException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#startDocument()
     */

    @Override
    public void startDocument() throws SAXException {
	// TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.micronixnetwork.gaf.service.layout.LayoutConfigLoader#startPrefixMapping
     * (java.lang.String, java.lang.String)
     */

    @Override
    public void startPrefixMapping(String arg0, String arg1) throws SAXException {
	// TODO Auto-generated method stub

    }

    @Override
    public void reloadDomain(String domain) throws LayoutLoaderException {
	
    }
    
    @Override
    public boolean isLoaded() {
	if(layouts!=null) return true;
	return false;
    }
}

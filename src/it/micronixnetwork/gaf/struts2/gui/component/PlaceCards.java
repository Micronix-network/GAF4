package it.micronixnetwork.gaf.struts2.gui.component;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.service.layout.LayoutConfigLoader;
import it.micronixnetwork.gaf.struts2.gui.model.CardModel;
import it.micronixnetwork.gaf.struts2.gui.model.CardModelsCache;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.opensymphony.xwork2.util.ValueStack;
import it.micronixnetwork.gaf.domain.GafZoneCard;

/**
 * Tag che aggiunge un insieme di CARD in una zona specificata
 * 
 * @author kobo
 */

public class PlaceCards extends GAFGuiComponent {

    private String layout;
    private String zone;

    private List<CardModel> cardModels;


    public PlaceCards(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
    }

    public List<CardModel> getCardModels() {
	return cardModels;
    }

    public String getZone() {
	return zone;
    }

    public String getLayout() {
	return layout;
    }

    public String test(String ciao) {
	return ciao;
    }

    public void setLayout(String layout) {
	this.layout = layout;
    }

    public void setZone(String zone) {
	this.zone = zone;
    }

    public boolean end(Writer writer, String body) {
	try {
	    debug("Start Place Cards");
	    Boolean isSuper = (Boolean) stack.findValue("superUser");
	    debug("Recupero delle CARD da aggiungere alla zona: "+zone);
	    cardModels = matchCards(isSuper);
	    templateDir = "gaf/tag";
	    templateName = "place_cards.ftl";
	    debug("Rendering di: " + cardModels.size() + " CARDS");
	    renderTemplate(writer,body);
	} catch (Exception ex) {
	    error("Eccezione nell' inserimento della CARD", ex);
	} finally {
	}
	debug("Fine inserimento CARDS nella zona: " + zone);
	return super.end(writer, body);
    }

    /**
     * Recupera la lista ordinata delle CARD da visualizzare nella zona facendo
     * un match fra il file di configurazione e e i modelli caricati in sessione
     * dai tag 'load_card'
     * 
     * @param isSuper
     * @return
     * @throws ApplicationException
     */
    private List<CardModel> matchCards(Boolean isSuper) throws ApplicationException {

	debug("Sono in modalità amminstratore?: " + isSuper);
	
	Set<String> placed=(Set<String>) stack.findValue("placed");

	List<CardModel> result = new ArrayList<CardModel>();

	// Mappa dei modelli caricati nella pagina, la kiave è il nome
	// unico della CARD
	CardModelsCache cardMap = (CardModelsCache) stack.findValue("cardModels");

	if (cardMap != null)
	    debug("Esiste una cache di CARD");

	// Se non esistono CARD caricate nella pagina si ritorna la lista
	// vuota
	if (cardMap == null || cardMap.isEmpty()) {
	    return result;
	}

	debug("CARD caricate nella pagina: " + cardMap.size());

	LayoutConfigLoader lcl = getLayoutConfigLoader();
	if (lcl == null) {
	    throw new ApplicationException("LayoutXMLConfigLoader non istanziato");
	}

	// Controllo che la zona dichiarata nel tag sia presente nella struttura
	// di confgigurazione
	// se non è presente la aggiungo e restituisco la lista vuota visto che
	// se non esiste non può
	// nemmeno contenere CARD
	if (!lcl.existZone(layout, zone)) {
	    debug("La zona: " + zone + " non esiste la creo");
	    lcl.addZone(layout, zone);
	    if (!zone.equals(LayoutConfigLoader.PARKING_ZONE))
		return result;
	}

	// La zona è dichiarata quindi vengono recuperate le CARD definite nella
	// configurazione
	List<GafZoneCard> cardsConf = lcl.getZoneCARDS(layout, zone);

	debug("Configurazione della zona: "+zone);
	debug(lcl.getLayoutZone(layout, zone));

	// Si procede a gestire il caso della parking zone è necessario inserire nel result le CARD
	// caricate ma non piazzate in nessuna zona e quelle piazzate in zone non esistenti
	// (capita quando viene cambiata la grid)
	if (zone.equals(LayoutConfigLoader.PARKING_ZONE)) {
	    Collection<CardModel> cardModels = (Collection<CardModel>) cardMap.values();
	    for (CardModel card : cardModels) {
		if (!lcl.existCardInLayout(layout, card.getName()) || !placed.contains(card.getName())) {
		    //Se la card caricata non è presente nella configurazione
		    //viene aggiunta alla zona di parking
		    card.setHide(true);
		    card.setPublished(false);
		    card.setZone(LayoutConfigLoader.PARKING_ZONE);
		    result.add(card);
		}
	    }
	} else{
        // Per ogni CARD configurata si controlla se è stata caricata nella
	// pagina
	for (GafZoneCard conf : cardsConf) {
	    if (cardMap.containsKey(conf.getCardname())) {
		// La CARD è stata inserita è possibile aggiungerla al risutato
		// settando i parametri mancanti al modello
		debug("CARD: "+conf.getCardname()+" caricata e piazzabile in "+zone);
		if (!conf.getHidden()) {
		    CardModel model = (CardModel) cardMap.get(conf.getCardname());
		    model.setZone(zone);
		    model.setHide(conf.getHidden());
		    model.setPublished(conf.getPublished());
		    result.add(model);
		}
	    } 
	}
        
        }

	return result;
    }

    private LayoutConfigLoader getLayoutConfigLoader() {
	LayoutConfigLoader lcl = (LayoutConfigLoader) stack.findValue("layoutConfigLoader");
	return lcl;
    }

}

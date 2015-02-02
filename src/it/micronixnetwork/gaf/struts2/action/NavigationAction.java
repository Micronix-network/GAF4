package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.domain.Card;
import it.micronixnetwork.gaf.domain.Domain;
import it.micronixnetwork.gaf.struts2.gui.model.CardDomainCache;
import it.micronixnetwork.gaf.struts2.gui.model.CardModel;
import it.micronixnetwork.gaf.struts2.gui.model.CardModelsCache;
import it.micronixnetwork.gaf.util.StringUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import it.micronixnetwork.gaf.service.layout.CardStatus;
import java.util.HashSet;
import java.util.List;

public class NavigationAction extends WebAppAction {

    private static final long serialVersionUID = -713189298025248992L;

    public final static String DOMAIN_MAP_KEY = "it.micronixnetwork.gaf.struts2.action.Navigation.DECK_MAP_KEY ";

    private String domain;

    private Integer pageHeight;

    private String cardsGrid;

    private boolean dirty;

    protected String exe() throws it.micronixnetwork.gaf.exception.ApplicationException {

	// Rimozione della chache delle CARD al cambio pagina
	HttpSession session = request.getSession();
	session.removeAttribute(CardModelsCache.CARD_MODEL_KEY);

	String actionName = ActionContext.getContext().getActionInvocation().getProxy().getActionName();

	// Decodifica dominio
	domain = actionName.replace("nav_", "");

	// Controllo esistenza mappa di DECK
	Map application = (Map) ActionContext.getContext().get("application");

	Map<String, Domain> domainMap = (Map<String, Domain>) application.get(DOMAIN_MAP_KEY);

	if (domainMap == null) {
	    domainMap = domainService.retriveDomains();
	    application.put(DOMAIN_MAP_KEY, domainMap);
	} else {
	    if (dirty || isSuperUser()) {
		Domain newDomain = domainService.retriveDomain(domain);
		domainMap.put(domain, newDomain);
	    }
	}
	
	//Recupero dominio
	Domain pageDomain=domainMap.get(domain);
	
	//Inizializzazione layoutConfigLoader
	if(layoutConfigLoader!=null && !layoutConfigLoader.isLoaded()){
	   layoutConfigLoader.load(); 
	}
	
	String reload=appProperties.getProperty("layout.reload");
	
	if(layoutConfigLoader!=null && reload!=null && reload.equals("true")){
	    layoutConfigLoader.reloadDomain(pageDomain.getId());
	}

	// Preparazione domainCache e cardModelCache

	pageHeight = pageDomain.getHeight();
	cardsGrid = pageDomain.getCardGrid();

	CardDomainCache domainCache = (CardDomainCache) session.getAttribute(CardDomainCache.cards_domain_KEY);

	if (domainCache == null) {
	    domainCache = new CardDomainCache();
	    session.setAttribute(CardDomainCache.cards_domain_KEY, domainCache);
	} else {
	    domainCache.remove(domain);
	}

	CardModelsCache cardModelCache = new CardModelsCache();
	domainCache.put(domain, cardModelCache);

	session.setAttribute(CardModelsCache.CARD_MODEL_KEY, cardModelCache);

	// Cariacamento CARDS

	for (Card card : pageDomain.getCards()) {
	    CardModel cardModel = new CardModel();
	    // Recupera i paramteri dal DB
	    debug("Cerco di recuperare la configurazione del card:" + card.getCardname() + " dal db");
	    Map<String, Object> card_params = loadCardParams(domain, card.getCardname());
	    debug("Effettuata la chiamata al DB per il recupero dei parametri");
	    if (card_params != null && !card_params.isEmpty()) {
		debug("Trovati: " + card_params.size() + " parametri");
		Iterator<String> keys = card_params.keySet().iterator();
		while (keys.hasNext()) {
		    String key = (String) keys.next();
		    cardModel.addParam(key, card_params.get(key));
		}
	    } else {
		debug("Nessun parametro trovato");
	    }

	    cardModel.setDomain(domain);
	    cardModel.setName(card.getCardname());
	    // Normalizzazione action
	    cardModel.setAction(card.getType() + "_entry");

	    if (StringUtil.EmptyOrNull(card.getNamespace())) {
		cardModel.setNamespace(card.getType());
	    } else {
		cardModel.setNamespace(card.getNamespace());
	    }

	    cardModel.setType(card.getType());
	    cardModelCache.put(cardModel.getName(), cardModel);
	}

	return super.exe();
    }
    
    
    public Set<String> getPlaced(){
        Set<String> result=new HashSet<String>();
	List<CardStatus> cards=layoutConfigLoader.getLayOutPlacedCARD(domain);
        for (CardStatus card : cards) {
            result.add(card.getName());
        }
        return result;
    }

    public String getDomain() {
	return domain;
    }

    public void setDomain(String domain) {
	this.domain = domain;
    };

    public String getCardsGrid() {
	return cardsGrid;
    }

    public Integer getPageHeight() {
	return pageHeight;
    }

    public void setDirty(boolean dirty) {
	this.dirty = dirty;
    }

    public boolean isDirty() {
	return dirty;
    }

}

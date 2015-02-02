package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.gui.model.CardDomainCache;
import it.micronixnetwork.gaf.struts2.gui.model.CardModelsCache;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class ChangeDomainContext extends CardAction{

	private static final long serialVersionUID = 1L;
	
	private String domain;
	private String message;

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public String getMessage() {
	    return message;
	}
	

	@Override
	protected String exe() throws ApplicationException {
	    
	    	if(domain==null){
	    	    message="error: null domain";
		    return SUCCESS;
	    	}
	    
	    	ActionContext context = ActionContext.getContext();
		if(context==null) {
		    message="error: Action context null";
		    return SUCCESS;
		}
		
		Map session = context.getSession();
		
		if(session==null) {
		    message="error: session mull";
		    return SUCCESS;
		}
	    
	    	CardDomainCache domainCache=(CardDomainCache) session.get(CardDomainCache.cards_domain_KEY);
		
	    	if(domainCache==null) {
		    message="error: Domain cache null";
		    return SUCCESS;
		}
		
	    	CardModelsCache cardMap = (CardModelsCache) domainCache.get(domain);
	    	
	    	if(cardMap==null) {
		    message="error: No CARDS models map for domain: "+domain;
		    return SUCCESS;
		}
	    	
	    	session.put(CardModelsCache.CARD_MODEL_KEY, cardMap);
		
	    	message="success";
		info("Domain changed to: "+domain);
		
		return SUCCESS;
	}

}

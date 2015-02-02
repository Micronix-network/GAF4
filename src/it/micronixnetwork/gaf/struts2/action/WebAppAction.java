/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.domain.Menu;
import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.CardConfService;
import it.micronixnetwork.gaf.service.ConfigService;
import it.micronixnetwork.gaf.service.DomainService;
import it.micronixnetwork.gaf.service.FileSystemService;
import it.micronixnetwork.gaf.service.MenuService;
import it.micronixnetwork.gaf.service.QueryService;
import it.micronixnetwork.gaf.service.AccountService;
import it.micronixnetwork.gaf.service.layout.LayoutConfigLoader;
import it.micronixnetwork.gaf.struts2.gui.model.CardModelsCache;
import it.micronixnetwork.gaf.util.StringUtil;
import it.micronixnetwork.gaf.util.Struts2Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author kobo
 */
public class WebAppAction extends BaseAction implements ServletRequestAware, ServletResponseAware {

    private static final long serialVersionUID = 1L;

    
    //---Sezione servizi
    protected AccountService accountService;

    protected QueryService queryService;
    
    protected CardConfService cardConfService;
    
    protected MenuService menuService;
    
    protected FileSystemService fileSystemService;
    
    protected DomainService domainService;
    
    protected ConfigService gafConfigService;
    
    protected LayoutConfigLoader layoutConfigLoader;
    
    public void setAccountService(AccountService userService) {
	this.accountService = userService;
    }
    
    public void setCardConfService(CardConfService cardConfService) {
	this.cardConfService = cardConfService;
    }
    
    public void setQueryService(QueryService queryService) {
   	this.queryService = queryService;
       }
    
    public void setMenuService(MenuService menuService){
	this.menuService=menuService;
    }
    
    public void setFileSystemService(FileSystemService fileSystemService) {
	this.fileSystemService = fileSystemService;
    }
    
    public void setDomainService(DomainService domainService) {
	this.domainService = domainService;
    }
    
    public void setGafConfigService(ConfigService gafConfigService) {
	this.gafConfigService = gafConfigService;
    }
    
    //---
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;


    /**
     * Recupera la lista dei Datasource configurati nel file contex.xml
     * @return 
     * @throws ActionException
     */
    public List<String> getDataSources() throws ActionException {
	Context initCtx = null;
	try {
	    initCtx = new InitialContext();
	} catch (NamingException e) {
	    throw new ActionException(e);
	}
	Context envCtx = null;
	if (initCtx != null)
	    try {
		envCtx = (Context) initCtx.lookup("java:comp/env");
	    } catch (NamingException e) {
		throw new ActionException(e);
	    }

	List<String> result = new ArrayList<String>();

	if (envCtx != null) {
	    try {
		NamingEnumeration<NameClassPair> lista = envCtx.list("jdbc");
		while (lista.hasMoreElements()) {
		    NameClassPair nameClassPair = (NameClassPair) lista.nextElement();
		    result.add("jdbc/" + nameClassPair.getName());
		}
	    } catch (NamingException e) {
		throw new ActionException(e);
	    }
	}
	return result;
    }
    
    /**
     * Ritorna il path assoluto dell'applicazione web
     * @return
     */
    public String getApplicationPath(){
	ServletContext context = ServletActionContext.getServletContext();
	return context.getRealPath("/");
    }

    /**
     * Carica la mappa dei parametri di una CARD in un dominio
     * 
     * @param domain
     *            il dominio relativo ai parametri
     * @param cardname
     *            il nome univoco, relativamente al dominio, della CARD
     * @return la mappa dei parametri
     */
    public Map<String, Object> loadCardParams(String domain, String cardname) {
	if (cardConfService == null)
	    return null;
	RoledUser user = (RoledUser) getUser();
	String id_user = null;
	if (user != null) {
	    id_user = user.getId();
	}
	if(isSuperUser()){
	    id_user="-1";
	}
	return cardConfService.getConf(id_user, domain, cardname);
    }
    
    /**
     * Restituisce la cache dei modelli delle CARD attualmente in uso
     * @return
    */
    public CardModelsCache getCardModels() {
	
	ActionContext context = ActionContext.getContext();
	if (context == null)
	    return null;
	Map session = context.getSession();
	if (session != null) {
	    return (CardModelsCache) session.get(CardModelsCache.CARD_MODEL_KEY);
	}
	return null;
    }
    
    /**
     * Recupera il modello del menu dal database
     * @return
     */
    public Menu getMainMenu() {
	try {
	    return menuService.getMenu(false);
	} catch (ServiceException e) {
	    error("Menu error", e);
	}
	return null;
    }
    
    
    /**
     * Esegue una query sql utilizzando il DataSouce applicativo
     * @param query la query da eseguire
     * @param unique indica se il risutato è una lista di valori o un singolo elemento
     * @return una lista di valori o un singolo risultato
     */
    public Object executeSQLQuery(String query, boolean unique) {
	if (queryService != null) {
	    try {
		if (!unique)
		    return queryService.search(query, null, true);
		else
		    return queryService.uniqueResult(query, null, true);
	    } catch (ServiceException e) {
		error("executeSQL error", e);
	    }
	}
	return null;
    }

    /**
     * Esegue una query hql utilizzando il DataSouce applicativo
     * @param query la query da eseguire
     * @param unique indica se il risutato è una lista di valori o un singolo elemento
     * @return una lista di valori o un singolo risultato
     */
    public Object executeHQLQuery(String query, boolean unique) {
	if (queryService != null) {
	    try {
		if (!unique)
		    return queryService.search(query, null, false);
		else
		    return queryService.uniqueResult(query, null, false);
	    } catch (ServiceException e) {
		error("executeSQL error", e);
	    }
	}
	return null;
    }

    /**
     * Restituisce tutti i ruoli definiti nell'applicativo
     * @return la lista dei Ruoli
     */
    public Collection<String> getAllRoles() {
	try {
	    return accountService.getAllRole();
	} catch (ServiceException se) {
	    error("getRoles error", se);
	}
	return null;
    }

    @Override
    public String execute() throws Exception {
	RoledUser user = getUser();
	String logMsg = "execute: ";
	if (user != null) {
	    if (user instanceof RoledUser) {
		logMsg += "-->by:  " + ((RoledUser) user).getUserName();
	    }
	}
	debug(logMsg);
	return super.execute();
    }

    @Override
    public String input() throws Exception {
	RoledUser user = getUser();
	String logMsg = "execute input method: ";
	if (user != null) {
	    if (user instanceof RoledUser) {
		logMsg += "-->by:  " + ((RoledUser) user).getUserName();
	    }
	}
	debug(logMsg);
	return super.input();
    }
    
    
    /**
     * Controlla il ruolo di un utente
     * @param roles i ruli utente
     * @param toCheck i ruoli ammessi
     * @return
     */
    public boolean checkRole(String[] roles, List<String> toCheck) {
	return StringUtil.checkStringExistenz(roles, toCheck);
    }
    
    
    /**
     * Controlla il ruolo di un utente
     * @param roles i ruli utente
     * @param toCheck i ruoli ammessi in formato "Ruolo1,Ruolo2,.."
     * @return
     */
    public boolean checkRole(String[] roles, String toCheck) {
	return StringUtil.checkStringExistenz(roles, toCheck);
    }
    
    /**
     * Controlla il ruolo di un utente
     * @param roles i ruli utente
     * @param toCheck i ruoli ammessi
     * @return
     */
    public boolean checkRole(String[] roles, String[] toCheck) {
	return StringUtil.checkStringExistenz(roles, toCheck);
    }

    protected void validInput() {
    }

    @Override
    protected String exe() throws ApplicationException {
	return SUCCESS;
    }

    public void setServletRequest(HttpServletRequest request) {
	this.request = request;
    }

    public HttpServletRequest getServletRequest() {
	return request;
    }

    public void setServletResponse(HttpServletResponse response) {
	this.response = response;
    }

    public HttpServletResponse getServletResponse() {
	return response;
    }
    
    public String calcAction(String action,String namespace,String method){
   	return Struts2Util.calcAction(request, action, namespace, method);
       }

    public LayoutConfigLoader getLayoutConfigLoader() {
	return layoutConfigLoader;
    }

    public void setLayoutConfigLoader(LayoutConfigLoader layoutConfigLoader) {
	this.layoutConfigLoader = layoutConfigLoader;
    }

}

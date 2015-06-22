/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.util.LogUtil;
import it.micronixnetwork.gaf.util.StringUtil;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author a.riboldi
 */
public abstract class BaseAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    public static String DEV_MODE_PROP = "app.devMode";
    public static String DYNA_MENU_PROP = "app.dynamic.menu";
    public static String THEME_PROP = "app.theme";
    public static String APPNAME_PROP = "app.name";
    public static String ACTIVE_USECASE = "it.td.struts2.paf.action.BaseAction.ACTIVE_USECASE";
    public static String DEF_THEME = "default";

    protected Properties appProperties;

    private final Log log = LogFactory.getLog(getClass());

    private String activeUseCase;

    protected String message;

    public BaseAction() {
    }

    protected Properties getAppProperties() {
	return appProperties;
    }

    public void setAppProperties(Properties appProperties) {
	this.appProperties = appProperties;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    /**
     * Ritorna l'utente loggato
     * @return
     */
    public RoledUser getUser() {
	ActionContext context = ActionContext.getContext();
	if (context == null)
	    return null;
	Map session = context.getSession();
	return (RoledUser) session.get(RoledUser.USER_TICKET);
    }

    /**
     * Ritorna se l'utente loggato è un super user
     * @return
     */
    public Boolean isSuperUser() {
	ActionContext context = ActionContext.getContext();
	if (context == null)
	    return Boolean.FALSE;
	Map session = context.getSession();
	return session.get(RoledUser.SUPER_TICKET) != null;
    }

    /**
     * Ritorna se è abilitata la modalità sviluppo
     * @return
     */
    public Boolean getDevMode() {
	String value = appProperties.getProperty(DEV_MODE_PROP);
	if (!StringUtil.EmptyOrNull(value)) {
	    return value.equals("true");
	}
	return false;
    }
    
    /**
     * Ritorna se il menu deve essere ricaricato ogni volta 
     * @return
     */
    public Boolean getDynaMenu() {
	String value = appProperties.getProperty(DYNA_MENU_PROP);
	if (!StringUtil.EmptyOrNull(value)) {
	    return value.equals("true");
	}
	return false;
    }
    

    /**
     * Ritorna il nome del tema utilizzato il default è 'default'
     * @return
     * @throws ServiceException
     */
    public String getThemeName() throws ServiceException {
	String value = appProperties.getProperty(THEME_PROP);
	if (!StringUtil.EmptyOrNull(value)) {
	    return value;
	}
	return DEF_THEME;
    }

    /**
     * Ritorna il nome dell'applicazione come specificato nel file di properties app.properties
     * @return
     * @throws ServiceException
     */
    public String getAppName() throws ServiceException {
	String value = appProperties.getProperty(APPNAME_PROP);
	if (!StringUtil.EmptyOrNull(value)) {
	    return value;
	}
	return "none";
    }

    public void setActiveUseCase(String activeUseCase) {
	this.activeUseCase = activeUseCase;
    }

    /**
     * Ritorna il nome del caso d'uso corrente
     * @return
     */
    public String getActiveUseCase() {
	return this.activeUseCase;
    }

    @Override
    public String execute() throws Exception {
	String result = exe();
	defineActiveUseCase();
	return result;
    }

    private void defineActiveUseCase() {
	ActionContext context = ActionContext.getContext();
	if (context != null) {
	    ActionInvocation invocation = context.getActionInvocation();
	    if (invocation != null) {
		activeUseCase = invocation.getProxy().getActionName();
	    }
	}
    }

    protected void info(String msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.INFO, "A");
    }

    protected void debug(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.DEBUG, "A");
    }

    protected void debug(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.DEBUG, "A");
    }

    protected void error(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.ERROR, "A");
    }

    protected void fatal(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.FATAL, "A");
    }

    protected void warn(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.WARN, "A");
    }

    protected void debugXML(Object msg, int level) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.logXML(ste, this.getClass().getName(), log, msg, LogUtil.LOGTYPE.DEBUG, level, "A");
    }

    protected abstract String exe() throws ApplicationException;
}

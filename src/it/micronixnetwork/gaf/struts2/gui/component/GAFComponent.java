/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.gui.component;

import it.micronixnetwork.gaf.util.LogUtil;
import it.micronixnetwork.gaf.util.XMLBeanViewer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.TextProvider;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * @author kobo
 */
public class GAFComponent extends Component {

    protected Log log = LogFactory.getLog(GAFComponent.class);

    public GAFComponent(ValueStack stack) {
	super(stack);
    }

    public String getText(String key, String defaultMessage, String[] args) {
	String msg = null;
	TextProvider tp = null;
	for (Iterator iterator = stack.getRoot().iterator(); iterator.hasNext();) {
	    Object o = iterator.next();
	    if (o instanceof TextProvider) {
		tp = (TextProvider) o;
		msg = tp.getText(key,args);
		if (!key.equals(msg) && msg != null)
		    break;
	    }
	}

	if (key.equals(msg) || msg == null) {
	    if (defaultMessage != null)
		msg = tp.getText(key,defaultMessage,args);
	    else
		msg = key;
	}
	return msg;
    }

    public String getText(String key, String defaultMessage) {
	return getText(key, defaultMessage, new String[0]);
    }

    public boolean checkRole(String[] roles, List<String> toCheck) {
	if (checkSuper())
	    return true;
	if (roles == null || roles.length == 0)
	    return false;
	if (toCheck == null || toCheck.size() == 0)
	    return true;
	for (String role : roles) {
	    if (toCheck.contains(role))
		return true;
	}
	return false;
    }

    public boolean checkSuper() {
	Boolean isSuper = (Boolean) stack.findValue("superUser");
	if (isSuper)
	    return true;
	return false;
    }

    public void info(String msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.INFO, "T");
    }

    public void debug(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.DEBUG, "T");
    }

    public void debug(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.DEBUG, "T");
    }

    public void error(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.ERROR, "T");
    }

    public void warn(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.WARN, "T");
    }

    public void fatal(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.FATAL, "T");
    }

    public void debugXML(Object msg, int level) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.logXML(ste, this.getClass().getName(), log, msg, LogUtil.LOGTYPE.DEBUG, level, "T");
    }

}

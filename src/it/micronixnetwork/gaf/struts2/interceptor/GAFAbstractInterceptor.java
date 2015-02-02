/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.struts2.interceptor;

import it.micronixnetwork.gaf.util.LogUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @author kobo
 */
public abstract class GAFAbstractInterceptor extends AbstractInterceptor {
    protected transient Log log = LogFactory.getLog(getClass());

    protected void info(String msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, null, LogUtil.LOGTYPE.INFO,"I");
    }

    protected void debug(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, null, LogUtil.LOGTYPE.DEBUG,"I");
    }

    protected void debug(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.DEBUG,"I");
    }

    protected void error(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.ERROR,"I");
    }

    protected void fatal(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.FATAL,"I");
    }

    protected void debugXML(Object msg, int level) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.logXML(ste, this.getClass().getSimpleName(), log, msg, LogUtil.LOGTYPE.DEBUG, level,"I");
    }
}

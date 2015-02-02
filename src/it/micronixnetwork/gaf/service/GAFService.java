package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GAFService {
    
    private final static Log log = LogFactory.getLog(GAFService.class);
    
    protected void info(String msg) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, null, LogUtil.LOGTYPE.INFO,"S");
       }

       protected void debug(Object msg) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, null, LogUtil.LOGTYPE.DEBUG,"S");
       }

       protected void debug(Object msg, Throwable e) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.DEBUG,"S");
       }

       protected void error(Object msg, Throwable e) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.ERROR,"S");
       }

       protected void fatal(Object msg, Throwable e) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.FATAL,"S");
       }

       protected void debugXML(Object msg, int level) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.logXML(ste, this.getClass().getSimpleName(), log, msg, LogUtil.LOGTYPE.DEBUG, level,"S");
       }

}

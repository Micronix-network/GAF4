package it.micronixnetwork.gaf.struts2.result;

import it.micronixnetwork.gaf.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GAFResult {
    
    private final static Log log = LogFactory.getLog(GAFResult.class);
    
    static void  info(Class clazz,String msg) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, clazz.getSimpleName(), log, msg, null, LogUtil.LOGTYPE.INFO,"RES");
       }

    static  void debug(Class clazz,Object msg) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, clazz.getSimpleName(), log, msg, null, LogUtil.LOGTYPE.DEBUG,"RES");
       }

    static  void debug(Class clazz,Object msg, Throwable e) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, clazz.getSimpleName(), log, msg, e, LogUtil.LOGTYPE.DEBUG,"RES");
       }

    static  void error(Class clazz,Object msg, Throwable e) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, clazz.getSimpleName(), log, msg, e, LogUtil.LOGTYPE.ERROR,"RES");
       }

    static  void fatal(Class clazz,Object msg, Throwable e) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, clazz.getSimpleName(), log, msg, e, LogUtil.LOGTYPE.FATAL,"RES");
       }

    static  void debugXML(Class clazz,Object msg, int level) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.logXML(ste, clazz.getSimpleName(), log, msg, LogUtil.LOGTYPE.DEBUG, level,"RES");
       }

}

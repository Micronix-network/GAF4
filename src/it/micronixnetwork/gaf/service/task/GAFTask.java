package it.micronixnetwork.gaf.service.task;

import it.micronixnetwork.gaf.util.LogUtil;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class GAFTask extends TimerTask {

    protected final Log log = LogFactory.getLog(getClass());

    protected void info(String msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.INFO, "TASK");
    }

    protected void debug(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.DEBUG, "TASK");
    }

    protected void debug(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.DEBUG, "TASK");
    }

    protected void error(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.ERROR, "TASK");
    }

    protected void fatal(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.FATAL, "TASK");
    }
    
    protected void warn(Object msg) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.FATAL, "TASK");
       }

    protected void debugXML(Object msg, int level) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.logXML(ste, this.getClass().getName(), log, msg, LogUtil.LOGTYPE.DEBUG, level, "TASK");
    }

}

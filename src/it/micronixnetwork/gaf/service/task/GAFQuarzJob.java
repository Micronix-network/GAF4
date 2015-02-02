package it.micronixnetwork.gaf.service.task;

import it.micronixnetwork.gaf.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class GAFQuarzJob extends QuartzJobBean {

    protected final Log log = LogFactory.getLog(getClass());

    protected void info(String msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.INFO, "QUARZ");
    }

    protected void debug(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.DEBUG, "QUARZ");
    }

    protected void debug(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.DEBUG, "QUARZ");
    }

    protected void error(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.ERROR, "QUARZ");
    }

    protected void fatal(Object msg, Throwable e) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, e, LogUtil.LOGTYPE.FATAL, "QUARZ");
    }
    
    protected void warn(Object msg) {
   	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
   	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.FATAL, "QUARZ");
       }

    protected void debugXML(Object msg, int level) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.logXML(ste, this.getClass().getName(), log, msg, LogUtil.LOGTYPE.DEBUG, level, "QUARZ");
    }

}

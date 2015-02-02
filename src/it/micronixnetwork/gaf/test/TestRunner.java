/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.test;

import it.micronixnetwork.gaf.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author kobo
 */
public abstract class TestRunner {

    private String description = "";

    private boolean detail = false;

    public void setDetail(boolean detail) {
	this.detail = detail;
    }

    public abstract boolean runTest();

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }

    private final Log log = LogFactory.getLog(TestRunner.class);

    protected void info(Object msg) {
	if (detail) {
	    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, null, LogUtil.LOGTYPE.INFO, "TESTRUN");
	}
    }

    protected void debug(Object msg) {
	if (detail) {
	    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, null, LogUtil.LOGTYPE.DEBUG, "TESTRUN");
	}
    }

    protected void debug(Object msg, Throwable e) {
	if (detail) {
	    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.DEBUG, "TESTRUN");
	}
    }

    protected void error(Object msg, Throwable e) {
	if (detail) {
	    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.ERROR, "TESTRUN");
	}
    }

    protected void fatal(Object msg, Throwable e) {
	if (detail) {
	    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, e, LogUtil.LOGTYPE.FATAL, "TESTRUN");
	}
    }

    protected void warn(Object msg) {
	if (detail) {
	    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    LogUtil.log(ste, this.getClass().getSimpleName(), log, msg, null, LogUtil.LOGTYPE.WARN, "TESTRUN");
	}
    }

    protected void debugXML(Object msg, int level) {
	if (detail) {
	    StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    LogUtil.logXML(ste, this.getClass().getSimpleName(), log, msg, LogUtil.LOGTYPE.DEBUG, level, "TESTRUN");
	}
    }

}

package it.micronixnetwork.gaf.util;

import org.apache.commons.logging.Log;

public class LogUtil {
    
    public static enum LOGTYPE {DEBUG,INFO,ERROR,FATAL,WARN};
    
    private static XMLBeanViewer xout = XMLBeanViewer.getInstance();
    
    protected void info(Log log,String msg) {
	if (log.isInfoEnabled()) {
		log.info(getClass().getSimpleName() + "-->" + msg);
	}

}

public static void log(StackTraceElement[] ste,String source,Log log,Object msg,Throwable e,LOGTYPE type,String cat) {
    	if(log!=null && source!=null){
    	
    	switch (type) {
	case DEBUG:
	    if(log.isDebugEnabled()){
		String log_message=lineMessage(ste,source,msg);
		if(e!=null){
		    log.debug("[D]["+cat+"] "+log_message,e);
		}else{
		    log.debug("[D]["+cat+"] "+log_message);
		}
	    }
	    break;
	case INFO:
	    if(log.isInfoEnabled()){
		String log_message=lineMessage(ste,source,msg);
		if(e!=null){
		    log.info("[I]["+cat+"] "+log_message,e);
		}else{
		    log.info("[I]["+cat+"] "+log_message);
		}
	    }
	    break;
	case ERROR:
	    if(log.isErrorEnabled()){
		String log_message=lineMessage(ste,source,msg);
		if(e!=null){
		    log.error("[E]["+cat+"] "+log_message,e);
		}else{
		    log.error("[E]["+cat+"] "+log_message);
		}
	    }
	    break; 
	case FATAL:
	    if(log.isFatalEnabled()){
		String log_message=lineMessage(ste,source,msg);
		if(e!=null){
		    log.fatal("[F]["+cat+"] "+log_message,e);
		}else{
		    log.fatal("[F]["+cat+"] "+log_message);
		}
	    }
	    break;  
	case WARN:
	    if(log.isWarnEnabled()){
		String log_message=lineMessage(ste,source,msg);
		if(e!=null){
		    log.warn("[W]["+cat+"] "+log_message,e);
		}else{
		    log.warn("[W]["+cat+"] "+log_message);
		}
	    }
	    break;      

	default:
	    break;
	}
    	}
	
}


public static void logXML(StackTraceElement[] ste,String source,Log log,Object bean,LOGTYPE type,int level,String cat) {
	if(source!=null && log!=null){
	
	switch (type) {
	case DEBUG:
	    if(log.isDebugEnabled()){
		String log_message=xmlMessage(ste,source,bean,level);
		log.debug("[D]["+cat+"] "+log_message);
	    }
	    break;
	case INFO:
	    if(log.isInfoEnabled()){
		String log_message=xmlMessage(ste,source,bean,level);
		log.info("[I]["+cat+"] "+log_message);
	    }
	    break;
	case ERROR:
	    if(log.isErrorEnabled()){
		String log_message=xmlMessage(ste,source,bean,level);
		log.error("[E]["+cat+"] "+log_message);
	    }
	    break; 
	case FATAL:
	    if(log.isFatalEnabled()){
		String log_message=xmlMessage(ste,source,bean,level);
		log.fatal("[F]["+cat+"] "+log_message);
	    }
	    break;   
	    
	case WARN:
	    if(log.isWarnEnabled()){
		String log_message=xmlMessage(ste,source,bean,level);
		log.warn("[W]["+cat+"] "+log_message);
	    }
	    break;    

	default:
	    break;
	}
	}
	
}

private static String lineMessage(StackTraceElement[] ste,String source,Object msg){
    //return source.getSimpleName()+"->("+ste[2].getFileName()+":"+ste[2].getLineNumber()+") -"+msg; 
    return source+" -> "+msg; 
}

private static String xmlMessage(StackTraceElement[] ste,String source,Object bean,int level){
    String result=source+"->("+ste[2].getFileName()+":"+ste[2].getLineNumber()+")\n" + xout.show(bean, level);
    result+="\n";
    result+="<---------------------- end xml ---------------------->";
    return result;
}

}

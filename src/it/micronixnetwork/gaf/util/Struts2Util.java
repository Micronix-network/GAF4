package it.micronixnetwork.gaf.util;

import javax.servlet.http.HttpServletRequest;

public class Struts2Util {
    
    public final static String ACTION_SUFFIX="action";
    
    
    public static String calcAction(HttpServletRequest req,String action,String namespace,String method){
	if(req==null) return null;
	String ctx=req.getContextPath();
	if(ctx==null) return null;
	String result=ctx;
	if(StringUtil.EmptyOrNull(action)) return null;
	
	if(!StringUtil.EmptyOrNull(namespace)){
	    result+="/"+namespace;
	}
	
	result+="/"+action;
	
	if(!StringUtil.EmptyOrNull(method)){
	    result+="!"+method;
	}
	
	result+="."+ACTION_SUFFIX;
	
	return result;
    }

}

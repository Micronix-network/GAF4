/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

import java.io.InputStream;

public class GetResource extends WebAppAction {

    private static final long serialVersionUID = 1L;
    
    private InputStream documentStream;
    private String res;
    private String contentType;

    public InputStream getDocumentStream() {
	return documentStream;
    }
    
    public String getContentType() {
	return contentType;
    }

    public void setDocumentStream(InputStream documentStream) {
	this.documentStream = documentStream;
    }

    public String getRes() {
	return res;
    }

    public void setRes(String res) {
	this.res = res;
    }

    @Override
    public String exe() throws ApplicationException {
	try {
	    
	    if(res.endsWith("png")){
		contentType="image/png";
	    }
	    
	    if(res.endsWith("css")){
		contentType="text/css";
	    }
	    
	    if(res.endsWith("js")){
		contentType="application/javascript";
	
	    }
	    
	    if(res.indexOf('.')!=res.lastIndexOf('.')){
		String last=res.substring(res.lastIndexOf('.'));
		String first=res.substring(0,res.lastIndexOf('.'));
		debug("First: /"+first);
		first=first.replaceAll("\\.", "/");
		res=first+last;
	    }
	    debug("Recupero risorsa: /"+res);
	    
	    debug("Classloader: "+this.getClass().getClassLoader().toString());
	    documentStream = this.getClass().getClassLoader().getResourceAsStream(res);
	    if(documentStream==null){
		debug("Risosra non individuata dal server classloader");
		documentStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + res);
	    }
	    if(documentStream==null){
		debug("Risosra non individuata dal web classloader");
	    }
	    
	    debug("Content Type: "+contentType);
	    
	    if(documentStream==null) return NONE;
	    
	} catch (Exception e) {
	    debug(e);
	    return NONE;
	}
	return SUCCESS;
    }

}

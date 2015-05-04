/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.service.FileSystemService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author a.riboldi
 */
public class RetriveProperties extends CardAction {

    private static final long serialVersionUID = 1L;

    private String cardType;
    private List<String> domainObjects;

    public void setCardType(String cardType) {
	this.cardType = cardType;
    }

    public String getCardType() {
	debug(cardType);
	return cardType;
    }

    public List<String> getDomainObjects() {
	return domainObjects;
    }
    
    public List<String> getHelpPages() {
	File baseDirectory=new File(getApplicationPath()+"WEB-INF/view/help_pages");
	//return new ArrayList<File>();
	List<String> result=new ArrayList<String>();
	try{
	List<File> files= fileSystemService.getFiles(baseDirectory,"ftl",FileSystemService.NAME,FileSystemService.ASC);
	for (File frag : files) {
	    String toAdd=frag.getName();
	    int index=toAdd.lastIndexOf(".");
	    toAdd=toAdd.substring(0,index);
	    result.add(toAdd);
	}
	}catch(Exception ex){
	    error("",ex);
	}
	return result;
    }

    @Override
    protected String exe() throws ApplicationException {
	cardType = getCardModel().getType();
	domainObjects=queryService.getAllMappedObjects();
	return SUCCESS;
    }
}

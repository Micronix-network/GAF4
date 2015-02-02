package it.micronixnetwork.gaf.struts2.gui.component;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public  class GeneralPropertiesTab extends GAFGuiComponent {
    
    	private String cardId;
    
    
	public GeneralPropertiesTab(ValueStack stack,HttpServletRequest req, HttpServletResponse res) {
		super(stack,req,res);	
		this.cardId=(String)findValue("cardId");
	}
	
	public String getCardId() {
	    return cardId;
	}
	
	public String getCardParam(String name){
	    return (String)findValue("getCardParam('"+name+"', false)");
	}
	
	
	@Override
	    public boolean end(Writer writer, String body) {
		try {
		    templateDir="gaf/tag";
		    templateName="gen_prop.ftl";
		    renderTemplate(writer,body);
		} catch (Exception ex) {
		    error("Render exception TAG: "+this.getClass().getSimpleName(), ex);
		} finally {
		}
		return super.end(writer, body);
	    }

}

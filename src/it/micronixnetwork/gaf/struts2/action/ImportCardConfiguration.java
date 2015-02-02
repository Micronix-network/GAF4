package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.gui.model.CardModel;
import it.micronixnetwork.gaf.util.MapUtil;
import it.micronixnetwork.gaf.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportCardConfiguration extends CardAction {

	private static final long serialVersionUID = 1L;
	
	private String toImport;
	private String jsCode;
	
	private Map<String,Object> props = new HashMap<String,Object>();

	public Map getProps() {
		return props;
	}

	public void setToImport(String toImport) {
	    this.toImport = toImport;
	}
	
	public String getToImport() {
	    return toImport;
	}
	
	public String getJsCode() {
		return jsCode;
	}
	

	@Override
	protected String exe() throws ApplicationException {
		
		CardModel cardModel=getCardModel();
		
		List<String> split=StringUtil.stringToList(toImport);
		
		if(split.size()==0) return SUCCESS;
		
		String domain=split.get(0);
		String cardId=split.get(1);
		String userId="-1";
		
		MapUtil.factorizeArrayMap(props);
		
		//Caricamento parametri della CARD
		if(cardId!=null && domain!=null){
		    	debug("Caricamento paramtri della CARD: '"+cardId+"' nel dominio: '"+domain+"'");
			props=cardConfService.getConf(userId, domain, cardId);
		}
		
		//Modifica forzata modello
		if(props!=null){
		    cardModel.setParams(props);
		}
		
		String width=(String)cardModel.getParam("card_width");
		String height=(String)cardModel.getParam("card_height");
		String title=(String)cardModel.getParam("title");
		String help=(String)cardModel.getParam("show_help");
		String prop=(String)cardModel.getParam("show_properties");
		if (StringUtil.EmptyOrNull(width)) {
		    width = "'100%'";
		}
		if (StringUtil.EmptyOrNull(height)) {
		    height = "'100%'";
		}
		if (StringUtil.EmptyOrNull(title)) {
		    title = "";
		}
		if (StringUtil.EmptyOrNull(help)) {
		    help = "false";
		}
		if (StringUtil.EmptyOrNull(prop)) {
		    prop = "false";
		}
		
		jsCode="<script type=\"text/javascript\">callEvent('"+getCardId()+"_properties_retrive');redraw_" + getCardId() + "_card('"+title+"',"+help+","+prop+")</script>";
		
		return super.exe();
	}

}

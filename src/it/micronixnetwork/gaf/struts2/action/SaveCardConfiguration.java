package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.gui.model.CardModel;
import it.micronixnetwork.gaf.util.MapUtil;
import it.micronixnetwork.gaf.util.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SaveCardConfiguration extends CardAction {

	private static final long serialVersionUID = 1L;
	
	private String domain;
	private String jsCode;
	
	private Map props = new HashMap();

	public Map getProps() {
		return props;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public String getJsCode() {
		return jsCode;
	}
	

	@Override
	protected String exe() throws ApplicationException {
		
		RoledUser user=getUser();
		
		String userid=null;
		
		if(user!=null){
			userid=user.getId();
		}else{
		    throw new ApplicationException("No active user");
		}
		
		if(isSuperUser()){
		    userid="-1";
		}
		
		CardModel cardModel=getCardModel();
		
		if(domain==null){
		    domain=cardModel.getDomain();
		}
		
		String cardType=cardModel.getType();
		
		MapUtil.factorizeArrayMap(props);
		
		//Salvataggio parametri
		if(getCardId()!=null && domain!=null && props.size()>0){
		    	debug("Save "+props.size()+" params: ");
			cardConfService.saveConf(userid, domain,getCardId(),cardType,props);
		}
		
		//Ricaricamento modello
		Map<String,Object> w_params=loadCardParams(domain, getCardId());
		
		
		
		if(w_params!=null && !w_params.isEmpty()){
			debug("Trovati: "+w_params.size()+" parametri");
			Iterator<String> keys=w_params.keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				cardModel.addParam(key, w_params.get(key));
			}
			//w.setParams(w_params);
		}else{
			debug("Nessun parametro trovato");
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
		
		
		jsCode="<script type=\"text/javascript\">"+ getCardId() + "_card.redraw('"+title+"',"+help+","+prop+")</script>";
		
		return super.exe();
	}

}

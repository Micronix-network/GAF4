/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.gui.model.CardModel;
import it.micronixnetwork.gaf.struts2.gui.model.CardModelsCache;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author kobo
 */
public class CardAction extends WebAppAction implements AjaxAction {

    private static final long serialVersionUID = 1L;

    private String cardId;

    public String getCardId() {
	return cardId;
    }

    public void setCardId(String cardId) {
	this.cardId = cardId;
    }

    /**
     * Recupera il parametro di una CARD dalla mappa
     * 
     * @param paramName
     *            il nome del parametro
     * @param evaluate
     *            se true cerca vi trovare il valore del parametro dal
     *            ValueStack
     * @return il valore del parametro
     */
    public String getCardParam(String paramName, boolean evaluate) {
	ActionContext context = ActionContext.getContext();
	if (context == null)
	    return null;
	CardModelsCache cardMap = getCardModels();
	if (cardMap != null) {
	    CardModel wmodel = cardMap.get(getCardId());
	    if (wmodel == null)
		return null;
	    Map<String, Object> params = wmodel.getParams();
	    if (params == null)
		return null;
	    Object value = params.get(paramName);
	    if (value != null) {
		if (evaluate) {
		    Object evaluated = context.getValueStack().findValue(value.toString());
		    if (evaluated != null) {
			return evaluated.toString();
		    }
		} else {
		    return value.toString();
		}
	    }
	}
	return null;
    }

    public String getCardParam(String paramName) {
	return getCardParam(paramName, false);
    }
    
    public String getCardParam(String paramName,String def) {
        String result=getCardParam(paramName, false);
        if(result==null){
            return def;
        }
	return result;
    }
    
   
    /**
     * Restituisce il modello di una CARD
     * 
     * @return
     */
    public CardModel getCardModel() {
	CardModelsCache cardMap = getCardModels();
	if (cardMap != null && getCardId() != null) {
	    return cardMap.get(getCardId());
	}
	return null;
    }


    @Override
    public void validate() {
	validInput();
    }

    protected void validInput() {
    }

    @Override
    protected String exe() throws ApplicationException {
	return SUCCESS;
    }

}

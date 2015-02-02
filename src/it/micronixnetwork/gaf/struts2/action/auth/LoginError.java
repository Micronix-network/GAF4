package it.micronixnetwork.gaf.struts2.action.auth;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.WebAppAction;


public class LoginError extends WebAppAction{

	private static final long serialVersionUID = -1969844471808655835L;
	
	@Override
	protected String exe() throws ApplicationException {
	      message=getText("login.form.accessError");
	      return SUCCESS;
	}

}

package it.micronixnetwork.gaf.struts2.action.exception;

import it.micronixnetwork.gaf.exception.ActionException;

public class UnauthorizedUser extends ActionException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UnauthorizedUser() {
    }

    public UnauthorizedUser(String message) {
	super(message);
    }

    public UnauthorizedUser(String message, Throwable cause) {
	super(message, cause);
    }

    public UnauthorizedUser(Throwable cause) {
	super(cause);
    }

}

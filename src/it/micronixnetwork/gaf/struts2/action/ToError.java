/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

/**
 *
 * @author a.riboldi
 */
public class ToError extends BaseAction {

    private static final long serialVersionUID = 1L;

protected String exe() throws ApplicationException {
    return SUCCESS;
  }
}


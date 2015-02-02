/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;

/**
 *
 * @author a.riboldi
 */
public class LogOut extends BaseAction {
    
  private static final long serialVersionUID = 7625469106100644498L;

@Override
  protected String exe() throws ApplicationException {
    ActionContext context = ActionContext.getContext();
        Map session = context.getSession();
        if (session != null) {
            session.clear();
        }
        if (session instanceof SessionMap) {
            try {
                ((SessionMap) session).invalidate();
            } catch (IllegalStateException e) {
                throw new ApplicationException("Log out exception");
            }
        }
        return SUCCESS;
  }
}

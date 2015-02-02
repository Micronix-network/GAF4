/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.action;


/**
 *
 * @author a.riboldi
 */
public class HelpAction extends CardAction {

    private static final long serialVersionUID = 1L;
    
    public String getHelpPage(){
	 return getCardParam("help_page", false);
    }

}

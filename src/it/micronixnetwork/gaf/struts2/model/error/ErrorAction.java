/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.struts2.model.error;

/**
 *
 * @author a.riboldi
 */
public class ErrorAction extends ErrorBean{

    public ErrorAction(String errorDett,String msg) {
        super(ERROR_LEVEL_LOGIC, errorDett, msg);
        setErrorType(ERROR_TYPE_APPLICATION);
    }
    
}

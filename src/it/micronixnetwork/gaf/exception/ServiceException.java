/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.exception;

/**
 *
 * @author a.riboldi
 */
public class ServiceException extends ApplicationException{

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
    }
    
    

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.service.exception;

import it.micronixnetwork.gaf.exception.ServiceException;

/**
 *
 * @author a.riboldi
 */
public class NotActiveUserException extends ServiceException{

    public NotActiveUserException(Throwable cause) {
        super(cause);
    }

    public NotActiveUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotActiveUserException(String message) {
        super(message);
    }

    public NotActiveUserException() {
    }
    
    

}

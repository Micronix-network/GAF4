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
public class UserExpiredException extends ServiceException{

    public UserExpiredException(Throwable cause) {
        super(cause);
    }

    public UserExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExpiredException(String message) {
        super(message);
    }

    public UserExpiredException() {
    }
    
    

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.exception.ServiceException;

/**
 *
 * @author kobo
 */
public class SearchEngineException extends ServiceException{

    public SearchEngineException() {
    }

    public SearchEngineException(String message) {
        super(message);
    }

    public SearchEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchEngineException(Throwable cause) {
        super(cause);
    }

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.exception;

/**
 *
 * @author a.riboldi
 */
public class ApplicationException extends Exception{

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException() {
    }
    
}

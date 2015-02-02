/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.exception;


/**
 *
 * @author a.riboldi
 */
public class ActionException extends ApplicationException{

    public ActionException() {
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }

}

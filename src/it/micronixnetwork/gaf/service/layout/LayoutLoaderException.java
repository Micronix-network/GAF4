/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.exception.ServiceException;

/**
 *
 * @author kobo
 */
public class LayoutLoaderException extends ServiceException{

    public LayoutLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public LayoutLoaderException(Throwable cause) {
        super(cause);
    }

    public LayoutLoaderException(String message) {
        super(message);
    }

    public LayoutLoaderException() {
    }

}

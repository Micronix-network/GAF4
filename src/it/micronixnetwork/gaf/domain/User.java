/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.domain;

import java.io.Serializable;

/**
 * 
 * @author kobo
 */
public interface User extends Serializable {

    public static String USER_TICKET = "it.micronixnetwork.gaf.domain.user_ticket";

    public String getUserName();

    public String getPassword();

    public Integer getEnabled();

    public String getAttribute(String name);

}

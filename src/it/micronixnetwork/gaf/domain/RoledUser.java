/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.domain;

/**
 * 
 * @author kobo
 */
public interface RoledUser extends User {

    public static String DEFAULT_ROLE = "ROLE_USER";
    
    public static String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";

    public static String SUPER_TICKET = "it.td.struts2.paf.RoleInterceptor.super_ticket";

    String getId();

    String[] getRoles();

    String getApplicaionRole();
}

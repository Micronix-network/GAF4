/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.model.error;

import java.io.Serializable;

/**
 *
 * @author a.riboldi
 */
public class ErrorBean implements Serializable {

    static final long serialVersionUID = -1234455382955298L;
    public static String ERROR_LEVEL_FATAL = "fatal";
    public static String ERROR_LEVEL_LOGIC = "logic";

    public static String ERROR_TYPE__TICKET = "no-ticket-error";
    public static String ERROR_TYPE_NO_ROLE = "no-role-error";
    public static String ERROR_TYPE_NO_ROLE_PERMISSION = "no-role-permission-error";
    public static String ERROR_TYPE_APPLICATION = "application-error";

    private Boolean async = Boolean.FALSE;
    private Boolean json = Boolean.FALSE;
    private String errorMsg;
    private String errorType;
    private String errorDett;
    private String errorLevel;

    public ErrorBean() {
    }

    public ErrorBean(String level, String errorDett, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorLevel = level;
        this.errorDett = errorDett;
    }

    public String getErrorDett() {
        return errorDett;
    }

    public void setErrorDett(String errorDett) {
        this.errorDett = errorDett;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    public void setJson(Boolean json) {
        this.json = json;
    }

    public Boolean getJson() {
        return json;
    }

}

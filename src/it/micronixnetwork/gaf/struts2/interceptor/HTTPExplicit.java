package it.micronixnetwork.gaf.struts2.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import java.io.BufferedReader;
import java.io.IOException;

public class HTTPExplicit extends GAFMethodFilterInterceptor {

    private static final long serialVersionUID = -7531436730913218294L;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();

        Enumeration enumer = request.getHeaderNames();
        debug("Request: header ---------------------------------");
        debug("METHOD: " + request.getMethod());
        while (enumer.hasMoreElements()) {
            Object object = (Object) enumer.nextElement();
            debug(object + ": " + request.getHeader(object.toString()));
        }
        debug("-------------------------------------------------");
        
        enumer = request.getAttributeNames();
        debug("Request: attribute --------------------------------");
        while (enumer.hasMoreElements()) {
            Object object = (Object) enumer.nextElement();
            debug(object + ": " + request.getAttribute(object.toString()));
        }

        debug("-------------------------------------------------");
        debug("Request: params ---------------------------------");
        enumer = request.getParameterNames();
        while (enumer.hasMoreElements()) {
            Object object = (Object) enumer.nextElement();
            String[] paramValues = request.getParameterValues(object.toString());
            if (paramValues != null && paramValues.length > 0) {
                for (String value : paramValues) {
                    debug(object + " = " + value);
                }
            }
        }
        debug("-------------------------------------------------");
        HttpSession session = request.getSession();

        if (session != null) {
            enumer = session.getAttributeNames();
            debug("Session: params ---------------------------------");
            while (enumer.hasMoreElements()) {
                Object object = (Object) enumer.nextElement();
                debug(object + " = " + session.getAttribute(object.toString()));
            }
            debug("-------------------------------------------------");
        }

        return invocation.invoke();

    }

}

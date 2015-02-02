/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.struts2.interceptor;

import java.util.Collections;
import java.util.Set;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptorUtil;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 *
 * @author kobo
 */
public abstract class GAFMethodFilterInterceptor extends GAFAbstractInterceptor{

    protected Set excludeMethods = Collections.EMPTY_SET;
    protected Set includeMethods = Collections.EMPTY_SET;

    public void setExcludeMethods(String excludeMethods) {
        this.excludeMethods = TextParseUtil.commaDelimitedStringToSet(excludeMethods);
    }

    public Set getExcludeMethodsSet() {
    	return excludeMethods;
    }

    public void setIncludeMethods(String includeMethods) {
        this.includeMethods = TextParseUtil.commaDelimitedStringToSet(includeMethods);
    }

    public Set getIncludeMethodsSet() {
    	return includeMethods;
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        if (applyInterceptor(invocation)) {
            return doIntercept(invocation);
        }
        return invocation.invoke();
    }

    protected boolean applyInterceptor(ActionInvocation invocation) {
        String method = invocation.getProxy().getMethod();
        // ValidationInterceptor
        boolean applyMethod = MethodFilterInterceptorUtil.applyMethod(excludeMethods, includeMethods, method);
        if (log.isDebugEnabled()) {
        	if (!applyMethod) {
        		debug("Skipping Interceptor... Method [" + method + "] found in exclude list.");
        	}
        }
        return applyMethod;
    }

    /**
     * Subclasses must override to implement the interceptor logic.
     *
     * @param invocation the action invocation
     * @return the result of invocation
     * @throws Exception
     */
    protected abstract String doIntercept(ActionInvocation invocation) throws Exception;

}

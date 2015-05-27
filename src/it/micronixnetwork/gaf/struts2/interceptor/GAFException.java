package it.micronixnetwork.gaf.struts2.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;

import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.struts2.action.AjaxAction;
import it.micronixnetwork.gaf.struts2.model.error.ErrorAction;
import it.micronixnetwork.gaf.struts2.model.error.ErrorBean;
import it.micronixnetwork.gaf.struts2.model.error.ErrorService;
import it.micronixnetwork.gaf.struts2.model.error.ErrorUndefine;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import it.micronixnetwork.gaf.struts2.action.JSONAction;

public class GAFException extends GAFMethodFilterInterceptor {
    
    private static final long serialVersionUID = -1467970061029822846L;

    private void createErrorBean(final ValueStack stack, Boolean ajax,Boolean json, Throwable ex) {
	ErrorBean errorBean;
	StringWriter writer = new StringWriter();
	PrintWriter printWriter = new PrintWriter(writer, true);
	ex.printStackTrace(printWriter);
	String erDett = writer.getBuffer().toString();
	String erMsg = ex.getMessage();
	if (ex.getCause() != null) {
	    erMsg += ": " + ex.getCause().getMessage();
	}
	if (ex instanceof ActionException) {
	    error(ex.getMessage(), ex);
	    errorBean = new ErrorAction(erDett, erMsg);
	} else {
	    if (ex instanceof ServiceException) {
		fatal(ex.getMessage(), ex);
		errorBean = new ErrorService(erDett, erMsg);
	    } else {
		if (ex instanceof NullPointerException) {
		    fatal(ex.getMessage(), ex);
		    errorBean = new ErrorService(erDett, "Null pointer exception");
		} else {
		    fatal(ex.getMessage(), ex);
		    errorBean = new ErrorUndefine(erDett, erMsg);
		}
	    }
	}
	errorBean.setAsync(ajax);
        errorBean.setJson(json);
	ErrorBeanContainer ebCont = new ErrorBeanContainer(errorBean);
	stack.push(ebCont);
    }
    
    private boolean checkAjax(Object action) {
	return (action instanceof AjaxAction);
    }
    
    private boolean checkJSON(Object action) {
	return (action instanceof JSONAction);
    }

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
	ValueStack stack = invocation.getInvocationContext().getValueStack();
	Object action=invocation.getAction();
	String result;
	try {
	    result = invocation.invoke();
	} catch (Throwable ex) {
	    result = produceErrorResult(stack,checkAjax(action),checkJSON(action),ex);
	}
	return result;
    }

    private String produceErrorResult(ValueStack stack,boolean ajax,boolean json,Throwable ex) {
	createErrorBean(stack,ajax,json,ex);
        if(json)
            return "error_json";
	if (ajax)
	    return "error_asinc";
	
	return "error";
    }

    protected class ErrorBeanContainer {

	private ErrorBean errorBean;

	public ErrorBeanContainer(ErrorBean errorBean) {
	    this.errorBean = errorBean;
	}

	public ErrorBean getErrorBean() {
	    return errorBean;
	}
    }

}

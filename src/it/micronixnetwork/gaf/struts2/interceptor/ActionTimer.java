package it.micronixnetwork.gaf.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;

public class ActionTimer extends GAFMethodFilterInterceptor {
    
    private static final long serialVersionUID = -7531436730913218294L;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
	final Object action=invocation.getAction();
	long start = 0;
	long endExecution = 0;
	String result;
	start = System.currentTimeMillis();
	result = invocation.invoke();
	endExecution = System.currentTimeMillis() - start;
	String endTimeMsg = " in: " + endExecution + " ms";
	info(action.getClass().getName()+" end execute with '" + result + "'" + endTimeMsg);
	return result;
    }

}

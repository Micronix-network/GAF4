package it.micronixnetwork.gaf.struts2.interceptor;

import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.struts2.action.CheckInAction;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class CheckCheckIn extends GAFMethodFilterInterceptor {

    private static final long serialVersionUID = 1L;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
	final Map session = invocation.getInvocationContext().getSession();
	final Object action = invocation.getAction();
	if (!(action instanceof CheckInAction)) {
	    if (session == null || session.get(RoledUser.USER_TICKET) == null)
		return "redirect_login";
	}
	return invocation.invoke();
    }

}

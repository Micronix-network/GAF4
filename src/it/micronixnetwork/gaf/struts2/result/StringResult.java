package it.micronixnetwork.gaf.struts2.result;

import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class StringResult implements Result {

    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_PARAM = "stringValue";
    String stringValue;

    public String getStringValue() {
	return stringValue;
    }

    public void setStringValue(String stringValue) {
	this.stringValue = stringValue;
    }

    @Override
    public void execute(ActionInvocation invocation) throws Exception {
	ServletActionContext.getResponse().setContentType("text/plain");
	PrintWriter responseStream = ServletActionContext.getResponse().getWriter();
	String out = "";
	if (stringValue != null) {
	    if (stringValue.startsWith("${") && stringValue.endsWith("}")) {
		String toFind = stringValue.substring(2, stringValue.length() - 1);
		Object tOut = invocation.getStack().findValue(toFind);
		if (tOut != null) {
		    out = tOut.toString();
		}
	    } else {
		out = stringValue;
	    }
	}
	responseStream.write(out);
	responseStream.flush();
    }

}
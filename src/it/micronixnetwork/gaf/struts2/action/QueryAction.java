package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class QueryAction extends CardAction implements AjaxAction {

    public final static String INTEGER_T = "i";

    public final static String REAL_T = "r";

    public final static String STRING_T = "s";

    public final static String BOOLEAN_T = "b";

    public final static String DATE_T = "d";

    protected static Logger log = Logger.getLogger(QueryAction.class);

    private static final long serialVersionUID = 1L;

    protected HashMap<String, Object> qParams;

    protected String p1;

    protected String p2;

    protected String p3;

    protected String p4;

    public void setP1(String p1) {
	this.p1 = p1;
    }

    public void setP2(String p2) {
	this.p2 = p2;
    }

    public void setP3(String p3) {
	this.p3 = p3;
    }

    public void setP4(String p4) {
	this.p4 = p4;
    }

    public String getP1() {
	return p1;
    }

    public String getP2() {
	return p2;
    }

    public String getP3() {
	return p3;
    }

    public String getP4() {
	return p4;
    }

    protected HashMap<String, Object> createParams() throws ServiceException {
	HashMap<String, Object> params = new HashMap<String, Object>();
	Object val;
	String p_type = null;

	String eval_s = getCardParam("p1_eval", false);
	boolean eval = eval_s == null ? false : eval_s.equals("false") ? false : true;

	if (p1 == null) {
	    p1 = getCardParam("p1", eval);
	}

	eval_s = getCardParam("p2_eval", false);
	eval = eval_s == null ? false : eval_s.equals("false") ? false : true;

	if (p2 == null) {
	    p2 = getCardParam("p2", eval);
	}

	eval_s = getCardParam("p3_eval", false);
	eval = eval_s == null ? false : eval_s.equals("false") ? false : true;

	if (p3 == null) {
	    p3 = getCardParam("p3", eval);
	}

	eval_s = getCardParam("p4_eval", false);
	eval = eval_s == null ? false : eval_s.equals("false") ? false : true;

	if (p4 == null) {
	    p4 = getCardParam("p4", eval);
	}

	if (p1 != null) {
	    p_type = getCardParam("p1_type", false);
	    val = convert(p1, p_type);
	    params.put("p1", val);
	}
	if (p2 != null) {
	    p_type = getCardParam("p2_type", false);
	    val = convert(p2, p_type);
	    params.put("p2", val);
	}
	if (p3 != null) {
	    p_type = getCardParam("p3_type", false);
	    val = convert(p3, p_type);
	    params.put("p3", val);
	}
	if (p4 != null) {
	    p_type = getCardParam("p4_type", false);
	    val = convert(p4, p_type);
	    params.put("p4", val);
	}

	if (getDevMode() & isSuperUser()) {
	    debug("Paramteri Query --------------------------------------");
	    debug("p1: " + p1);
	    debug("p2: " + p2);
	    debug("p3: " + p3);
	    debug("p4: " + p4);
	}

	return params;
    }

    private Object convert(String param, String type) {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	if (type == null || type.equals(STRING_T)) {
	    return param;
	}
	if (type.equals(INTEGER_T)) {
	    try {
		return new Integer(param);
	    } catch (Exception e) {
	    }
	}
	if (type.equals(REAL_T)) {
	    try {
		return new Double(param);
	    } catch (Exception e) {
	    }
	}
	if (type.equals(BOOLEAN_T)) {
	    try {
		return new Boolean(param);
	    } catch (Exception e) {
	    }
	}
	if (type.equals(DATE_T)) {
	    try {
		return sdf.parse(param);
	    } catch (ParseException e) {

	    }
	}

	return null;
    }

    protected abstract String exeQuery() throws ApplicationException;

    @Override
    protected final String exe() throws ApplicationException {
	qParams = createParams();
	return exeQuery();
    }

    protected List sqlSearch(String query) throws ApplicationException {
	return queryService.search(query, qParams, true);
    }

    protected Object sqlUniqueResult(String query) throws ApplicationException {
	return queryService.uniqueResult(query, qParams, true);
    }

    protected List hqlSearch(String query) throws ApplicationException {
	return queryService.search(query, qParams, false);
    }

    protected Object hqlUniqueResult(String query) throws ApplicationException {
	return queryService.uniqueResult(query, qParams, false);
    }

}

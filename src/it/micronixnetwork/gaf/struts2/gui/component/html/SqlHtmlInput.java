package it.micronixnetwork.gaf.struts2.gui.component.html;

import it.micronixnetwork.gaf.struts2.action.WebAppAction;
import it.micronixnetwork.gaf.struts2.gui.component.GAFGuiComponent;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public abstract class SqlHtmlInput extends Html {

	private boolean unique;

	private HashMap<String, Object> iParam;

	public SqlHtmlInput(ValueStack stack, HttpServletRequest req, HttpServletResponse res, String tag) {
		super(stack,req,res, tag);
		iParam = new HashMap<String, Object>();
	}

	protected Object executeQuery(boolean hql) {
		String query = null;

		if (!iParam.isEmpty()) {
			query = (String) iParam.get("query");
		}

		debug("Query passata come parametero: " + query);

		// Se esiste una query si procede a recuperare i valori dal db
		Object values = null;

		if (query != null) {
			try {
				// recupero la lista dei valori da visualizzare dal db
				ActionInvocation inv = ActionContext.getContext()
						.getActionInvocation();
				Object realAction = inv.getAction();
				if (realAction != null
						&& realAction instanceof WebAppAction) {
					if (hql) {
						values = ((WebAppAction) realAction)
								.executeHQLQuery(query, unique);
					} else {
						values = ((WebAppAction) realAction)
								.executeSQLQuery(query, unique);
					}
				}
			} catch (Exception ex) {
				error("Il componente "+tag+" ha generato un eccezione: ", ex);
			}
		}
		return values;
	}

	public HashMap<String, Object> getiParam() {
		return iParam;
	}

	public void setiParam(HashMap<String, Object> iParam) {
		this.iParam = iParam;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		if (unique != null) {
			this.unique = unique.equals("true");
		} else {
			this.unique = false;
		}
	}

	public void addInputParam(String name, Object value) {
		if (name != null && value != null)
			this.iParam.put(name, value);
	}

}

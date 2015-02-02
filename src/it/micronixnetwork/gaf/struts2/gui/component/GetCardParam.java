package it.micronixnetwork.gaf.struts2.gui.component;

import it.micronixnetwork.gaf.struts2.gui.model.CardModel;
import it.micronixnetwork.gaf.struts2.gui.model.CardModelsCache;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "get_card_param", tldTagClass = "it.micronixnetwork.gaf.struts2.gui.jsp.GetCardParamTag", description = "")
public final class GetCardParam extends GAFComponent {

    protected HttpServletResponse res;
    protected HttpServletRequest req;
    private String name;
    private boolean evaluate;

    public GetCardParam(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack);
	this.req = req;
	this.res = res;
    }

    public boolean isEvaluate() {
	return evaluate;
    }

    public void setEvaluate(String evaluate) {
	if (evaluate != null) {
	    this.evaluate = evaluate.equals("true");
	} else {
	    this.evaluate = false;
	}
    }

    public String getVar() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean end(Writer writer, String body) {
	try {
	    String cardId = (String) findValue("cardId");
	    if (cardId == null) {
		return false;
	    }
	    String toOut = "";
	    CardModelsCache cardMap = (CardModelsCache) stack.findValue("cardModels");
	    if (cardMap != null) {
		CardModel wmodel = cardMap.get(cardId);
		if (wmodel == null)
		    return false;
		Map<String, Object> params = wmodel.getParams();
		if (params == null)
		    return false;
		Object value = params.get(name);
		if (value != null) {
		    if (isEvaluate()) {
			Object evaluated = findValue(value.toString());
			if (evaluated != null) {
			    toOut = evaluated.toString();
			}
		    } else {
			toOut = value.toString();
		    }
		}

	    }

	    debug("CARD " + cardId + " param " + name + ": " + toOut);
	    writer.write(toOut);
	} catch (Exception ex) {
	    error("Errore nel rendering del tag get_wpapram", ex);
	    return false;
	}
	return super.end(writer, "");
    }

    @Override
    public boolean usesBody() {
	return false;

    }
}

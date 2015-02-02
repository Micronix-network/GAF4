package it.micronixnetwork.gaf.struts2.gui.component;

import it.micronixnetwork.gaf.service.SearchResult;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.util.ValueStack;

public final class Paginazione extends GAFGuiComponent {

    public Paginazione(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack,req,res);
    }

    protected static Log log = LogFactory.getLog(Paginazione.class);

    private String divId;
    private String result;
    private String eleLabel = "pagination.eleLabel";
    private String callFunction;

    public String getDivId() {
	return (this.divId);
    }

    public void setDivId(String divId) {
	this.divId = divId;
    }

    public String getEleLabel() {
	return eleLabel;
    }

    public void setEleLabel(String eleLabel) {
	this.eleLabel = eleLabel;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public String getCallFunction() {
	return callFunction;
    }

    public void setCallFunction(String callFunction) {
	this.callFunction = callFunction;
    }

    @Override
    public boolean start(Writer out) {

	try {
	    SearchResult sResult = (SearchResult) findValue(result);
	    long total = (sResult.getRecordNumber() == null) ? 0 : sResult.getRecordNumber();
	    long pageSize = (sResult.getRecordForPage() == null) ? 1000 : sResult.getRecordForPage();
	    long actualPage = (sResult.getPage() == null || sResult.getPage() == 0) ? 1 : sResult.getPage();

	    long pages = (long) Math.floor((double) total / (double) pageSize);
	    long mod = (long) Math.floor((double) total % (double) pageSize);
	    if (mod != 0) {
		pages += 1;
	    }

	    out.write("<div");
	    if (divId != null) {
		out.write(" id=\"" + getDivId() + "\"");
	    }
	    out.write(" class=\"pagination\"");
	    out.write(">");
	    out.write("<table><tr>");

	    // informazioni numero totale elementi paginati
	    out.write("<td class=\"pagination_msg\">");
	    out.write(getText(eleLabel, eleLabel, new String[] { String.valueOf(total) }));
	    out.write("</td>");

	    // Combo di paginazione
	    out.write("<td style=\"text-align:center;\">");
	    if (pages > 1) {
		out.write(getText("paginazione.page", "Pagina"));
		out.write("<select onchange=\"" + callFunction + "(this.options[this.selectedIndex].value);\">");
		for (int i = 1; i <= pages; i++) {
		    if (i == actualPage) {
			out.write("<option value=\"" + i + "\" selected=\"selected\">" + i + "</option>");
		    } else {
			out.write("<option value=\"" + i + "\">" + i + "</option>");
		    }
		}
		out.write("</select>");
		out.write(" " + getText("paginazione.page.di", "di") + " " + pages);
	    }
	    out.write("</td>");

	    // Comando di back
	    out.write("<td class=\"pagination_back\">");
	    if (pages > 1) {
		if (actualPage > 1) {
		    out.write("<a title=\"" + getText("paginazione.prec.tip", "prec") + "\" class=\"prec\" href=\"javascript:" + callFunction + "(" + (actualPage - 1) + ");\"><span>"
			    + getText("paginazione.prec", "prec") + "</span></a>");
		} else {
		    out.write("<a title=\"" + getText("paginazione.prec.tip", "prec") + "\" class=\"prec_disabled\" href=\"javascript:\"><span>" + getText("paginazione.prec", "prec")
			    + "</span></a>");
		}
	    }
	    out.write("</td>");

	    // Comando di forward
	    out.write("<td class=\"pagination_forward\">");
	    if (pages > 1) {
		if (actualPage < pages) {
		    out.write("<a title=\"" + getText("paginazione.succ.tip", "succ") + "\" class=\"succ\" href=\"javascript:" + callFunction + "(" + (actualPage + 1) + ");\"><span>"
			    + getText("paginazione.succ", "succ") + "</span></a>");
		} else {
		    out.write("<a title=\"" + getText("paginazione.succ.tip", "succ") + "\" class=\"succ_disabled\" href=\"javascript:\"><span>" + getText("paginazione.succ", "succ")
			    + "</span></a>");
		}
	    }
	    out.write("</td>");
	    out.write("</tr></table>");
	    out.write("</div>");
	} catch (IOException ioe) {
	    error("Errore nel rendering del tag di paginazione", ioe);
	}
	return true;

    }

    public boolean end(Writer writer) {
	return true;
    }

    @Override
    public boolean usesBody() {

	return false;

    }

}

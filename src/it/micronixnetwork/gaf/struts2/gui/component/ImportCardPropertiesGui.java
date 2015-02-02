package it.micronixnetwork.gaf.struts2.gui.component;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class ImportCardPropertiesGui extends GAFGuiComponent {

    private String cardId;

    public ImportCardPropertiesGui(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
    }

    public void setCardId(String cardId) {
	this.cardId = cardId;
    }
    
    public String getCardId() {
	return cardId;
    }

    @Override
    public boolean end(Writer writer, String body) {
	debug("Render gui di import delle proprietà");
	try {
	    templateDir = "gaf/tag";
	    templateName = "import_prop_gui.ftl";
	    renderTemplate(writer,body);

	} catch (Exception ioe) {
	    error("Errore nel rendering del tag per le properties", ioe);
	}
	return super.end(writer, body);
    }

    @Override
    public boolean usesBody() {
	return false;
    }

}

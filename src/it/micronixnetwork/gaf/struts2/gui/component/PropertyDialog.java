package it.micronixnetwork.gaf.struts2.gui.component;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsException;

import com.opensymphony.xwork2.util.ValueStack;

public class PropertyDialog extends GAFGuiComponent {

    private String width;
    private String height;
    private String cardId;
    private String namespace;

    public PropertyDialog(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
    }

    @Override
    public boolean end(Writer writer, String body) {
	if (cardId == null)
	    return false;
	try {
	    templateDir = "gaf/tag";
	    templateName = "property_dialog.ftl";
	    renderTemplate(writer,body);
	} catch (Exception ex) {
	    error("Render exception TAG: " + this.getClass().getSimpleName(), ex);
	} finally {
	}
	return super.end(writer, body);
    }

    public boolean usesBody() {
	return false;
    }

    public String getWidth() {
	return width;
    }

    public void setWidth(String width) {
	this.width = width;
    }

    public String getHeight() {
	return height;
    }

    public void setHeight(String height) {
	this.height = height;
    }

    public String getCardId() {
	return cardId;
    }

    public void setCardId(String cardId) {
	this.cardId = cardId;
    }
    
    public String getNamespace() {
	return namespace;
    }
    
    public void setNamespace(String namespace) {
	this.namespace = namespace;
    }

}

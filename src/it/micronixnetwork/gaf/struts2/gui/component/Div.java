/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.gui.component;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * @author kobo
 */
@StrutsTag(name = "div", tldTagClass = "it.micronixnetwork.gaf.struts2.gui.jsp.DivTag", description = "")
public class Div extends GAFAjaxActionComponent {

    public Div(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
    }

    @Override
    public boolean usesBody() {
	return true;
    }

    @Override
    public boolean end(Writer writer, String body) {
	try {
	    templateDir = "gaf/tag";
	    templateName = "div.ftl";
	    renderTemplate(writer,body);
	} catch (Exception ex) {
	    error("Render exception TAG: " + this.getClass().getSimpleName(), ex);
	} finally {
	}
//	try {
//	    writer.write(body);
//	    writer.write("</div>");
//	} catch (IOException e) {
//	    error("IOError while writing the body: " + e.getMessage(), e);
//	}
	popComponentStack();
	return false;
    }

}

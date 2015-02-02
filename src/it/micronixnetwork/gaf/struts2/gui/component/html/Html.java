package it.micronixnetwork.gaf.struts2.gui.component.html;

import it.micronixnetwork.gaf.struts2.gui.component.GAFGuiComponent;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsException;

import com.opensymphony.xwork2.util.ValueStack;

public abstract class Html extends GAFGuiComponent {

	protected final String tag;
	
	protected String iterable;

	public Html(ValueStack stack, HttpServletRequest req, HttpServletResponse res,String tag) {
		super(stack,req,res);
		this.tag = tag;
	}
	
	protected void writeBasicAttribute(Writer writer) throws IOException{
		if (getId() != null) {
			writer.write(" id=\"" + getId() + "\"");
		}
		if (getStyle() != null) {
			writer.write(" style=\"" + getStyle() + "\"");
		}
		if (getClassName() != null) {
			writer.write(" class=\"" + getClassName() + "\"");
		}
	}
	
	protected abstract void addAttribute(Writer writer) throws IOException;
	
	protected abstract void renderBody(Writer writer) throws IOException;
	
	
	
	public boolean end(Writer writer, String body) {
		try {
			super.end(writer, body, false);
			writer.write("<" + tag);
			writeBasicAttribute(writer);
			addAttribute(writer);
			writer.write(">");
			renderBody(writer);
			writer.write("</" + tag + ">");
		} catch (Exception e) {
			throw new StrutsException(e);
		} finally {
			popComponentStack();
		}
		return false;
	}

	public void setIterable(String iterable) {
		this.iterable = iterable;
	}
	
	
	

}

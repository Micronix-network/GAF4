package it.micronixnetwork.gaf.struts2.gui.component.html;

import it.micronixnetwork.gaf.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class Select extends SqlHtmlInput {

    private boolean multiple;
    private String size;
    private String name;
    private String value;

    public Select(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack,req,res,"select");
    }

    public void setMultiple(String multiple) {
	if (multiple != null)
	    this.multiple = multiple.equals("true");
	else
	    this.multiple = false;
    }

    public void setSize(String size) {
	this.size = size;
    }
    
    public void setName(String name) {
	this.name = name;
    }
    
    
    public void setValue(String value) {
	this.value = value;
    }

    @Override
    protected void addAttribute(Writer writer) throws IOException {
	if (name != null) {
	    writer.write(" name=\"" + name + "\"");
	} else {
	    if (getVar() != null) {
		writer.write(" name=\"" + getVar() + "\"");
	    }
	}
	if (multiple) {
	    writer.write(" multiple=\"multiple\"");
	}
	if (size != null) {
	    writer.write(" size=\"" + size + "\"");
	}
    }

    @Override
    protected void renderBody(Writer writer) throws IOException {
	Object values = null;
	
	String selected="";

	if(StringUtil.EmptyOrNull(value))
	    selected = stack.findString(getVar());
	else
	    selected=value;

	if (iterable != null) {
	    values = findValue(iterable);
	}

	// Se esiste una query si procede a recuperare i valori dal db
	if (values == null) {
	    values = executeQuery(false);
	}

	// Se esiste un risultato dalla query si crea l'html del componente
	// essendo un select mi aspetto
	// come risultato una lista di array (valore,descrizione)
	if (values != null) {
	    for (Object[] row : (List<Object[]>) values) {
		if (row[0] != null && row[1] != null) {
		    writer.write("<option value='");
		    writer.write(row[0].toString());
		    writer.write("'");
		    if (selected != null && selected.trim().equals(row[0].toString().trim())) {
			writer.write(" selected");
		    }
		    writer.write(">");
		    writer.write(row[1].toString());
		    writer.write("</option>");
		}
	    }
	}

    }

}

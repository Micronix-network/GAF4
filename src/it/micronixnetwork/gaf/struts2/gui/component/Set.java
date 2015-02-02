package it.micronixnetwork.gaf.struts2.gui.component;

import java.io.Writer;

import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "set", tldBodyContent = "JSP", tldTagClass = "it.micronixnetwork.gaf.struts2.gui.jsp.SetTag", description = "Assigns a value to a variable")
public class Set extends GAFComponent {

  private String value;
  private String var;
  private String def;

  public Set(ValueStack stack) {
    super(stack);
  }

  public void setVar(String var) {
    this.var = var;
  }

  public void setDef(String def) {
	this.def = def;
}

  public boolean end(Writer writer, String body) {
    Object o;
    if (value == null) {  
      o = body;
    } else {
      o = findValue(value);
    }
    body = "";
    
    if(def!=null){
    	if(o==null || "".equals(o.toString())){
    		o=def;
    	}
    }
    
    stack.getContext().put(var, o);
    stack.setValue("#attr['" + var + "']", o, false);
    return super.end(writer, body);
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean usesBody() {
    return true;
  }
}

package it.micronixnetwork.gaf.struts2.gui.component.html;

import it.micronixnetwork.gaf.struts2.gui.component.GAFComponent;

import java.io.Writer;

import org.apache.struts2.StrutsException;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class InputParam extends GAFComponent {

		  protected String name;
		  protected String value;

		  public InputParam(ValueStack stack) {
		    super(stack);
		  }

		  public boolean end(Writer writer, String body) {
		    Component component = findAncestor(Component.class);
		    if (component instanceof SqlHtmlInput) {
		      if (name == null) {
		        throw new StrutsException("No name found for following expression: " + this.name);
		      }
		      if(value!=null){
		    	  ((SqlHtmlInput)component).addInputParam(name, value);
		      }else{
		    	  if(body!=null) 
		    		 ((SqlHtmlInput)component).addInputParam(name,body);
		      }
		    }
		    return super.end(writer, "");
		  }

		  public boolean usesBody() {
		    return true;
		  }

		  public void setName(String name) {
		    this.name = name;
		  }

		  public void setValue(String value) {
		    this.value = value;
		  }

		  public String getName() {
		    return name;
		  }

		  public String getValue() {
		    return value;
		  }
		  

}

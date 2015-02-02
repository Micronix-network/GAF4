package it.micronixnetwork.gaf.struts2.gui.ftl;

import it.micronixnetwork.gaf.struts2.gui.component.ImportCardPropertiesGui;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @see Set
 */
public class ImportCardPropertiesGuiModel extends TagModel {

  private static final long serialVersionUID = 1L;
  
  
  public ImportCardPropertiesGuiModel(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
      super(stack, req, res);
  }

  protected Component getBean() {
      return new ImportCardPropertiesGui(stack, req, res);
  }
  
}

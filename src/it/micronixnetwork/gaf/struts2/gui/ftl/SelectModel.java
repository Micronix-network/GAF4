package it.micronixnetwork.gaf.struts2.gui.ftl;

import it.micronixnetwork.gaf.struts2.gui.component.html.Select;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.opensymphony.xwork2.util.ValueStack;

public class SelectModel extends TagModel{
    
    private static final long serialVersionUID = 1L;
    
    public SelectModel(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	      super(stack, req, res);
    }

    @Override
    protected Component getBean() {
	return new Select(stack, req, res);
    }

}

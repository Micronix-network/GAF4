package it.micronixnetwork.gaf.struts2.gui.ftl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.TagLibrary;

import com.opensymphony.xwork2.util.ValueStack;

public class GAFTagLibrary implements TagLibrary{
  
    public Object getFreemarkerModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new GAFModels(stack, req, res);
    }
     
    public List getVelocityDirectiveClasses() {
        return null;
    }
     
    

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.gui.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsException;
import org.apache.struts2.components.ComponentUrlProvider;
import org.apache.struts2.components.UrlProvider;
import org.apache.struts2.components.UrlRenderer;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.RequestMap;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.views.jsp.TagUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 *
 * @author kobo
 */
public class GAFActionComponent extends GAFGuiComponent {

  private String action;
  private String namespace;
  private String method;

  public GAFActionComponent(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
    super(stack,req,res);
  }

  protected void putInContext(String var, Object value) {
    if (var != null && var.length() > 0) {
      stack.getContext().put(var, value);
    }
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }
  
  public String calcAction(){
      return calcAction(action, namespace, method);
  }
}

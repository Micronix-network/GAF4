/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.gui.component;

import it.micronixnetwork.gaf.util.StringUtil;
import it.micronixnetwork.gaf.util.Struts2Util;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

/**
 * 
 * @author kobo
 */
public class GAFGuiComponent extends GAFComponent {

    private String id;
    private String var;
    private String style;
    private String className;
    protected HttpServletResponse res;
    protected HttpServletRequest req;

    // The template to use, overrides the default one.
    protected String templateName;

    // templateDir and theme attributes
    protected String templateDir;

    protected FreemarkerManager freemarkerManager;

    @Inject
    public void setFreemarkerManager(FreemarkerManager mgr) {
	this.freemarkerManager = mgr;
    }

    public GAFGuiComponent(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack);
	this.req = req;
	this.res = res;
    }
    
    

    public void renderTemplate(Writer writer,String body) throws Exception {

	if(templateName!=null && templateDir!=null){
	    
	if(body==null){
	    body="";
	}    
	
	// get the various items required from the stack
	ValueStack stack = getStack();
	Map context = stack.getContext();
	ServletContext servletContext = (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT);

	// prepare freemarker
	Configuration config = freemarkerManager.getConfiguration(servletContext);

	Template template = null;
	
	//config.setDirectoryForTemplateLoading(new File(templateDir));

	template = config.getTemplate("/template/"+templateDir+"/"+templateName);
	

	if (template != null) {
	    
	    debug("Rendering template " + templateName);
	    
	    ActionInvocation ai = ActionContext.getContext().getActionInvocation();

	    Object action = (ai == null) ? null : ai.getAction();
	    SimpleHash model = freemarkerManager.buildTemplateModel(stack, action, servletContext, req, res, config.getObjectWrapper());

	    model.put("tag", this);
	    
	    model.put("tag_body", body);
	  
	  
	    final Writer wrapped = writer;
	    writer = new Writer() {
		public void write(char cbuf[], int off, int len) throws IOException {
		    wrapped.write(cbuf, off, len);
		}

		public void flush() throws IOException {
		    // nothing!
		}

		public void close() throws IOException {
		    wrapped.close();
		}
	    };

	    try {
		stack.push(this);
		template.process(model, writer);
	    } finally {
		stack.pop();
	    }
	}
	}
    }

    public String getStyle() {
	return style;
    }

    public void setStyle(String style) {
	this.style = style;
    }

    public String getClassName() {
	return className;
    }

    public void setClassName(String className) {
	this.className = className;
    }

    public String getVar() {
	return var;
    }

    public void setVar(String var) {
	this.var = var;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }
    
    public HttpServletRequest getReq() {
	return req;
    }
    
    public HttpServletResponse getRes() {
	return res;
    }
    
    public String calcAction(String action,String namespace,String method){
	return Struts2Util.calcAction(req, action, namespace, method);
    }

}

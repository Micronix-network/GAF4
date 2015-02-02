/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.struts2.gui.component;

import it.micronixnetwork.gaf.util.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * @author kobo
 */
public abstract class GAFAjaxActionComponent extends GAFActionComponent {

    	protected List<String> listen;
	protected String formId;
	protected boolean startOnLoad;
	protected String loadImage;
	protected Integer reloadTime;
	protected String directUrl;

	public GAFAjaxActionComponent(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		super(stack, req, res);
	}

	public void setListen(String listen) {
		this.listen = StringUtil.stringToList(listen);
	}

	public List<String> getListen() {
		return listen;
	}

	public boolean isStartOnLoad() {
		return startOnLoad;
	}

	public void setReloadTime(String reloadTime) {
		if (reloadTime != null) {
			try {
				this.reloadTime = new Integer(reloadTime);
			} catch (NumberFormatException nfe) {
			}
		}
	}

	public void setStartOnLoad(String startOnLoad) {
		if (startOnLoad != null) {
			this.startOnLoad = startOnLoad.equals("true");
		} else {
			this.startOnLoad = false;
		}
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormId() {
		return formId;
	}

	public String getLoadImage() {
		return loadImage;
	}

	public void setLoadImage(String loadImage) {
		this.loadImage = loadImage;
	}
	
	public Integer getReloadTime() {
	    return reloadTime;
	}

	public String getDirectUrl() {
		return directUrl;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
	

}

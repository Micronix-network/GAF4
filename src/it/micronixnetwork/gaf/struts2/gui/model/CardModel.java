/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.struts2.gui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kobo
 */
public class CardModel implements Serializable{
  
  private static final long serialVersionUID = -1663148785245305019L;
 
  private Map<String,Object> params=new LinkedHashMap<String, Object>();
  private List<String> inRequest=new ArrayList<String>();

  private String zone;
  private String name;
  private String type;
  private boolean hide;
  private boolean published;
  private String style;
  private String action;
  private String namespace;
  private String method;
  private boolean stateless;
  private String domain;

  public CardModel() {
    this.hide=true;
    this.published=true;
  }

  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public void addParam(String key,Object value){
    params.put(key, value);
  }
  
  public Object removeParam(String key){
      return params.remove(key);
  }
  
  public Object getParam(String key){
	  if(params!=null && key!=null){
		return params.get(key);  
	  }
	  return null;
  }
  
  public void addIsRequest(String pName){
    inRequest.add(pName);
  }

  public Boolean isInRequest(String pName){
    return inRequest.contains(pName);
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isHide() {
    return hide;
  }
  
  public boolean getHide() {
      return hide;
    }

  public void setHide(boolean hide) {
    this.hide = hide;
  }

  public boolean isPublished() {
    return published;
  }
  
  public boolean getPublished() {
      return published;
    } 

  public void setPublished(boolean published) {
    this.published = published;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
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

public boolean isStateless() {
	return stateless;
}

public void setStateless(boolean stateless) {
	this.stateless = stateless;
}

public void setDomain(String domain) {
	this.domain = domain;
}

public String getDomain() {
	return domain;
}


 
}

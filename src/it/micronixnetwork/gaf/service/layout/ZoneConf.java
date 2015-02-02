/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.util.xml.XMLObject;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author kobo
 */
public class ZoneConf extends XMLObject {

  public static final String TAG_NAME = "zone";
  public static final String name_att = "name";
  public static final String width_att = "width";
  public static final String height_att = "height";
  public static final String closed_att = "closed";

  public ZoneConf() {
    super(TAG_NAME);
  }

  public ZoneConf(String name) {
    this();
    addAttribute(name_att, name);
  }

  public String getName() {
    return getAttribute(name_att);
  }

  public void setName(String name) {
    addAttribute(name_att, name);
  }
  
  public String getHeight() {
	  return getAttribute(height_att);
  }
  
  public void setHeight(String height) {
	    addAttribute(height_att, height);
  }
  
  public String getWidth() {
	  return getAttribute(width_att);
  }
  
  public void setWidth(String width) {
	    addAttribute(width_att, width);
  }
  
  public String getClosed() {
	  return getAttribute(closed_att);
  }
  
  public void setClosed(String closed) {
	    addAttribute(closed_att, closed);
  }
 
  
  public void addCard(CardStatus card) {
    this.addContent(card);
  }

  public CardStatus getCard(String name) {
    if (name == null) {
      return null;
    }
    List<CardStatus> wds = this.getChildren(CardStatus.class);
    for (CardStatus card : wds) {
      if (name.equals(card.getName())) {
        return card;
      }
    }
    return null;
  }

  public void removeCard(String name) {
    Iterator iter = this.getChildren().iterator();
    CardStatus wd = getCard(name);
    if (wd != null) {
      this.removeContent(wd);
    }
  }

  public List<CardStatus> getCards() {
    List<CardStatus> wds = this.getChildren(CardStatus.class);
    return wds;
  }
}

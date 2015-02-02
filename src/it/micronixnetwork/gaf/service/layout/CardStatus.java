/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.util.xml.XMLObject;

/**
 *
 * @author kobo
 */
public class CardStatus extends XMLObject{

  public static final String TAG_NAME="card";

    public static final String name_att="name";
    public static final String hidden_att="hidden";
    public static final String published_att="published";

    public CardStatus() {
        super(TAG_NAME);
    }

    public String getName() {
        return getAttribute(name_att);
    }

    public void setName(String name) {
        addAttribute(name_att, name);
    }
    
    public String getHidden() {
        String value=getAttribute(hidden_att);
        if(value==null) return "false";
        return value;
    }

    public void setHidden(String hidden) {
        addAttribute(hidden_att, hidden);
    }

    public String getPublished() {
        String value=getAttribute(published_att);
        if(value==null) return "false";
        return value;
    }

    public void setPublished(String published) {
        addAttribute(published_att, published);
    }



}

package it.micronixnetwork.gaf.domain;

import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "gaf_menues")
public class Menu implements Serializable {

    private static final long serialVersionUID = -7315815916053893446L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String label;

    public Boolean unlogged;

    public String allowed;

    public String disallowed;

    public String page;

    public String action;

    public String url;

    public Integer order;

    public Boolean active;

    public String icon;
    
    public String pin;

    @ManyToOne
    @JoinColumn(name = "parent")
    public Menu parentMenu;

    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Menu> subMenues;

    public Menu() {
    }

    public void addMenu(Menu menu) {
	menu.parentMenu = this;
	subMenues.add(menu);
    }

    public Menu getParentMenu() {
	return parentMenu;
    }

    public List<Menu> getSubMenues() {
	return subMenues;
    }

    public String getLabel() {
	return label;
    }

    public Boolean getUnlogged() {
	return unlogged;
    }

    public List<String> getAllowed() {
	return StringUtil.stringToList(allowed);
    }

    public List<String> getDisallowed() {
	return StringUtil.stringToList(disallowed);
    }

    public String getPage() {
	return page;
    }

    public String getAction() {
	return action;
    }

    public String getUrl() {
	return url;
    }

    public Integer getOrder() {
	return order;
    }

    public Boolean getActive() {
	return active;
    }

    public String getIcon() {
	return icon;
    }
    
    public void setPin(String pin) {
	this.pin = pin;
    }
    
    public String getPin() {
	return pin;
    }

    @Transient
    public boolean checkRoles(String[] roles) {

	boolean result = true;

	List<String> all = getAllowed();

	List<String> dis = getDisallowed();

	if (all.size() > 0) {
	    result = false;
	    for (String role : roles) {
		if (all.contains(role)) {
		    result = true;
		}
	    }
	}

	if (dis.size() > 0) {
	    for (String role : roles) {
		if (dis.contains(role)) {
		    result = false;
		}
	    }

	}

	return result;
    }

}
package it.micronixnetwork.gaf.domain;

import it.micronixnetwork.gaf.util.StringUtil;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "gaf_menues")
public class Menu implements Serializable {

    private static final long serialVersionUID = -7315815916053893446L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String label;

    private Boolean unlogged;

    private String allowed;

    private String disallowed;

    @Column(name="domain")
    private Integer idDomain;

    @OneToOne
    @JoinColumn(name = "domain", insertable = false, updatable = false)
    private _Domain domain;

    private String action;

    private String url;

    private Integer order;

    private Boolean active;

    private String icon;

    private String pin;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Menu parentMenu;

    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Menu> subMenues;

    public Menu() {
    }

    public void addMenu(Menu menu) {
        menu.parentMenu = this;
        subMenues.add(menu);
    }

    private List<String> getAllowed() {
        return StringUtil.stringToList(allowed);
    }

    private List<String> getDisallowed() {
        return StringUtil.stringToList(disallowed);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUnlogged(Boolean unlogged) {
        this.unlogged = unlogged;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    public void setDisallowed(String disallowed) {
        this.disallowed = disallowed;
    }

    public void setIdDomain(Integer idDomain) {
        this.idDomain = idDomain;
    }

    public void setDomain(_Domain domain) {
        this.domain = domain;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public void setSubMenues(List<Menu> subMenues) {
        this.subMenues = subMenues;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getUnlogged() {
        return unlogged;
    }

    public Integer getIdDomain() {
        return idDomain;
    }

    public _Domain getDomain() {
        return domain;
    }

    public String getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getActive() {
        return active;
    }

    public String getIcon() {
        return icon;
    }

    public String getPin() {
        return pin;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public List<Menu> getSubMenues() {
        return subMenues;
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

    @Entity
    @Table(name = "gaf_domains")
    public static class _Domain implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        private Integer id;

        @Column(name = "name")
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}

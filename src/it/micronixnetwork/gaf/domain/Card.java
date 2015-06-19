package it.micronixnetwork.gaf.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the deck_cards database table.
 * 
 */
@Entity
@Table(name = "gaf_cards")
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String cardname;

    private Integer domain;

    private String type;
    
    private String namespace;

    public Card() {
    }

    public Integer getId() {
	return this.id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getCardname() {
	return this.cardname;
    }

    public void setCardname(String cardname) {
	this.cardname = cardname;
    }

    public Integer getDomain() {
	return this.domain;
    }

    public void setDomain(Integer domain) {
	this.domain = domain;
    }

    public String getType() {
	return this.type;
    }

    public void setType(String type) {
	this.type = type;
    }
    
    public void setNamespace(String namespace) {
	this.namespace = namespace;
    }
    
    public String getNamespace() {
	return namespace;
    }

}
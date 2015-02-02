package it.micronixnetwork.gaf.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The persistent class for the cards_conf database table.
 * 
 */
@Entity
@Table(name = "gaf_cards_conf")
public class CardConf implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CardConfId id;

    @Column(name = "param_value")
    private String value;

    public CardConfId getId() {
	return id;
    }

    public void setId(CardConfId id) {
	this.id = id;
    }

    public CardConf() {
    }

    public String getValue() {
	return this.value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (!(obj instanceof CardConf))
	    return false;
	return getId().equals(obj);
    }

}
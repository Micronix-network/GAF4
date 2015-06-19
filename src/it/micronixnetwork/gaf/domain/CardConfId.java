package it.micronixnetwork.gaf.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class CardConfId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userid;

    private Integer domain;

    private String cardname;

    @Column(name = "param_key")
    private String key;

    public CardConfId() {
    }

    public CardConfId(String userid, Integer domain, String cardname, String key) {
	super();
	this.userid = userid;
	this.domain = domain;
	this.cardname = cardname;
	this.key = key;
    }

    public Integer getDomain() {
	return domain;
    }

    public void setDomain(Integer domain) {
	this.domain = domain;
    }

    public String getCardname() {
	return cardname;
    }

    public void setCardname(String cardname) {
	this.cardname = cardname;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public String getUserid() {
	return userid;
    }

    public void setUserid(String userid) {
	this.userid = userid;
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder(17, 37).append(domain).append(cardname).append(key).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (!(obj instanceof CardConfId))
	    return false;
	return domain.equals(((CardConfId) obj).getDomain()) && cardname.equals(((CardConfId) obj).getCardname()) && key.equals(((CardConfId) obj).getKey())
		&& userid.equals(((CardConfId) obj).getUserid());
    }
}

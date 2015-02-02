package it.micronixnetwork.gaf.domain;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the gaf_zones database table.
 * 
 */
@Entity
@Table(name="gaf_zones")
public class GafZone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Boolean closed;

	private Integer height;

	private String idDomain;

	private String name;

	private Integer width;

	//bi-directional many-to-one association to GafZoneCard
	@OneToMany(mappedBy="zone", cascade={CascadeType.ALL})
	private List<GafZoneCard> cards;

	public GafZone() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getClosed() {
		return this.closed;
	}

	public void setClosed(Boolean closed) {
		this.closed = closed;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getIdDomain() {
		return this.idDomain;
	}

	public void setIdDomain(String idDomain) {
		this.idDomain = idDomain;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public List<GafZoneCard> getCards() {
		return this.cards;
	}

	public void setCards(List<GafZoneCard> cards) {
		this.cards = cards;
	}

}
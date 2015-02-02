package it.micronixnetwork.gaf.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gaf_zone_cards database table.
 * 
 */
@Entity
@Table(name="gaf_zone_cards")
public class GafZoneCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String cardname;

	private Boolean hidden;

	private Boolean published;

	//bi-directional many-to-one association to GafZone
	@ManyToOne
	@JoinColumn(name="idZone")
	private GafZone zone;

	public GafZoneCard() {
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

	public Boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getPublished() {
		return this.published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public GafZone getZone() {
		return this.zone;
	}

	public void setZone(GafZone zone) {
		this.zone = zone;
	}

}
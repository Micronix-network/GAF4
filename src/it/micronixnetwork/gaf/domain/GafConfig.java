package it.micronixnetwork.gaf.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gaf_configs")
public class GafConfig {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer id;

	public Integer section;


	public String label;


	public String val;

	
	public String description;
	
	@Override
	public String toString() {
	    return label;
	}

}

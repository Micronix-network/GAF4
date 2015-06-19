package it.micronixnetwork.gaf.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.persistence.*;

import java.util.List;
import java.util.Map;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The persistent class for the gaf_zones database table.
 *
 */
@Entity
@Table(name = "gaf_zones")

public class GafZone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Boolean closed;

    private Integer height;

    @Column(name="domain")
    private Integer idDdomain;

    @OneToOne
    @JoinColumn(name = "domain", insertable = false, updatable = false)
    private Domain domain;

    private String name;

    private Integer width;
    
    @OneToMany(mappedBy = "zone", cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @MapKey(name="cardname")
    private Map<String, GafZoneCard> cards = new HashMap<>();

    public void removeCard(String cardName) {
        cards.remove(cardName);
    }

    public void removeContents() {
        if (cards == null) {
            return;
        }
        this.cards.clear();
    }

    public void addCard(GafZoneCard card) {
        if(card==null || card.getCardname()==null) return;
        cards.put(card.getCardname(), card);
    }

    public GafZone() {
    }

    public GafZone(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getIdDdomain() {
        return idDdomain;
    }

    public void setIdDdomain(Integer idDdomain) {
        this.idDdomain = idDdomain;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Map<String, GafZoneCard> getCards() {
        return cards;
    }

    public void setCards(Map<String, GafZoneCard> cards) {
        this.cards = cards;
    }
    
    

}

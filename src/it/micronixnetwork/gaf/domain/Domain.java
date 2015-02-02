package it.micronixnetwork.gaf.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gaf_domains")
public class Domain {
    
    @Id
    private String id;
    
    @Column(name = "card_grid")
    private String cardGrid;
    
    private Integer height;

    private Boolean active;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "domain", referencedColumnName = "id")
    private List<Card> cards;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardGrid() {
        return cardGrid;
    }

    public void setCardGrid(String cardGrid) {
        this.cardGrid = cardGrid;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    
    
    
    

}

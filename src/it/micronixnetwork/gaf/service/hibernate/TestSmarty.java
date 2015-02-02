/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.service.hibernate;

import java.util.Date;

import org.hibernate.Session;

/**
 *
 * @author kobo
 */
public class TestSmarty extends HibernateSmartySearchEngine {

    private String ciccio;
    private Integer uno;
    private Double due;
    private Date trullo;

    public String getCiccio() {
        return ciccio;
    }

    public Date getTrullo() {
        return trullo;
    }

    public Double getDue() {
        return due;
    }

    public Integer getUno() {
        return uno;
    }



    public TestSmarty(Session session) {
        super(session);
    }

    public TestSmarty(Session session, String ciccio, Date trullo) {
        super(session);
        this.ciccio = ciccio;
        this.trullo = trullo;
        this.uno=2;
        this.due=4d;
        
    }

    @Override
    protected void build() {
        setSelectString("Select from Prova");
        addWhereClausole("ciccio,trullo", "(ciccio like :0 or trullo > :1)");
        addWhereClausole("trullo", "trullo > :0");
        addWhereClausole("ciccio,trullo",new String[]{"trullo > :0","ciccio = :0"});
        addWhereClausole("due,uno",new String[]{"due > :0","uno = :1"});
    }
 
}

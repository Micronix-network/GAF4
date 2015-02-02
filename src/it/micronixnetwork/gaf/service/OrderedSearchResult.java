package it.micronixnetwork.gaf.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kobo
 */
public class OrderedSearchResult extends SearchResult {
    
    private final List<String> orderFields;

    /**
     * Ritorna il primo campo di ordinameto
     * @return
     */
    public String getField() {
        return orderFields.get(0);
    }


    /**
     * Ritorna la lista dei campi di ordinamento
     * @return
     */
    public List<String> getOrderFields(){
        return orderFields;
    }

    public OrderedSearchResult(Long recordNumber, Integer page, Integer recordForPage, List result, String field) {
        super(recordNumber, page, recordForPage, result);
        this.orderFields=new ArrayList<String>();
        orderFields.add(field);
    }

    public OrderedSearchResult(SearchResult result,String field) {
        super(result.getRecordNumber(), result.getPage(), result.getRecordForPage(), result.getResult());
        this.orderFields=new ArrayList<String>();
        orderFields.add(field);
    }

    public OrderedSearchResult(SearchResult result,List fields) {
        super(result.getRecordNumber(), result.getPage(), result.getRecordForPage(), result.getResult());
        this.orderFields=fields;
    }





}

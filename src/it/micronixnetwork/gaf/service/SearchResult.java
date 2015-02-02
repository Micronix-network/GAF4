package it.micronixnetwork.gaf.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a.riboldi
 */
public class SearchResult implements  Serializable {
    
    private final Long recordNumber;
    private final Integer page;
    private final Integer recordForPage;
    private final List result;

    public SearchResult(Long recordNumber, Integer page, Integer recordForPage, List result) {
        this.recordNumber = recordNumber;
        this.page = page;
        this.recordForPage = recordForPage;
        this.result = result;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getRecordForPage() {
        return recordForPage;
    }

    public Long getRecordNumber() {
        return recordNumber;
    }

    public List getResult() {
        return result;
    }
    
}

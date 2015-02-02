package it.micronixnetwork.gaf.service.hibernate;

import it.micronixnetwork.gaf.service.SearchEngine;
import it.micronixnetwork.gaf.service.SearchResult;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

public abstract class HibernateSearchEngine implements SearchEngine {

    protected static Log log = LogFactory.getLog(HibernateSearchEngine.class);
    protected Session session;

    public HibernateSearchEngine(Session session) {
        this.session = session;
    }

    protected abstract void setParameters(Query query);

    protected abstract Query createQuery();

    public SearchResult produceResult(Integer page, Integer size) {

        Long start = System.currentTimeMillis();
        Query query = createQuery();

        log.trace(">>>>>>>>>>>>>>>QUERY " + query);

        setParameters(query);
        int BATCH_SIZE = 100;

        List dtoList = new ArrayList();
        Long recordNum = new Long(0);
        int rowNum = 0;
        if (page != null && size != null) {
            ScrollableResults sr = query.setFetchSize(BATCH_SIZE).scroll();
            if (sr.first()) {
                sr.last();
                recordNum = new Long(sr.getRowNumber() + 1);
                page = checkPage(page, recordNum, size);
                rowNum = (page - 1) * size;
                log.trace("Scroll to: " + (page - 1) * size);
                sr.setRowNumber(rowNum);
                //Si controlla
                for (int i = 0; (i < size); i++) {
                    dtoList.add(sr.get(0));
                    if (sr.isLast()) {
                        break;
                    }
                    sr.next();
                }

            }
            sr.close();
        } else {
            dtoList = query.list();
            recordNum = new Long(dtoList.size());
        }
        SearchResult result = new SearchResult(recordNum, page, size, dtoList);
        String time = new Long((System.currentTimeMillis() - start) / 1000).toString();
        log.trace(">>>>>>>>>>>>>>>Time " + time);
        return result;
    }

    private int checkPage(int page, long recordNum, int size) {
        long pages = (long) Math.floor((double) recordNum / (double) size);
        long mod = (long) Math.floor((double) recordNum % (double) size);
        if (mod != 0) {
            pages += 1;
        }
        if (page > pages) {
            page = (int) pages;
        }
        return page;
    }

}

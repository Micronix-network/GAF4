package it.micronixnetwork.gaf.service;


/**
 *
 * @author a.riboldi
 */
public interface SearchEngine{
    SearchResult produceResult(Integer page,Integer size) throws SearchEngineException;
}

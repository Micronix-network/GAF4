package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

public interface QueryService {
		
	
    	/**
    	 * Esegue una query sql/hql sul database
    	 * @param query la query da eseguire
    	 * @param values i parametri da settare passati come una mappa [nome_parametro->valore]
    	 * @param sqlFlag  il flag che identifica se la query e sql:true o hql:false
    	 * @return la lista dei valori
    	 * @throws ServiceException
    	 */
    	List search(String query,HashMap<String, Object> values,boolean sqlFlag) throws ServiceException;
	
	
    	/**
    	 * Esegue una query sql/hql sul database retituendo un unico valore 
    	 * @param query la query da eseguire
    	 * @param values i parametri da settare passati come una mappa [nome_parametro->valore]
    	 * @param sqlFlag  il flag che identifica se la query e sql:true o hql:false
    	 * @return il risultato singolo dell'interrogazione
    	 * @throws ServiceException
    	 */
    	Object uniqueResult(String query,HashMap<String, Object> values,boolean sqlFlag) throws ServiceException;
	
    	/**
    	 * Restituisce la lista degli oggetti mappati via hibernate
    	 * @return
    	 * @throws ServiceException
    	 */
    	List<String> getAllMappedObjects() throws ServiceException;
}

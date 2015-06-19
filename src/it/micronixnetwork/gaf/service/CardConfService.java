package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import java.util.Map;

public interface CardConfService {
	
	Map<String,Object> getConf(String userid,String domain,String cardName) throws ServiceException;
	
	String getProperty(String userid,String domain,String cardName,String name) throws ServiceException;
	
	void saveProperty(String userid,String domain,String cardName,String name,String type,String value) throws ServiceException;
	
	void saveConf(String userid, String domain,String cardName,String type,Map props) throws ServiceException;

}

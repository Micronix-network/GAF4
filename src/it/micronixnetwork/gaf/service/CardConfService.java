package it.micronixnetwork.gaf.service;

import java.util.Map;

public interface CardConfService {
	
	Map<String,Object> getConf(String userid,String domain,String cardName);
	
	String getProperty(String userid,String domain,String cardName,String name);
	
	void saveProperty(String userid,String domain,String cardName,String name,String type,String value);
	
	void saveConf(String userid, String domain,String cardName,String type,Map props);

}

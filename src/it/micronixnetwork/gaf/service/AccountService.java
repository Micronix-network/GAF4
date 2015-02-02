package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ServiceException;

import java.util.Collection;

public interface AccountService {
	
	RoledUser checkIn(String username,String password) throws ServiceException;
	
	Collection<String> getAllRole() throws ServiceException; 

}

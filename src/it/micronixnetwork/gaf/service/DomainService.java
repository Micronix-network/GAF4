package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.Domain;
import it.micronixnetwork.gaf.exception.ServiceException;

import java.util.Map;

public interface DomainService {
    
    Map<String,Domain> retriveDomains() throws ServiceException;
    
    Domain retriveDomain(String domain)  throws ServiceException;
    
    void refreshDomains()  throws ServiceException;
    
    void clearDomains()  throws ServiceException;
    
}

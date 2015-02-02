package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.GafConfig;
import it.micronixnetwork.gaf.exception.ServiceException;

public interface ConfigService {

    GafConfig getConfig(String key) throws ServiceException;

    GafConfig getConfig(int id) throws ServiceException;

    String getString(String key, String defValue) throws ServiceException;

    boolean getBoolean(String key, boolean defValue) throws ServiceException;

    int getInt(String key, int defValue) throws ServiceException;

    double getDouble(String key, double defValue) throws ServiceException;

}

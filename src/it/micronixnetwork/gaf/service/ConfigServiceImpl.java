package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.GafConfig;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class ConfigServiceImpl extends HibernateSupport implements ConfigService {

    @Override
    public GafConfig getConfig(String key) throws ServiceException {

	if (key == null)
	    throw new ServiceException("key not defined");

	String hql = "From GafConfig g where g.label=:key";

	Query query = createQuery(hql);

	query.setString("key", key);

	return (GafConfig) query.uniqueResult();

    }

    @Override
    public String getString(String key, String defValue) throws ServiceException {

	GafConfig gc = getConfig(key);
	if (gc == null || gc.val == null || gc.val.trim().length() == 0) {
	    return defValue;
	}

	return gc.val.trim();
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) throws ServiceException {

	String v = getString(key, "" + defValue);
	return ("0 false falso no".indexOf(v.toLowerCase()) == -1);

    }

    @Override
    public int getInt(String key, int defValue) throws ServiceException {

	return Integer.parseInt(getString(key, "" + defValue));
    }

    @Override
    public double getDouble(String key, double defValue) throws ServiceException {
	return Double.parseDouble(getString(key, "" + defValue));
    }

    @Override
    public GafConfig getConfig(int id) throws ServiceException {

	String hql = "From GafConfig g where g.id=:id";

	Query query = createQuery(hql);

	query.setInteger("id", id);

	return (GafConfig) query.uniqueResult();
    }

}
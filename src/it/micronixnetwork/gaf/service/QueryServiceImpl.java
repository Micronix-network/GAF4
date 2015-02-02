package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class QueryServiceImpl extends HibernateSupport implements QueryService {

    @Override
    public List search(String query, HashMap<String, Object> values, boolean sqlFlag) throws ServiceException {
	Query q = null;
	if (sqlFlag) {
	    q = createQueryBySQL(query);
	} else {
	    q = createQuery(query);
	}
	if (values != null) {
	    for (String key : values.keySet()) {
		setParameter(q, values.get(key), key);
	    }
	}
	return q.list();
    }

    @Override
    public Object uniqueResult(String query, HashMap<String, Object> values, boolean sqlFlag) throws ServiceException {
	Query q = null;
	if (sqlFlag) {
	    q = createQueryBySQL(query);
	} else {
	    q = createQuery(query);
	}
	if (values != null) {
	    for (String key : values.keySet()) {
		setParameter(q, values.get(key), key);
	    }
	}
	return q.uniqueResult();
    }

    private void setParameter(Query query, Object value, String key) {
	String[] params_name = query.getNamedParameters();

	if (params_name == null) {
	    return;
	}

	if (!Arrays.asList(params_name).contains(key)) {
	    return;
	}

	if (value == null) {
	    return;
	}

	if (value instanceof String) {
	    if (((String) value).trim().equals("")) {
		return;
	    }
	}
	if (value instanceof Character) {
	    if (value.toString().trim().equals("")) {
		return;
	    }
	}
	if (value instanceof String) {
	    query.setString(key, (String) value);
	}
	if (value instanceof Date) {
	    query.setDate(key, (Date) value);
	}
	if (value instanceof Integer) {
	    query.setInteger(key, (Integer) value);
	}
	if (value instanceof Character) {
	    query.setCharacter(key, (Character) value);
	}
	if (value instanceof Double) {
	    query.setDouble(key, (Double) value);
	}
	if (value instanceof Float) {
	    query.setFloat(key, (Float) value);
	}
	if (value instanceof Long) {
	    query.setLong(key, (Long) value);
	}
	if (value instanceof Boolean) {
	    query.setBoolean(key, (Boolean) value);
	}
	if (value instanceof Short) {
	    query.setShort(key, (Short) value);
	}
	if (value instanceof Collection) {
	    query.setParameterList(key, (Collection) value);
	}
	if (value instanceof BigDecimal) {
	    query.setBigDecimal(key, (BigDecimal) value);
	}
	if (value instanceof Number) {
	    query.setParameter(key, value);
	}
    }

    public List<String> getAllMappedObjects() throws ServiceException {
	try {
	    return getHibernateMappedObjects();
	} catch (Exception ex) {
	    throw new ServiceException(ex);
	}
    }

}

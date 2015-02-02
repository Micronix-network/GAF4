package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.Domain;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.gaf.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class DomainServiceImpl extends HibernateSupport implements DomainService {

    @Override
    public Map<String, Domain> retriveDomains() throws ServiceException {
	Map<String,Domain> result=new HashMap<String, Domain>();
	String hql="From Domain";
	Query query = createQuery(hql);
	query.setCacheable(true);
	List<Domain> list = (List<Domain>) query.list();
	for (Domain domain : list) {
	    result.put(domain.getId(), domain);
	}
	return result;
    }

    @Override
    public Domain retriveDomain(String domain) throws ServiceException {
	if(StringUtil.EmptyOrNull(domain)) return null;
	Domain result=null;
	result=(Domain)getCurrentSession().get(Domain.class, domain);
	return result;
    }

}

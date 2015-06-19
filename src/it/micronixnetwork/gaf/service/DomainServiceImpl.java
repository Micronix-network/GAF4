package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.Domain;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.gaf.service.layout.LayoutConfig;
import it.micronixnetwork.gaf.util.StringUtil;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class DomainServiceImpl extends HibernateSupport implements DomainService {

    private Map<String, Domain> domains = new Hashtable<>();

    @Override
    public Map<String, Domain> retriveDomains() throws ServiceException {
        if (domains.size() == 0) {
            String hql = "From Domain";
            Query query = createQuery(hql);
            query.setCacheable(true);
            List<Domain> list = (List<Domain>) query.list();
            for (Domain domain : list) {
                domains.put(domain.getName(), domain);
            }
        }
        return domains;
    }

    @Override
    public Domain retriveDomain(String domainName) throws ServiceException {
        if (StringUtil.EmptyOrNull(domainName)) {
            return null;
        }
        Domain result = domains.get(domainName);
        if (result != null) {
            return result;
        }
        String hql = "From Domain d where d.name=:name";
        Query q = createQuery(hql);
        q.setString("name", domainName);
        result = (Domain) q.uniqueResult();
        if (result != null) {
            domains.put(result.getName(), result);
        }
        return result;
    }

    @Override
    public void refreshDomains() throws ServiceException {
        domains.clear();
        String hql = "From Domain";
        Query query = createQuery(hql);
        query.setCacheable(true);
        List<Domain> list = (List<Domain>) query.list();
        for (Domain domain : list) {
            domains.put(domain.getName(), domain);
        }

    }

    @Override
    public void clearDomains() throws ServiceException {
        domains.clear();
    }

}

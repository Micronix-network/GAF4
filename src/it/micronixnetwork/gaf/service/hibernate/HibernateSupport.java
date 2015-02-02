package it.micronixnetwork.gaf.service.hibernate;

import it.micronixnetwork.gaf.service.GAFService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;

/**
 * 
 * @author Andrea Riboldi
 */
public class HibernateSupport extends GAFService{

    private SessionFactory sessionFactory;

    public HibernateSupport() {
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
	return sessionFactory.getCurrentSession();
    }

    protected Query getNamedQuery(String queryName) {
	return getCurrentSession().getNamedQuery(queryName);
    }

    protected Query createQuery(String hql) {
	return getCurrentSession().createQuery(hql);
    }

    /**
     * Crea un oggetto SQLQuery partendo da uno script SQL
     * @param sql
     * @return
     */
    protected SQLQuery createQueryBySQL(String sql) {
	return getCurrentSession().createSQLQuery(sql);
    }
    
    
    /**
     * Ritorna la lista degli oggetti mappati in un factory
     * @return la lista dei nomi delle entità
     */
    protected List<String> getHibernateMappedObjects(){
   	List<String> result=new ArrayList<String>(); 
   	Map metaMap = sessionFactory.getAllClassMetadata();  
           for (String key : (Set<String>) metaMap.keySet()) {  
               AbstractEntityPersister classMetadata = (SingleTableEntityPersister) metaMap  
                       .get(key);  
               result.add(classMetadata.getEntityName());  
           }  
           return result;
       } 

}

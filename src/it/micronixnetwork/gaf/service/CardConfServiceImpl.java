package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.CardConf;
import it.micronixnetwork.gaf.domain.CardConfId;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.gaf.util.StringUtil;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class CardConfServiceImpl extends HibernateSupport implements CardConfService {

    @Override
    public Map<String, Object> getConf(String userid, String domain, String cardName) {
	Map<String, Object> result = new HashMap<String, Object>();

	String hql = "From CardConf wc where cardname=:cardname and (userid=-1";

	if (userid != null) {
	    hql += " or userid=:userid";
	}

	hql += ")";

	if (!StringUtil.EmptyOrNull(domain)) {
	    hql += " and domain=:pageName";
	}

	Query query = createQuery(hql);
	query.setCacheable(true);

	debug("Ricerca paramteri per la CARD: " + cardName);

	query.setString("cardname", cardName);

	if (!StringUtil.EmptyOrNull(domain)) {
	    query.setString("pageName", domain);
	}

	if (userid != null) {
	    query.setString("userid", userid);
	}

	List<CardConf> list = (List<CardConf>) query.list();

	if (list.size() > 0) {
	    for (CardConf wc : list) {
		Object precValue = result.get(wc.getId().getKey());
		if (precValue == null) {
		    result.put(wc.getId().getKey(), wc.getValue());
		} else {
		    if (wc.getId().getUserid().equals(userid)) {
			result.put(wc.getId().getKey(), wc.getValue());
		    }
		}
	    }
	    return result;
	}
	return null;
    }
    
    

    @Override
    public void saveConf(String userid, String domain, String cardId,String type, Map props) {
	if (props == null) {
	    return;
	}

	Iterator keys = props.keySet().iterator();
	CardConf conf;

	while (keys.hasNext()) {
	    String key = (String) keys.next();

	    CardConfId id = new CardConfId(userid, domain, cardId, key);

	    conf = (CardConf) getCurrentSession().get(CardConf.class, id);

	    if (conf == null) {
		conf = new CardConf();
		conf.setId(id);
	    }
	    
	    Object toSave = props.get(key);
	    String toRealSave = convert(toSave);
	    conf.setValue(toRealSave);
	    getCurrentSession().saveOrUpdate(conf);
	}
    }

    private String convert(Object toSave) {
	if (toSave == null)
	    return null;
	if (toSave instanceof Collection) {
	    String value = "";
	    for (Object temp : (Collection) toSave) {
		if(temp.toString().length()>0){
		    value += temp.toString() + ",";
		}
	    }
	    if (value.trim().length() > 0) {
		return value.substring(0, value.length() - 1);
	    }else{
		return value;
	    }
	}
	if (toSave instanceof Object[]) {
	    int length = Array.getLength(toSave);
	    String value = "";
	    for (int i = 0; i < length; i++) {
		String object = Array.get(toSave, i).toString();
		if(object.trim().length()>0){
        		value += object;
        		if (i < length - 1) {
        		    value += ",";
        		}
		}
	    }
	    return value;
	    
	}
	return toSave.toString();
    }



    @Override
    public String getProperty(String userid, String domain, String cardname, String name) {
	CardConfId id=new CardConfId();
	id.setDomain(domain);
	id.setKey(name);
	id.setCardname(cardname);
	id.setUserid(userid);
	CardConf prop=(CardConf)getCurrentSession().get(CardConf.class, id);
	if(prop!=null){
	    return prop.getValue();
	}
	return null;
    }



    @Override
    public void saveProperty(String userid, String domain, String cardname, String name,String type, String value) {
	CardConfId id=new CardConfId();
	id.setDomain(domain);
	id.setKey(name);
	id.setCardname(cardname);
	id.setUserid(userid);
	CardConf prop=(CardConf)getCurrentSession().get(CardConf.class, id);
	if(prop!=null){
	    prop.setValue(value);
	    getCurrentSession().update(prop);
	}else{
	    prop=new CardConf();
	    prop.setId(id);
	    prop.setValue(value);
	    getCurrentSession().save(prop);
	}
    }
}

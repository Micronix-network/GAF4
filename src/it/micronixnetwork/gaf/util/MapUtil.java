package it.micronixnetwork.gaf.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

public class MapUtil {
	
	/**
	 * Fattorizza una Mappa di Array producendo coppie key:value nel caso si incontrino
	 * coppie key:[values] dove values è un array di un solo elemento.
	 * @return
	 */
	public static void factorizeArrayMap(Map param){
		
		Map res=new HashedMap();
		
		Iterator keys=param.keySet().iterator();
		
		while (keys.hasNext()) {
			Object key = keys.next();
			Object value=param.get(key);
			if(value !=null){
				if(value.getClass().isArray()){
					if(((Object[])value).length==1){
						param.put(key, ((Object[])value)[0]);
					}
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		Map test=new HashedMap();
		
		test.put("one", "prova1");
		test.put("two", "prova2");
		test.put("three", new String[]{"1","2","3"});
		test.put("four", new String[]{"1"});
		
		XMLBeanViewer out=XMLBeanViewer.getInstance();
		
		System.out.println(out.show(test, 4));
		
		factorizeArrayMap(test);
		
		System.out.println(out.show(test, 4));
		
	}

}

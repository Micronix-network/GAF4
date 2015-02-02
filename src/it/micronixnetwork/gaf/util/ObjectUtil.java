package it.micronixnetwork.gaf.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObjectUtil {
    
    private static final Log log = LogFactory.getLog(ObjectUtil.class);
    
    
    /**
     * Recupera il valore di un field da un oggetto passato per parametro prima cerca di ottenere il valore nel caso di field 
     * ubblico e successivamente cerca di ottenerlo via metodo get.
     * @param field il field di cui recuperare il valore
     * @param obj l'instanza da cui recuperare il valore
     * @return
     */
    public static Object getFieldValue(Field field,Object obj) {
   	Object value=null;
           try {
               value=field.get(obj);
           } catch (Exception e) {
             //debug(e.getMessage());
           } 
           
           if(value!=null) return value;
           
           try{
               char first = Character.toUpperCase(field.getName().charAt(0));
               String methodName = "get" + first + field.getName().substring(1);
               Class paramTypes[] = new Class[0];
               Method method = obj.getClass().getMethod(methodName, paramTypes);
               value= method.invoke(obj, new Object[0]);
           } catch (Exception e) {
               //debug(e.getMessage());
           }
           
           return value;
       }
    
    public static Object getFieldValue(String fieldName,Object obj) {
	if(obj==null) return null;
	try {
	    Field field=obj.getClass().getField(fieldName);
	    return getFieldValue(field, obj);
	} catch (Exception e) {
	    
	} 
	
	return null;
	
    }

}

package it.micronixnetwork.gaf.util;

import it.micronixnetwork.gaf.util.xml.XMLObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Questa classe permette la rappresentazione XML di un bean generico
 * @author Andrea Riboldi
 */
public class XMLBeanViewer {

  private static final Set<Class> SCALAR = new HashSet<Class>(Arrays.asList(
          String.class, Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Float.class, Long.class, Double.class,
          BigInteger.class, BigDecimal.class, AtomicBoolean.class, AtomicInteger.class, AtomicLong.class, Date.class, Calendar.class, GregorianCalendar.class,
          Class.class, UUID.class, Number.class, Object.class, Timestamp.class));
  private static XMLBeanViewer instance = new XMLBeanViewer();

  private XMLBeanViewer() {
  }

  public static XMLBeanViewer getInstance() {
    return instance;
  }

  /**
   * Esporta il bean in un XMLObject
   * @param bean l'oggetto da visualizzare in formato XML
   * @param level livello di visualizzazione dell'albero xml, evita il loop dovuto ai riferimenti circolari
   * @return L'XMLObject che rappresenta il bean, il toString lo visualizza testualmente
   **/
  public XMLObject marshal(Object bean, int level) {
    if (bean == null) {
      return new XMLObject("NODEF_NULL");
    }
    Class c = bean.getClass();
    String tag = getShortName(c);
    return marshal(tag, bean, level, false);
  }

  /**
   * Esporta il bean in un XMLObject
   * @param bean l'oggetto da visualizzare in formato XML
   * @param level livello di visualizzazione dell'albero xml, evita il loop dovuto ai riferimenti circolari
   * @param cdata flag per specificare l'inserimento dei ![CDATA[]] per i valori testuali dell'xml
   * @return L'XMLObject che rappresenta il bean, il toString lo visualizza testualmente
   **/
  public XMLObject marshal(Object bean, int level, boolean cdata) {
    if (bean == null) {
      return new XMLObject("NODEF_NULL");
    }
    Class c = bean.getClass();
    String tag = getShortName(c);
    return marshal(tag, bean, level, cdata);
  }

  private XMLObject marshal(String tagName, Object bean, int level, boolean cdata) {
    if (bean == null) {
      return new XMLObject("NODEF_NULL");
    }
    Class c = bean.getClass();
    XMLObject tag = new XMLObject(tagName);
    tag.setCdataFlag(cdata);
    tag.addAttribute("type", c.getName());
    if (bean instanceof Collection) {
      tag.addAttribute("size", Integer.toString(((Collection) bean).size()));
      Iterator iter = ((Collection) bean).iterator();
      int pos = 0;
      while (iter.hasNext()) {
        Object object = iter.next();
        if (level > 0) {
          tag.addContent(marshal("ele" + pos++, object, level - 1, cdata));
        } else {
          tag.addAttribute("trunked", object.getClass().getName());
        }
      }
      return tag;
    }
    if (bean instanceof Object[]) {
      int length = Array.getLength(bean);
      tag.addAttribute("size", Integer.toString(length));
      for (int i = 0; i < length; i++) {
        Object object = Array.get(bean, i);
        if (level > 0) {
          tag.addContent(marshal("ele" + i, object, level - 1, cdata));
        } else {
          tag.addAttribute("trunked", object.getClass().getName());
        }
      }
      return tag;
    }
    if (bean instanceof Map) {
      int length = ((Map) bean).size();
      tag.addAttribute("size", Integer.toString(length));

      Iterator iter = ((Map) bean).keySet().iterator();
      int pos = 0;
      while (iter.hasNext()) {
        Object key = iter.next();
        if (level > 0) {
          tag.addContent(marshal(key.toString(), ((Map) bean).get(key), level - 1, cdata));
        } else {
          tag.addAttribute("trunked", key.toString());
        }
      }
      return tag;
    }

    try {
      if (level > 0) {
        ArrayList<Field> fields = new ArrayList<Field>();
        getAllFields(bean.getClass(), fields);
        if (fields.size() > 0) {
          Iterator<Field> iter = fields.iterator();
          while (iter.hasNext()) {
            Field field = iter.next();
            Object value = getValue(field, bean);
            if (value != null) {
              try{
              tag.addContent(marshal(field.getName(), value, level - 1, cdata));
              }catch(Exception ex){
                XMLObject ex_tag = new XMLObject(field.getName());
                ex_tag.addAttribute("type", value.getClass().getName());
                ex_tag.setText(ex.getMessage());
                tag.addContent(ex_tag);
              }
            }
          }
        }
        if (tag.getChildren().isEmpty()) {
          tag.setText(formatValue(bean));
        }
      } else {
        tag.addAttribute("trunked", bean.getClass().getName());
      }
    } catch (Exception ex) {
    }

    return tag;

  }
  
  
  
  private XMLObject marshalHTMLForm(Class bean) {
	    XMLObject form = new XMLObject("form");
	    if(bean==null) return form;
	    try {
	        ArrayList<Field> fields = new ArrayList<Field>();
	        getAllFields(bean, fields);
	        if (fields.size() > 0) {
	          Iterator<Field> iter = fields.iterator();
	          while (iter.hasNext()) {
	              Field field = iter.next();    
	              XMLObject par=new XMLObject("p");
	              XMLObject label=new XMLObject("label");
	              label.setCdataFlag(false);
	              label.addAttribute("for", field.getName());
	              label.addAttribute("class", "prop_label");
	              label.setText(field.getName());
	              par.addContent(label);
	              
	              XMLObject input=new XMLObject("input");
	      					
	              input.addAttribute("type", "text");
	              input.addAttribute("name", bean.getSimpleName().toLowerCase()+"."+field.getName());
	              input.addAttribute("class", "right_input");
	              input.addAttribute("value", "<s:property value=\""+bean.getSimpleName().toLowerCase()+"."+field.getName()+"\"/>");
	              
	              par.addContent(input);
	              
	              form.addContent(par);        
	          }
	        }
	    } catch (Exception ex) {
	    }
	    return form;
	  } 
  

  private void getAllFields(Class bean, ArrayList<Field> fields) {
    if (bean != null && !SCALAR.contains(bean)) {
      Field[] partial = bean.getDeclaredFields();
      fields.addAll(Arrays.asList(partial));
      Class superC = bean.getSuperclass();
      getAllFields(superC, fields);
    }
  }

  private String formatValue(Object value) {
    String outValue = null;
    if (value == null) {
      return "NULL";
    }
    if (value instanceof java.util.Date) {
      DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      outValue = formatter.format(value);
      return outValue;
    }
    if (value instanceof String) {
      return filterPCDATA(value.toString());
    }
    if (value instanceof Number) {
      return filterPCDATA(value.toString());
    }
    if (value instanceof Boolean) {
      return filterPCDATA(value.toString());
    }
    if (value instanceof Character) {
      return filterPCDATA(value.toString());
    }
    return "UNDEFINED";
  }

  private Object getValue(Field attribute, Object bean) {
    try {
      if(attribute.getModifiers()==1){
	  return attribute.get(bean);
      }
      char first = Character.toUpperCase(attribute.getName().charAt(0));
      String methodName = "get" + first + attribute.getName().substring(1);
      Class paramTypes[] = new Class[0];
      Method method = bean.getClass().getMethod(methodName, paramTypes);
      return method.invoke(bean, new Object[0]);
    } catch (Exception e) {
      //e.printStackTrace();
      return null;
    }
  }

  private String getShortName(Class c) {
    String name = c.getName();
    int i = name.lastIndexOf('.');
    if (i == -1) {
      return name.toLowerCase();
    }
    return (name.substring(i + 1).toLowerCase());
  }

  private static String filterPCDATA(String s) {
    for (int i = 0; i < s.length(); i++) {
      switch (s.charAt(i)) {
        case '&':
          s = s.substring(0, i) + "&amp;" + s.substring(i + 1);
          break;
        case '>':
          s = s.substring(0, i) + "&gt;" + s.substring(i + 1);
          break;
        case '<':
          s = s.substring(0, i) + "&lt;" + s.substring(i + 1);
          break;
        case '\"':
          s = s.substring(0, i) + "&quot;" + s.substring(i + 1);
          break;
        case '\'':
          s = s.substring(0, i) + "&apos;" + s.substring(i + 1);
          break;

      }
    }
    return s;
  }

  /**
   * Esporta il bean in formato testuale xml
   * @param bean l'oggetto da visualizzare in formato XML
   * @param level livello di visualizzazione dell'albero xml, evita il loop dovuto ai riferimenti circolari
   * @param cdata flag per specificare l'inserimento dei ![CDATA[]] per i valori testuali dell'xml
   * @return L'XMLObject che rappresenta il bean, il toString lo visualizza testualmente
   **/
  public String show(Object obj, int level, boolean cdata) {
    XMLObject xobj = marshal(obj, level, cdata);
    return xobj.toString();
  }

  /**
   * Esporta il bean in formato testule xml
   * @param bean l'oggetto da visualizzare in formato XML
   * @param level livello di visualizzazione dell'albero xml, evita il loop dovuto ai riferimenti circolari
   * @return L'XMLObject che rappresenta il bean, il toString lo visualizza testualmente
   **/
  public String show(Object obj, int level) {
    XMLObject xobj = marshal(obj, level);
    return xobj.toString();
  }
  
  /**
   * Crea un form HTML semplice del bean
   * @param bean il bean su cui basare la form
   * @return l'html della form
   **/
  public String showHTMLForm(Class bean) {
    XMLObject form = marshalHTMLForm(bean);
    return form.toString();
  }
  
  
  
  
  
//    public static void main(String[] args) {
//
//        Field[] fields = Integer.class.getDeclaredFields();
//        for (Field field : fields) {
//            System.out.println(field);
//        }
//        Object test = new Object() {
//
//            String ciao = "Stringa di salute";
//            Date time = new Date();
//            String[] prova = new String[]{"Prova1", "Prova2", "Prova3"};
//
//            public String getCiao() {
//                return ciao;
//            }
//
//            public void setCiao(String ciao) {
//                this.ciao = ciao;
//            }
//
//            public Date getTime() {
//                return time;
//            }
//
//            public void setTime(Date time) {
//                this.time = time;
//            }
//
//            public String[] getProva() {
//                return prova;
//            }
//
//            public void setProva(String[] prova) {
//                this.prova = prova;
//            }
//        };
//        System.out.println(XMLBeanViewer.getInstance().show(test, 2,true));
//    }
}

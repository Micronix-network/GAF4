package it.micronixnetwork.gaf.service.hibernate;

import it.micronixnetwork.gaf.service.OrderedSearchResult;
import it.micronixnetwork.gaf.service.SearchEngine;
import it.micronixnetwork.gaf.service.SearchEngineException;
import it.micronixnetwork.gaf.service.SearchResult;
import it.micronixnetwork.gaf.util.LogUtil;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

/**
 * Implementazione furba del search engine hibernate dipendente Questo nuovo
 * SearchEngine usa la reflection per settare in automatico i parametri nella
 * query, in pratica è necessario solo definire le clausole di where dando ai
 * paramtri lo stesso nome degli attributi dichiarati nel Search engine.
 * Costurzione della logica del SearchEngine, l'ordine con cui vengono fatte le
 * operazioni non è importante
 * 
 * è possibile specificare se una proprietà di tipo String debba essere
 * formattata in modo particolare al momento del setting del valore nella query,
 * questo si realizza definendo un fillPattern per il campo specifico tramite il
 * metodo
 * 
 * - addFieldPattern("fieldName", "pattern"); Definizione pattern di fill che
 * definisce le regole che utilizzerà l'engine per settare i parametri di tipo
 * stringa definite nelle clausole di where. il pattern è una string che deve
 * rispettare l'espressione regolare [%]{0,1}[^#]*#[uU]{0,1}[%]{0,1}
 * 
 * Es. %cccccc#u% # : dove viene messo il valore del campo relativo a fildName c
 * : caratteri di rimepimento possono essere qualunque u/U: specificano se deve
 * essere fatto l'uppercase o il lowerCase del vaolre % : indica la wild, card
 * può essere messa all'inizio o alla fine o in entrambe le parti
 * 
 * Esempio di implementazione del search Engine public class TestSmarty extends
 * HibernateSmartySearchEngine {
 * 
 * private String ciccio; private Integer uno; private Double due; private Date
 * trullo;
 * 
 * public String getCiccio() { return ciccio; }
 * 
 * public Date getTrullo() { return trullo; }
 * 
 * public Double getDue() { return due; }
 * 
 * public Integer getUno() { return uno; }
 * 
 * public TestSmarty(Session session) { super(session); }
 * 
 * public TestSmarty(Session session, String ciccio, Date trullo) {
 * super(session); this.ciccio = ciccio; this.trullo = trullo; this.uno=2;
 * this.due=4d;
 * 
 * }
 * 
 * @Override protected void build() { setSelectString("Select from Prova");
 *           addWhereClausole("ciccio,trullo",
 *           "(ciccio like :0 or trullo > :1)"); addWhereClausole("trullo",
 *           "trullo > :0"); addWhereClausole("ciccio,trullo",new
 *           String[]{"trullo > :0","ciccio = :0"});
 *           addWhereClausole("due,uno",new String[]{"due > :0","uno = :1"}); }
 *           }
 * 
 *           la query prodotta da questo search engine è la seguente:
 * 
 *           Select from Prova where trullo > :trullo and (ciccio like :ciccio
 *           or trullo > :trullo) and ( trullo > :ciccio or ciccio = :ciccio)
 *           and ( due > :due or uno = :uno)
 * 
 *           Al momento della produce il Search engine recupererà in automatico,
 *           via reflection, i valori da sostituire alle etichette :trullo,
 *           :ciccio, :uno, :due;
 * 
 *           Questo searchEngine gestisce anche la produzione di
 *           OrderedSearchResult in base alla presenza o meno di criteri di
 *           ordinamento aggiunti tramite il metodo addOrderField([nome field])
 * 
 */
public abstract class HibernateSmartySearchEngine implements SearchEngine {

    protected static Log log = LogFactory.getLog(HibernateSmartySearchEngine.class);
    protected Session session;
    private Hashtable<String, String> fieldClausoles;
    private Hashtable<String, String[]> orFieldClausoles;
    private ArrayList<String> pureClausoles;
    private Hashtable<String, String> fieldPatterns;
    private ArrayList<String> orderFields;
    private Hashtable<String, String> orderFieldAliases;
    private String selectString = null;
    private Hashtable<String, Object> fieldValues;
    private Integer limit;
    private boolean sql=false;

    public HibernateSmartySearchEngine(Session session) {
	this.session = session;
	fieldClausoles = new Hashtable<String, String>();
	orFieldClausoles = new Hashtable<String, String[]>();
	fieldPatterns = new Hashtable<String, String>();
	orderFieldAliases = new Hashtable<String, String>();
	orderFields = new ArrayList<String>();
	pureClausoles = new ArrayList<String>();
	fieldValues = new Hashtable<String, Object>();
    }
    
    public void setLimit(Integer limit) {
	this.limit = limit;
    }
    
   public void setSql(boolean sql) {
    this.sql = sql;
   }
   
    List<String> stringToList(String val) {
	if (val != null) {
	    String[] list = val.split("[ ]*,[ ]*");
	    return Arrays.asList(list);
	} else {
	    return Collections.EMPTY_LIST;
	}
    }

    public void setValue(String fieldName, Object value) {
	if (fieldName != null) {
	    fieldValues.put(fieldName, value);
	}
    }

    /**
     * Ritorna la stringa di select ossia la prima parte della query priva dei
     * filtri
     * 
     * @return
     */
    public String getSelectString() {
	return selectString;
    }

    /**
     * Setta la stringa di select
     * 
     * @param selectString
     */
    public void setSelectString(String selectString) {
	this.selectString = selectString;
    }

    /**
     * metodo richiamato dalla produce result per
     */
    protected abstract void build();

    protected String createOrderBy() throws SearchEngineException {
	String result = "";
	int size = orderFields.size();
        boolean comma=false;
	for (int i = 0; i < size; i++) {
	    String realField = orderFieldAliases.get(orderFields.get(i));
	    if (realField == null) {
		info("Alias del campo: " + orderFields.get(i) + " non definito");
	    }else{
                if (comma) {
                    result += ",";
                }
                if(!realField.contains("none")){
                    result += realField;
                    comma=true;
                }
            }
	    
	}
	return result;
    }

    private void setParameters(Query query) {
	Set<String> keys = fieldClausoles.keySet();
	for (String key : keys) {
	    List<String> fields = stringToList(key);
	    for (int i = 0; i < fields.size(); i++) {
		String fieldName = fields.get(i);
		Object value = getFieldValue(fieldName);
		debug("Settaggio parametri query " + fieldName + ": " + value);
		setParameter(query, value, fieldName);
	    }
	}
	keys = orFieldClausoles.keySet();
	for (String key : keys) {
	    List<String> fields = stringToList(key);
	    for (int i = 0; i < fields.size(); i++) {
		String fieldName = fields.get(i);
		Object value = getFieldValue(fieldName);
		debug("Settaggio parametri query " + fieldName + ": " + value);
		setParameter(query, value, fieldName);
	    }
	}
    }

    private String createWhere() {
	String result = "";
	Set<String> keys = fieldClausoles.keySet();
	int i = 0;
	for (String key : keys) {
	    if (i > 0) {
		result += " and";
	    }
	    result += " " + fieldClausoles.get(key);
	    i++;
	}
	for (int j = 0; j < pureClausoles.size(); j++) {
	    if (i > 0) {
		result += " and";
	    }
	    result += " " + pureClausoles.get(j);
	    i++;
	}
	keys = orFieldClausoles.keySet();
	for (String key : keys) {
	    if (i > 0) {
		result += " and ";
	    }
	    result += " (";
	    String[] orClausoles = orFieldClausoles.get(key);
	    for (int z = 0; z < orClausoles.length; z++) {
		if (z > 0) {
		    result += " or";
		}
		result += " " + orClausoles[z];
	    }
	    i++;
	    result += ")";
	}
	return result;
    }

    protected final String createQueryString() throws SearchEngineException {
	String queryStr = getSelectString();
	if (queryStr == null) {
	    throw new SearchEngineException("Select string non definita");
	}
	String where = createWhere();
	String orderBy = createOrderBy();
	if (where != null && where.trim().length() > 0) {
	    queryStr += " where " + where;
	}
	if (orderBy != null && !orderBy.equals("")) {
	    queryStr += " order by " + orderBy;
	}
	return queryStr;
    }

    private void setParameter(Query query, Object field, String key) {
	if (field == null) {
	    return;
	}
	if (field instanceof String) {
	    if (((String) field).trim().equals("")) {
		return;
	    }
	}
	if (field instanceof Character) {
	    if (field.toString().trim().equals("")) {
		return;
	    }
	}
	if (field instanceof String) {
	    String fieldPattern = fieldPatterns.get(key);
	    query.setString(key, processPattern((String) field, fieldPattern));
	}
	if (field instanceof Date) {
	    query.setDate(key, (Date) field);
	}
	if (field instanceof Integer) {
	    query.setInteger(key, (Integer) field);
	}
	if (field instanceof Character) {
	    query.setCharacter(key, (Character) field);
	}
	if (field instanceof Double) {
	    query.setDouble(key, (Double) field);
	}
	if (field instanceof Float) {
	    query.setFloat(key, (Float) field);
	}
	if (field instanceof Long) {
	    query.setLong(key, (Long) field);
	}
	if (field instanceof Boolean) {
	    query.setBoolean(key, (Boolean) field);
	}
	if (field instanceof Short) {
	    query.setShort(key, (Short) field);
	}
	if (field instanceof Collection) {
	    query.setParameterList(key, (Collection) field);
	}
	if (field instanceof BigDecimal) {
	    query.setBigDecimal(key, (BigDecimal) field);
	}
    }

    private String processPattern(String field, String pattern) {
	if (pattern == null) {
	    return field;
	}
	// Controllo pattern
	if (pattern.matches("[%]{0,1}[^#]*#[uU]{0,1}[%]{0,1}")) {
	    String filler = "";
	    filler = pattern.replaceAll("[%#uU]", "");
	    if (filler.length() > 0 && filler.length() > (field.length() - 1)) {
		filler = filler.substring(0, filler.length() - (field.length() - 1));
		field = filler + field;
	    }
	    // Gestione uppercase
	    if (pattern.matches("[^#]*#[U][%]{0,1}")) {
		field = field.toUpperCase();
	    }

	    // Gestione lowercase
	    if (pattern.matches("[^#]*#[u][%]{0,1}")) {
		field = field.toLowerCase();
	    }
	    // Sostituzione field nel pattern
	    String fillPurged = pattern.replaceAll("[^#%]", "");
	    return fillPurged.replaceAll("#", field);
	}
	return field;
    }

    /**
     * aggiunge una clausola di where statica, tutte le clausole inserite
     * mediante questometodo vengono messe in filtro in and con le altre.
     * 
     * @param clausole
     *            la clausola hql
     */
    protected void addWhereClausole(String clausole) {
	if (clausole == null) {
	    return;
	}
	pureClausoles.add(clausole);
    }

    /**
     * Inserisce la clausola di where se tutti i field passati risultano
     * valorizzati (!=null)
     * 
     * @param fieldNames
     * @param clausole
     */
    protected void addWhereClausole(String fieldNames, String clausole) {
	if (fieldNames == null || clausole == null) {
	    return;
	}
	List<String> fields = stringToList(fieldNames);
	String replacedClausole = clausole;

	for (int i = 0; i < fields.size(); i++) {
	    String fieldName = fields.get(i);
	    Object fieldValue = getFieldValue(fieldName);
	    if (fieldValue == null) {
		return;
	    }
	    if (fieldValue instanceof String) {
		if (((String) fieldValue).trim().equals("")) {
		    return;
		}
	    }
	    if (fieldValue instanceof Character) {
		if (fieldValue.toString().trim().equals("")) {
		    return;
		}
	    }
	    replacedClausole = replacedClausole.replaceAll(":" + i, ":" + fieldName);
	}
	fieldClausoles.put(fieldNames, replacedClausole);
    }

    protected void addWhereClausole(String fieldNames, String[] clausoles) {
	if (fieldNames == null || clausoles == null) {
	    return;
	}
	List<String> fields = stringToList(fieldNames);
	String[] purged = clausoles;
	// Purge delle clausole contenenti field nulli
	for (int i = 0; i < fields.size(); i++) {
	    String fieldName = fields.get(i);
	    Object fieldValue = getFieldValue(fieldName);
	    if (fieldValue == null || (fieldValue instanceof String && fieldValue.toString().trim().equals("")) || (fieldValue instanceof Character && fieldValue.toString().trim().equals(""))) {
		purged = purge(purged, i);
	    }
	}

	for (int i = 0; i < fields.size(); i++) {
	    String fieldName = fields.get(i);
	    purged = replaceFiledName(purged, i, fieldName);
	}

	if (purged.length > 0) {
	    orFieldClausoles.put(fieldNames, purged);
	}
    }

    private String[] replaceFiledName(String[] purged, int i, String fieldName) {
	ArrayList<String> result = new ArrayList<String>();
	for (String clausole : purged) {
	    result.add(clausole.replaceAll(":" + i, ":" + fieldName));
	}
	return result.toArray(new String[result.size()]);
    }

    private String[] purge(String[] purged, int i) {
	ArrayList<String> result = new ArrayList<String>();
	for (String clausole : purged) {
	    if (clausole.indexOf(":" + i) == -1) {
		result.add(clausole);
	    }
	}
	return result.toArray(new String[result.size()]);
    }

    public void addOrderField(String field) {
	if (field == null) {
	    return;
	}
	if (field.trim().equals("")) {
	    return;
	}
	orderFields.add(field);
    }

    protected void addFieldAlias(String field, String realField) {
	if (field == null || realField == null) {
	    return;
	}
	if (field.trim().equals("") || realField.trim().equals("")) {
	    return;
	}
	orderFieldAliases.put(field, realField);
    }

    protected void addFieldPattern(String field, String pattern) {
	if (field == null || pattern == null) {
	    return;
	}
	if (((String) field).trim().equals("")) {
	    return;
	}
	fieldPatterns.put(field, pattern);
    }

    public SearchResult produceResult(Integer page, Integer size) throws SearchEngineException {
	build();
	Long start = System.currentTimeMillis();
	Query query;
	
	String query_s=createQueryString();
	
	debug("QUERY SMARTY: " + query_s);
	
	if(sql){
	    query= session.createSQLQuery(query_s);
	}else{
	    query = session.createQuery(query_s);
	}
	if(limit!=null)
	    query.setMaxResults(limit);
	
	debug("QUERY HIBERNATE: " + query);

	setParameters(query);

	List dtoList = new ArrayList();
	Long recordNum = new Long(0);
	
	if (page != null && size != null) {
	    
	    if (page == null)
		page = 1;
	    if (size == null)
		size = 20;
	    ScrollableResults sr = query.setReadOnly(true).scroll();
	    if (sr.first()) {
		sr.last();
		recordNum = new Long(sr.getRowNumber() + 1);
		page = checkPage(page, recordNum, size);
		debug("Scroll to: " + (page - 1) * size);
		sr.setRowNumber((page - 1) * size);
		for (int i = 0; (i < size); i++) {
		    dtoList.add(sr.get(0));
		    if (sr.isLast()) {
			break;
		    }
		    sr.next();
		}
	    }
	    sr.close();
	} else {
	    dtoList = query.list();
	    recordNum = new Long(dtoList.size());
	}
	SearchResult result = new SearchResult(recordNum, page, size, dtoList);
	if (orderFields.size() > 0) {
	    result = new OrderedSearchResult(result, orderFields);
	}
	String time = new Long((System.currentTimeMillis() - start)).toString();
	debug(">>>Tempo totale ricerca: " + time);
	return result;
    }

    private Object getFieldValue(String fieldName) {
	Object result = null;
	result = fieldValues.get(fieldName);
	if (result != null)
	    return result;
	try {
	    char first = Character.toUpperCase(fieldName.charAt(0));
	    String methodName = "get" + first + fieldName.substring(1);
	    Class paramTypes[] = new Class[0];
	    Method method = this.getClass().getMethod(methodName, paramTypes);
	    result = method.invoke(this, new Object[0]);
	} catch (Exception e) {
	    // e.printStackTrace();
	    return null;
	}
	return result;
    }

    private int checkPage(int page, long recordNum, int size) {
	long pages = (long) Math.floor((double) recordNum / (double) size);
	long mod = (long) Math.floor((double) recordNum % (double) size);
	if (mod != 0) {
	    pages += 1;
	}
	if (page > pages) {
	    page = (int) pages;
	}
	return page;
    }

    public static void main(String[] args) {
	// TestSmarty sm = new TestSmarty(null, "Ciao", new Date());
	// String field = "ciao";
	// String pattern = "%d0000000#U%";
	//
	// System.out.println(pattern.matches("[%]{0,1}[^#]*#[uU]{0,1}[%]{0,1}"));
	//
	// //Recupero filler
	// String filler = "";
	// filler = pattern.replaceAll("[%#]", "");
	// //filler=filler.replaceAll("#","");
	//
	// filler = filler.substring(0, filler.length() - (field.length() - 1));
	//
	// field = filler + field;
	//
	// //Controllo uppercase
	//
	// System.out.println(pattern.matches("[^#]*#[U][%]{0,1}"));
	//
	//
	// //Sostituzione field nel pattern
	//
	// String test = pattern.replaceAll("[^#%]", "");
	//
	// String result = test.replaceAll("#", field);
	//
	// System.out.println(result);
	// try {
	// sm.setSelectString("Select from Prova");
	// //sm.addWhereClausole("ciccio,trullo",
	// "(ciccio like :0 or trullo > :1)");
	// sm.addWhereClausole("ciccio<>1");
	// //sm.addWhereClausole("trullo", "trullo > :0");
	// sm.addWhereClausole("ciccio,trullo",new
	// String[]{"trullo > :0","ciccio = :0"});
	// sm.addWhereClausole("due,uno",new String[]{"due > :0","uno = :1"});
	// System.out.println(sm.createQueryString());
	// } catch (SearchEngineException ex) {
	// Logger.getLogger(HibernateSmartySearchEngine.class.getName()).log(Level.SEVERE,
	// null, ex);
	// }
    }

    protected void info(String msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.INFO,"SE");
    }

    protected void debug(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.DEBUG,"SE");
    }

    protected void fatal(Object msg) {
	StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	LogUtil.log(ste, this.getClass().getName(), log, msg, null, LogUtil.LOGTYPE.FATAL,"SE");
    }
}

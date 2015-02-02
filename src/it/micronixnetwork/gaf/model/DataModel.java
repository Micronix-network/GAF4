/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.gaf.model;

import it.micronixnetwork.gaf.util.StringUtil;
import it.micronixnetwork.gaf.util.xml.XMLObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author kobo
 */
public class DataModel implements Serializable{
    
    static final long serialVersionUID = 1L;

    private final Log log = LogFactory.getLog(DataModel.class);
    
    /*attributi per l'esportazione XML*/
    public static String TAG = "dataModel";
    public static final String colTitles_a = "colTitles";
    public static final String colKeys_a = "colKeys";
    public static final String rowKeys_a = "rowKeys";
    public static final String colDescription_a = "colDescription";
    public static final String rowDescription_a = "rowDescription";
    public static final String rowTitles_a = "rowTitles";
    public static final String description_a = "description";
   
    /*Mappa per immagazzinare informazioni caratteristiche del data model*/
    private final HashMap<String, Object> info = new HashMap<String, Object>();

    /*Attributi di righa*/
    private final LinkedHashMap<String, HashMap<String, Object>> rowAttributes = new LinkedHashMap<String, HashMap<String, Object>>();

    /*Attributi di colonna*/
    private final LinkedHashMap<String, HashMap<String, Object>> colAttributes = new LinkedHashMap<String, HashMap<String, Object>>();
    
    /*Struttura dati*/
    private List<List> data;
    
    /*Indice delle chiavi di righa*/
    private LinkedHashMap<String,Integer> rowKeyIndex=new LinkedHashMap<String, Integer>();
    
    /*Indice delle chiavi di colonna*/
    private LinkedHashMap<String,Integer> colKeyIndex=new LinkedHashMap<String, Integer>();
    
    /*Array delle chiavi di riga*/
    private String[] rowKeys=new String[0];
    
    /*Array delle chiavi di colonna*/
    private String[] colKeys=new String[0];
    
    /*Array delle label associate alle righe*/
    private String[] rowTitles=new String[0];
    
    /*Array delle label associate alle colonne*/
    private String[] colTitles=new String[0];
    
    /*Descrizione delle righe*/
    private String rowDescription="";
    
    /*Descrizione delle colonne*/
    private String colDescription="";
    
    /*Descrizione del data model*/
    private String description="";
    

    public DataModel() {
	setData(new ArrayList());
    }

    public DataModel(String[] rowKeys, String[] colKeys) {
	if (rowKeys != null && colKeys != null) {
	    List<List> model = new ArrayList<List>();
	    for (String ele : rowKeys) {
		ArrayList row = new ArrayList(colKeys.length);
		model.add(row);
	    }
	    setData(model);
	    setColKeys(colKeys);
	    setRowKeys(rowKeys);
	}
    }

    public DataModel(String[] rowKeys, List<String> colKeys) {
	if (rowKeys != null && colKeys != null) {
	    List<List> model = new ArrayList<List>();
	    for (String ele : rowKeys) {
		ArrayList row = new ArrayList(colKeys.size());
		model.add(row);
	    }
	    setData(model);
	    setColKeys(colKeys);
	    setRowKeys(rowKeys);
	}
    }

    public DataModel(List<String> rowKeys, String[] colKeys) {
	if (rowKeys != null && colKeys != null) {
	    List<List> model = new ArrayList<List>();
	    for (String ele : rowKeys) {
		ArrayList row = new ArrayList(colKeys.length);
		model.add(row);
	    }
	    setData(model);
	    setColKeys(colKeys);
	    setRowKeys(rowKeys);
	}
    }

    public DataModel(List<String> rowKeys, List<String> colKeys) {
	if (rowKeys != null && colKeys != null) {
	    List<List> model = new ArrayList<List>();
	    for (String ele : rowKeys) {
		ArrayList row = new ArrayList(colKeys.size());
		model.add(row);
	    }
	    setData(model);
	    setColKeys(colKeys);
	    setRowKeys(rowKeys);
	}
    }

    public DataModel(List<List> data) {
	this();
	setData(data);
    }

    public String getColDescription() {
	return colDescription;
    }

    public void setColDescription(String colDescription) {
	this.colDescription=colDescription;
    }

    public String getDescription() {
	return description;
    }
    
    public void setDescription(String description) {
	this.description = description;
    }

    public void setRowDescription(String rowDescription) {
	this.rowDescription = rowDescription;
    }
    
    public String getRowDescription() {
	return rowDescription;
    }

    public List<List> getData() {
	return data;
    }

    public void addDataRow(List values) {
	if (data != null) {
	    data.add(values);
	}
	setData(data);
    }

    public void setData(List<List> data) {
	this.data = data;

    }

   
    public XMLObject toXmlObject() {
	
	XMLObject out=new XMLObject(TAG);
	
	out.setStringArrayAttribute(rowKeys_a, getRowKeys());
	out.setStringArrayAttribute(colKeys_a, getColKeys());
	
	
	if(colTitles.length==0){
	    out.setStringArrayAttribute(colTitles_a, getColKeys());
	}else{
	    out.setStringArrayAttribute(colTitles_a, getColTitles());
	}
	
	if(rowTitles.length==0){
	    out.setStringArrayAttribute(rowTitles_a, getRowKeys());
	}else{
	    out.setStringArrayAttribute(rowTitles_a, getRowTitles());
	}
	
	if(StringUtil.EmptyOrNull(out.getAttribute(rowTitles_a))){
	    out.addAttribute(rowTitles_a, out.getAttribute(rowKeys_a));
	}
	XMLObject dataXML;
	XMLObject infoXML;
	XMLObject colsAtts;
	XMLObject rowsAtts;
	
	int cols = getCols();
	dataXML = new XMLObject("data_rows");
	infoXML = new XMLObject("info");
	colsAtts = new XMLObject("cols_attributes");
	rowsAtts = new XMLObject("rows_attributes");

	// Create row
	for (int i = 1; i <= getRows(); i++) {
	    for (int j = 1; j <= cols; j++) {
		XMLObject cell = new XMLObject("cell");
		cell.addAttribute("col", getColKey(j));
		cell.addAttribute("row", getRowKey(i));
		Object obj = getElementAt(i, j);
		if (obj != null) {
		    cell.setText(getElementAt(i, j).toString());
		}
		dataXML.addContent(cell);
	    }
	}

	// Create info
	for (String key : info.keySet()) {
	    XMLObject param = new XMLObject("param");
	    param.addAttribute("name", key);
	    if (info.get(key) != null) {
		param.setText(info.get(key).toString());
	    }
	    infoXML.addContent(param);
	}

	// Create col attributes
	for (String key : colAttributes.keySet()) {
	    XMLObject col = new XMLObject("col_attributes");
	    col.addAttribute("col", key);
	    if (colAttributes.get(key) != null) {
		for (String attr : colAttributes.get(key).keySet()) {
		    Object toView=colAttributes.get(key).get(attr);
		    col.addAttribute(attr, toView!=null?toView.toString():"");
		}
	    }
	    colsAtts.addContent(col);
	}

	// Create row attributes
	for (String key : rowAttributes.keySet()) {
	    XMLObject row = new XMLObject("row_attributes");
	    row.addAttribute("row", key);
	    if (rowAttributes.get(key) != null) {
		for (String attr : rowAttributes.get(key).keySet()) {
		    Object toView=rowAttributes.get(key).get(attr);
		    row.addAttribute(attr, toView!=null?toView.toString():"");
		}
	    }
	    rowsAtts.addContent(row);
	}

	out.addContent(infoXML);
	out.addContent(colsAtts);
	out.addContent(rowsAtts);
	out.addContent(dataXML);
	return out;
    }
    
    
    public String[] getColTitles() {
	if(colTitles==null || colTitles.length==0){
	    return colKeys;
	}
	return colTitles;
    }
    
    public void setColTitles(String[] titles) {
	this.colTitles=titles;
    }

    public void setRowTitles(String[] titles) {
	this.rowTitles=titles;
    }
    
    public void setColTitles(List<String> titles) {
	this.colTitles=titles.toArray(new String[titles.size()]);
    }

    public void setRowTitles(List<String> titles) {
	this.rowTitles=titles.toArray(new String[titles.size()]);
    }
    
    public String[] getRowTitles() {
	if(rowTitles==null || rowTitles.length==0){
	    return rowKeys;
	}
	return rowTitles;
    }

    public String[] getColKeys() {
	return colKeys;
    }

    public void setColKeys(String[] keys) {
	this.colKeys=keys;
	for(int i=0;i<keys.length;i++){
	    colKeyIndex.put(keys[i], i+1);
	}
    }

    public void setColKeys(List<String> keys) {
	this.colKeys=keys.toArray(new String[keys.size()]);
	for(int i=0;i<keys.size();i++){
	    colKeyIndex.put(keys.get(i), i+1);
	}
    }

    public String[] getRowKeys() {
	return rowKeys;
    }

    public void setRowKeys(String[] keys) {
	this.rowKeys=keys;
	for(int i=0;i<keys.length;i++){
	    rowKeyIndex.put(keys[i], i+1);
	}
    }

    public void setRowKeys(List<String> keys) {
	this.rowKeys=keys.toArray(new String[keys.size()]);
	for(int i=0;i<keys.size();i++){
	    rowKeyIndex.put(keys.get(i), i+1);
	}
    }

    public String getColTitle(int col) {
	String title = null;
	try {
	    title = getColTitles()[col - 1];
	} catch (Exception ex) {
	}
	if (title == null) {
	    try {
		title = getColKey(col);
	    } catch (Exception ex) {
	    }
	}
	return title;
    }
    
    public String getColTitle(String colkey) {
	int index=getColIndex(colkey);
	if(index==-1) return null;
	return getColTitle(index);
    }

    public String getRowTitle(int row) {
	String title = null;
	
	try {
	    title = getRowTitles()[row - 1];
	} catch (Exception ex) {
	}
	if (title == null) {
	    try {
		title = getRowKey(row);
	    } catch (Exception ex) {
	    }
	}
	return title;
    }
    
    public String getRowTitle(String rowkey) {
	int index=getRowIndex(rowkey);
	if(index==-1) return null;
	return getRowTitle(index);
    }

    public String getColKey(int col) {
	String key = "";
	try {
	    key = getColKeys()[col - 1];
	} catch (Exception ex) {
	}
	return key;
    }

    public String getRowKey(int row) {
	String key = "";
	try {
	    key = getRowKeys()[row - 1];
	} catch (Exception ex) {
	}
	return key;
    }

    public Object getElementAt(int row, int col) {
	if (row <= 0) {
	    return null;
	}
	if (row > data.size()) {
	    return null;
	}
	Object dataRow = data.get(row - 1);
	if (dataRow instanceof List) {
	    if (col <= 0) {
		return null;
	    }
	    if (col > ((List) dataRow).size()) {
		return null;
	    }
	    return ((List) dataRow).get(col - 1);
	}
	return null;
    }

    public Object getElementAt(int row, int col, Object def) {
	Object result = getElementAt(row, col);
	if (result == null) {
	    return def;
	}
	return result;
    }

    public Object getElementAt(String rkey, String ckey) {

	int indeR = getRowIndex(rkey);
	int indeC = getColIndex(ckey);

	if (log.isDebugEnabled()) {
	    log.debug("Conversione indici: row=" + indeR + ", col=" + indeC + " valore: " + getElementAt(indeR, indeC));
	}
	
	if(indeR==-1 || indeC==-1) return null;
	
	return getElementAt(indeR, indeC);

    }

    public Object getElementAt(String rkey, String ckey, Object def) {
	Object result = getElementAt(rkey, ckey);
	if (result == null) {
	    return def;
	}
	return result;
    }

    private void updateRowKeysIndex(String key) {
	if (rowKeyIndex.size()==0) {
	    rowKeyIndex.put(key, 1);
	    rowKeys=new String[]{key};
	    return;
	}
	int indeR = getRowIndex(key);
	if (indeR != -1) {
	    return;
	}
	int index = rowKeyIndex.size();
	rowKeyIndex.put(key, index+1);
	
	String[] newRKeys = new String[index + 1];
	for (int i = 0; i < rowKeys.length; i++) {
	    newRKeys[i] = rowKeys[i];
	}
	newRKeys[index] = key;
	rowKeys=newRKeys;
    };

    private void updateColKeysIndex(String key) {
	if (colKeyIndex.size()==0) {
	    colKeyIndex.put(key, 1);
	    colKeys=new String[]{key};
	    return;
	}
	int indeC = getColIndex(key);
	if (indeC != -1) {
	    return;
	}
	int index = colKeyIndex.size();
	colKeyIndex.put(key, index+1);
	
	String[] newCKeys = new String[index + 1];
	for (int i = 0; i < colKeys.length; i++) {
	    newCKeys[i] = colKeys[i];
	}
	newCKeys[index] = key;
	colKeys=newCKeys;
	
    };

    public void setElementAt(String rowKey, String colKey, Object value, Object def) {
	if (rowKey != null && colKey != null) {
	    updateRowKeysIndex(rowKey);
	    updateColKeysIndex(colKey);

	    int indeR = getRowIndex(rowKey);
	    int indeC = getColIndex(colKey);

	    setElementAt(indeR, indeC, value, def);
	}
    }

    public void setElementAt(int row, int col, Object value, Object def) {
	if (row <= 0 || col <= 0)
	    return;
	List<Object> rowData = null;
	if (row > data.size()) {
	    int bound = data.size();
	    for (int i = 0; i < row - bound; i++) {
		int actCols=getCols();
		int cols=col>actCols?col:actCols;
		rowData = new ArrayList<Object>(cols);
		data.add(rowData);
		for (int j = 0; j < cols; j++) {
		    rowData.add(def);
		}
		if(col>actCols){
		  //Normalizzazione dati già presenti nel datamodel
		    for (int j = 0; j < data.size(); j++) {
			rowData = (List<Object>) data.get(j);
			while(rowData.size()<col)
			    rowData.add(def);
			}   
		}
		
	    }
	    rowData.set(col - 1, value);
	    return;
	}
	rowData = (List<Object>) data.get(row - 1);
	if (col > rowData.size()) {
	    int bound = rowData.size();
	    for (int i = 0; i < col - bound; i++) {
		rowData.add(def);
	    }
	    rowData.set(col - 1, value);
	    
	    //Normalizzazione dati già presenti nel datamodel
	    for (int i = 0; i < data.size(); i++) {
		rowData = (List<Object>) data.get(i);
		while(rowData.size()<col)
		    rowData.add(def);
		}
	    return;
	}
	rowData.set(col - 1, value);
    }

    public void addToElementAt(String rowKey, String colKey, Object value) {
	int indeR = getRowIndex(rowKey);
	int indeC = getColIndex(colKey);
	if (indeR == -1 || indeC == -1) {
	    return;
	}
	List<Object> rowData = null;
	// La riga non esiste
	if (indeR > data.size()) {
	    int bound = data.size();
	    int colNum=getCols();
	    for (int i = 0; i < indeR - bound; i++) {
		rowData = new ArrayList(colNum);
		data.add(rowData);
		for (int j = 0; j < colNum; j++) {
		    rowData.add(null);
		}
	    }

	    rowData.set(indeC - 1, value);
	    return;
	}

	// La riga esiste
	rowData = data.get(indeR - 1);

	if (indeC > rowData.size()) {
	    int bound = rowData.size();
	    for (int i = 0; i < indeC - bound; i++) {
		rowData.add(null);
	    }

	    rowData.set(indeC - 1, value);
	    return;
	}
	Object oldValue = rowData.get(indeC - 1);
	if (oldValue != null) {
	    if (oldValue instanceof Number) {
		if (oldValue instanceof Integer) {
		    rowData.set(indeC - 1, ((Number) value).intValue() + ((Integer) oldValue).intValue());
		    return;
		}
		if (oldValue instanceof Long) {
		    rowData.set(indeC - 1, ((Number) value).longValue() + ((Long) oldValue).longValue());
		    return;
		}
		if (oldValue instanceof Double) {
		    rowData.set(indeC - 1, ((Number) value).doubleValue() + ((Double) oldValue).doubleValue());
		    return;
		}
		if (oldValue instanceof Float) {
		    rowData.set(indeC - 1, ((Number) value).floatValue() + ((Float) oldValue).floatValue());
		    return;
		}
	    }
	    if (oldValue instanceof String) {
		rowData.set(indeC - 1, value.toString() + oldValue.toString());
		return;
	    }
	}
	rowData.set(indeC - 1, value);
    }

    public int getRowIndex(String key) {
	if (key == null) {
	    return -1;
	}
	Integer index=rowKeyIndex.get(key);	
	if(index==null) return -1;
	return index;
    }
    
    public int getColIndex(String key) {
	if (key == null) {
	    return -1;
	}
	Integer index=colKeyIndex.get(key);	
	if(index==null) return -1;
	return index;
    }
    

    public int getRows() {
	if (data != null) {
	    return data.size();
	}
	return 0;
    }

    public int getCols() {
	int tmpCol = 0;
	if (data != null) {
	    Iterator rows = data.iterator();
	    while (rows.hasNext()) {
		Object dataRow = rows.next();
		int size = 0;
		if (dataRow instanceof List) {
		    size = ((List) dataRow).size();
		}
		tmpCol = tmpCol > size ? tmpCol : size;
	    }
	}
	return tmpCol;
    }

    public void initWith(Object value) {
	String[] rowKeys = getRowKeys();
	String[] colKeys = getColKeys();
	if (rowKeys != null && colKeys != null) {
	    for (int i = 1; i <= rowKeys.length; i++) {
		for (int j = 1; j <= colKeys.length; j++) {
		    setElementAt(i, j, value, value);
		}
	    }
	}
    }

    public BigDecimal sumAll() {
	BigDecimal result = BigDecimal.ZERO;
	BigDecimal toAdd;
	for (int i = 1; i <= data.size(); i++) {
	    result = result.add(sumRow(i));
	}
	return result;
    }

    public BigDecimal sumRow(int row) {
	return sumRow(row, -1);
    }
    
    public BigDecimal sumRow(String key) {
	int index = getRowIndex(key);
	return sumRow(index, -1);
    }
    
    public BigDecimal sumRow(int row,int col) {
	if (row < 1 || row > data.size())
	    return null;
	List rowData = data.get(row - 1);
	if(col==0)
	    return BigDecimal.ZERO;
	if(col==-1 || col >rowData.size())
	    col=rowData.size();
	BigDecimal result = BigDecimal.ZERO;
	for (int i = 0; i < col; i++) {
	    result = sumObject(result, rowData.get(i));
	}
	return result;
    }

    public BigDecimal sumCol(int col) {
	return sumCol(col, -1);
    }
    
    public BigDecimal sumCol(String key) {
	int index = getColIndex(key);
	return sumCol(index, -1);
    }
    
    public BigDecimal sumCol(int col,int row) {
	if (col < 1)
	    return null;
	if(row==0)
	    return BigDecimal.ZERO;
	if(row==-1)
	    row=data.size();
	if(row>data.size())
	    row=data.size();
	BigDecimal result = BigDecimal.ZERO;
	for (int i = 1; i <= row; i++) {
	    Object object = getElementAt(i, col);
	    result = sumObject(result, object);
	}
	return result;
    }

    private BigDecimal sumObject(BigDecimal base, Object object) {
	BigDecimal result = base;
	BigDecimal toAdd;
	if (object instanceof BigDecimal) {
	    toAdd = (BigDecimal) object;
	    result = result.add(toAdd);
	}
	if (object instanceof Double) {
	    toAdd = new BigDecimal((Double) object);
	    result = result.add(toAdd);
	}
	if (object instanceof Integer) {
	    toAdd = new BigDecimal((Integer) object);
	    result = result.add(toAdd);
	}
	if (object instanceof Float) {
	    toAdd = new BigDecimal((Float) object);
	    result = result.add(toAdd);
	}
	if (object instanceof Short) {
	    toAdd = new BigDecimal((Short) object);
	    result = result.add(toAdd);
	}
	if (object instanceof Long) {
	    toAdd = new BigDecimal((Long) object);
	    result = result.add(toAdd);
	}
	return result;
    }

    public BigDecimal getAverageRow(int row) {
	if (getCols() != 0) {
	    return sumRow(row).divide(new BigDecimal(getCols()), 2, BigDecimal.ROUND_CEILING);
	}
	return null;
    }

    public BigDecimal getAverageCol(int col) {
	BigDecimal result = BigDecimal.ZERO;
	if (getRows() != 0) {
	    try {
		result = sumCol(col).divide(new BigDecimal(getRows()), 2, BigDecimal.ROUND_CEILING);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return result;
    }

    public String getStringRowKeys() {
	return StringUtil.ArrayToString(getRowKeys(), ",", true);
    }

    public String getStringColKeys() {
	return StringUtil.ArrayToString(getColKeys(), ",", true);
    }

    public String getStringColTitles() {
	return StringUtil.ArrayToString(getColTitles(), ",", true);
    }

    public String getStringRowTitles() {
	return StringUtil.ArrayToString(getRowTitles(), ",", true);
    }

    public String getStringDataRowValue(int index) {
	if (index < 1 || index > data.size())
	    return null;
	;
	List rowData = data.get(index - 1);
	return StringUtil.ArrayToString(rowData, ",", false);
    }

    public String getStringDataColValue(int index, String def) {
	if (index < 1)
	    return null;
	StringBuffer res = new StringBuffer();
	for (int i = 1; i <= data.size(); i++) {
	    Object object = getElementAt(i, index);
	    if (object == null) {
		res.append(def);
	    } else {
		res.append(object);
	    }
	    if (i < (data.size())) {
		res.append(",");
	    }
	}
	return res.toString();
    }

    // Gestione info

    public Object getInfo(String name) {
	return info.get(name);
    }

    public void addInfo(String name, Object obj) {
	info.put(name, obj);
    }

    public List getRow(String key) {
	if (key == null)
	    return null;
	int indeR = getRowIndex(key);

	if (indeR != -1) {
	    return data.get(indeR - 1);
	}
	return null;
    }

    public List getRow(int index) {
	if (index < 1)
	    return null;
	if (index <= data.size()) {
	    return data.get(index - 1);
	}
	return null;
    }

    public void addRowAttribute(String rowKey, String key, Object value) {

	int indeR = getRowIndex(rowKey);

	if (indeR == -1) {
	    return;
	}

	HashMap<String, Object> rowAtt = rowAttributes.get(rowKey);

	if (rowAtt == null) {
	    rowAtt = new HashMap<String, Object>();
	}

	rowAtt.put(key, value);

	rowAttributes.put(rowKey, rowAtt);
    }
    
    public Object getRowAttribute(String rowKey, String key){
	int indeR = getRowIndex(rowKey);

	if (indeR == -1) {
	    return null;
	}
	
	HashMap<String, Object> rowAtt = rowAttributes.get(rowKey);

	if (rowAtt == null) {
	    return null;
	}
	
	return rowAtt.get(key);
	
    }

    public void addColAttribute(String colKey, String key, Object value) {
	int indeC = getColIndex(colKey);

	if (indeC == -1) {
	    return;
	}

	HashMap<String, Object> collAtt = colAttributes.get(colKey);

	if (collAtt == null) {
	    collAtt = new HashMap<String, Object>();
	}

	collAtt.put(key, value);

	colAttributes.put(colKey, collAtt);
    }
    
    
    public Object getColAttribute(String colKey, String key) {
   	
   	int indeC = getColIndex(colKey);

   	if (indeC == -1) {
   	    return null;
   	}

   	HashMap<String, Object> collAtt = colAttributes.get(colKey);

   	if (collAtt == null) {
   	    return null;
   	}

   	return collAtt.get(key);

       }
    
    @Override
    public String toString() {
	return (String) toXmlObject().describe();
    }

    public static void main(String[] args) {
	String[] cols = new String[] { "uno", "due", "tre" };
	String[] rows = new String[] {"uno", "due", "tre"};
	DataModel data = new DataModel();

	System.out.println(data);
	
	data.addToElementAt("uno", "due", 4);

	//data.setColTitles(cols);
	//data.setRowTitles(rows);
	// data.initWith(3);
	System.out.println(data);
	
	data.setElementAt("uno", "uno", "1", 0);
	data.setElementAt("due", "due", "1", 0);
	data.setElementAt("tre", "tre", "1", 0);
	data.setElementAt("quattro", "quattro", "1", 0);
	data.setElementAt("cinque", "cinque", "1", 0);
	data.setElementAt("sei,sette", "sei,sette", "1", 0);
	
	data.addToElementAt("due", "due", 4);

	System.out.println(data.getElementAt(1, 2));

	data.addColAttribute("uno", "prova1", "vediamo1");

	data.addColAttribute("uno", "prova2", "vediamo2");

	data.addColAttribute("due", "prova2", "vediamo2");

	data.addColAttribute("tre", "prova2", "vediamo2");
	
	data.addRowAttribute("uno", "prova2", "vediamo2");

	data.addRowAttribute("due", "prova2", "vediamo2");

	System.out.println(data);

	System.out.println("Somma di tutti i valori: "+data.sumAll());
	
	System.out.println("Attributo 'prova2' della row 'uno': "+data.getRowAttribute("uno", "prova2"));

	System.out.println(data.getStringColKeys());
	System.out.println(data.getStringColTitles());
	System.out.println(data.getStringRowKeys());
	System.out.println(data.getStringRowTitles());
	System.out.println(data.getStringDataRowValue(1));
	System.out.println(data.getStringDataRowValue(2));
	System.out.println(data.getStringDataRowValue(3));
	System.out.println(data.getStringDataRowValue(4));
	System.out.println(data.getStringDataRowValue(5));
	System.out.println(data.getStringDataRowValue(6));
	
	System.out.println("Somma la prima righa: "+data.sumRow("uno"));
	System.out.println("Somma la seconda righa: "+data.sumRow("due"));
	System.out.println("Somma la terza righa: "+data.sumRow("tre"));
	System.out.println("Somma la quarta righa: "+data.sumRow("quattro"));
	

    }
}

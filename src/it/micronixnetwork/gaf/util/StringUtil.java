/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author kobo
 */
public class StringUtil {

    /**
     * Rimuove gli spazi a sinistra
     * 
     * @param source
     * @return
     */

    public static String ltrim(String source) {
	return source.replaceAll("^\\s+", "");
    }

    /**
     * Rimuove gli spazi a destra
     * 
     * @param source
     * @return
     */
    public static String rtrim(String source) {
	return source.replaceAll("\\s+$", "");
    }

    /**
     * Rimuove gli spazi multipli sostituendoli con un singlo spazio
     * 
     * @param source
     * @return
     */
    public static String itrim(String source) {
	return source.replaceAll("\\b\\s{2,}\\b", " ");
    }

    /**
     * Rimuove tutti gli spazi superflui in una stringa
     * 
     * @param source
     * @return
     */
    public static String trim(String source) {
	return itrim(ltrim(rtrim(source)));
    }

    /**
     * Rimuove i ritorni a capo da una stringa
     */
    public static String nltrim(String source) {
	return source.replaceAll("\\n", "");
    }

    /**
     * Rimuove gli spazi superflui e i ritoni a capo da una stringa
     */
    public static String trimn(String source) {
	return trim(nltrim(source));
    }

    /**
     * Converte una lista di stringhe in una striga di valori separati da
     * separator
     * 
     * @param list
     *            la lista di stringhe
     * @param separator
     *            il separatore da utilizzare
     * @return
     */
    public static String ArrayToString(String[] list, String separator, boolean str) {
	if (separator == null)
	    separator = ",";
	if (list == null)
	    return "";
	if (list.length == 0)
	    return "";
	StringBuffer res = new StringBuffer();
	for (int i = 0; i < list.length; i++) {
	    if (str)
		res.append("'");
	    res.append(list[i]);
	    if (str)
		res.append("'");
	    if (i < (list.length - 1)) {
		res.append(separator);
	    }
	}
	return res.toString();
    }

    /**
     * Converte una lista di stringhe in una striga di valori separati da
     * separator
     * 
     * @param list
     *            la lista di stringhe
     * @param separator
     *            il separatore da utilizzare
     * @return
     */
    public static String ArrayToString(List list, String separator, boolean str) {
	if (separator == null)
	    separator = ",";
	if (list == null)
	    return "";
	if (list.size() == 0)
	    return "";
	StringBuffer res = new StringBuffer();
	for (int i = 0; i < list.size(); i++) {
	    if (str)
		res.append("'");
	    res.append(list.get(i));
	    if (str)
		res.append("'");
	    if (i < (list.size() - 1)) {
		res.append(separator);
	    }
	}
	return res.toString();
    }

    /**
     * Controlla se una stringa è null o vuota
     * 
     * @param s
     */
    public static boolean EmptyOrNull(String s) {
	return (s == null || s.trim().equals(""));
    }

    public static String patternComposer(Object[] values, Object[] p_values, String pattern, String p_prefix) {
	if (values == null && p_values == null)
	    return pattern;
	if (values != null && values.length == 0 && p_values != null && p_values.length == 0)
	    return pattern;
	if (pattern == null)
	    return null;

	String result = pattern;

	if (values != null) {
	    for (int i = 0; i < values.length; i++) {
		if (values[i] != null) {
		    result = result.replaceAll("\\{" + (i + 1) + "\\}", values[i].toString());
		}
	    }
	}

	if (p_values != null) {
	    for (int i = 0; i < p_values.length; i++) {
		if (p_values[i] != null) {
		    result = result.replaceAll("\\{" + p_prefix + (i + 1) + "\\}", p_values[i].toString());
		}
	    }
	}

	return result;
    }

    public static List<String> patternComposer(List<Object[]> l_values, Object[] p_values, List<String> patterns, String p_prefix) {
	if (l_values == null && p_values == null)
	    return patterns;
	if (l_values.size() == 0 && p_values.length == 0)
	    return patterns;
	if (patterns == null)
	    return null;

	List<String> result = new ArrayList<String>();

	for (int j = 0; j < patterns.size(); j++) {

	    String pattern = patterns.get(j);

	    if (j < l_values.size()) {
		Object[] values = l_values.get(j);
		for (int i = 0; i < values.length; i++) {
		    if (values[i] != null) {
			pattern = pattern.replaceAll("\\{" + (i + 1) + "\\}", values[i].toString());
		    }
		}
	    }

	    for (int i = 0; i < p_values.length; i++) {
		if (p_values[i] != null) {
		    pattern = pattern.replaceAll("\\{" + p_prefix + (i + 1) + "\\}", p_values[i].toString());
		}
	    }
	    result.add(pattern);

	}

	return result;
    }

    /**
     * Ritorna la stringa o il default se nulla
     * @param value la stringa da controllare
     * @param _default il valore restituito de value è null
     * @return la stringa o il default
     */
    public static final String valueOrDefault(String value, String _default) {

	return value == null ? _default : value;

    }

    public static void main(String[] args) {
	String[] values1 = new String[] { "123", "pio", "trullo" };
	String[] values2 = new String[] { "1968", "2001", "2010" };

	List<String> patterns = new ArrayList<String>();

	List<Object[]> values = new ArrayList<Object[]>();

	// values.add(values1);

	// values.add(values2);

	patterns.add("{2} {1} dai cazzo! {p2}");

	patterns.add("{2} {1} dai cazzo! {p1}");

	String[] p_values = new String[] { "Bau", "Bio", "Bua" };

	String pattern = "{2} {1} dai cazzo! {p2}";

	List<String> result = patternComposer(values, p_values, patterns, "p");

	for (String string : result) {
	    System.out.println(string);
	}
    }

    /**
     * Splitta una stringa di valori separati da virgola ',' in una lista si
     * stringhe
     * 
     * @param val
     *            la stringa di elementi separati da virgole ','
     * @return la lista degli elementi
     */
    public static List<String> stringToList(String val) {
	return stringToList(val,",");
    }
    
    
    /**
     * Splitta una stringa di valori separati da un separatore in
     * stringhe
     * @param val la stringa di elementi separati da sep
     * @param sep il separatore
     * @return la lista degli elementi
     */
    public static List<String> stringToList(String val,String sep) {
	if (val != null) {
	    if (val.trim().length() == 0) {
		return new ArrayList<String>();
	    }
	    String[] list = val.split("[ ]*"+sep+"[ ]*");
	    return new ArrayList<String>(Arrays.asList(list));
	} else {
	    return new ArrayList<String>();
	}
    }
    
    /**
     * Controlla che almeno una stringa in values sia presente in toChek se toCheck è vuota
     * si considare valido il controllo
     * @param values i valori di partenza
     * @param toCheck i valori da controllare
     * @return
     */
    public static boolean checkStringExistenz(String[] values, List<String> toCheck) {
	if (values == null || values.length == 0)
	    return false;
	if (toCheck == null || toCheck.size() == 0)
	    return true;
	for (String value : values) {
	    if (toCheck.contains(value))
		return true;
	}
	return false;
    }
    
    
    /**
     * Controlla che almeno una stringa in values sia presente in toChek se toCheck è vuota
     * si considare valido il controllo
     * @param values i valori di partenza
     * @param toCheck i valori da controllare in formato "Valore1,Valore2,.."
     * @return
     */
    public static boolean checkStringExistenz(String[] values, String toCheck) {
	List<String> toCheck_l=StringUtil.stringToList(toCheck);
	return checkStringExistenz(values, toCheck_l);
    }
    
    /**
     * Controlla che almeno una stringa in values sia presente in toChek se toCheck è vuota
     * si considare valido il controllo
     * @param values i valori di partenza
     * @param toCheck i valori da controllare
     * @return
     */
    public static boolean checkStringExistenz(String[] values, String[] toCheck) {
	if(toCheck==null || toCheck.length==0){
	    return checkStringExistenz(values, (List<String>)null);
	}
	return checkStringExistenz(values,Arrays.asList(toCheck));
    }

}

package it.micronixnetwork.gaf.util;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Iterator che data una stringa in ingresso e una espressione regolare permette di estrarre tutto ciò che matcha
 * con l'espressione.
 * @author kobo
 */

public class REXTokenizer implements Iterator {
   
    private final CharSequence input;

    private final boolean returnDelims;
   
    private Matcher matcher;
 
    private String delim;
   
    private String match;
   
    private int lastEnd = 0;


    /**
     * @param input testo da parsare
     * @param patternStr espressione regolare da applicare all'input
     * @param returnDelims  flag che specifica se prendere anche il tasto a destra e sinistra del match
     */
    public REXTokenizer(CharSequence input, String patternStr, boolean returnDelims) {
        this.input = input;
        this.returnDelims = returnDelims;
        Pattern pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher(input);
    }

    /**
     * Controlla se l'iteratore ha ancora qualche cosa su cui iterare
     * @return
     */
    public boolean hasNext() {
        if (matcher == null) {
            return false;
        }
        if (delim != null || match != null) {
            return true;
        }
        if (matcher.find()) {
            if (returnDelims) {
                delim = input.subSequence(lastEnd, matcher.start()).toString();
            }
            match = matcher.group();
            lastEnd = matcher.end();
        } else if (returnDelims && lastEnd < input.length()) {
            delim = input.subSequence(lastEnd, input.length()).toString();
            lastEnd = input.length();
            matcher = null;
        }
        return delim != null || match != null;
    }

    /**
     * Il token o i delimitatore successivo
     * @return generalmente il mach successivo, il delimitatatore se siamo all'inizio o alla fine del token e il tokenizzatore
     * è stato creato con flag a true
     */
    public Object next() {
        String result = null;

        if (delim != null) {
            result = delim;
            delim = null;
        } else if (match != null) {
            result = match;
            match = null;
        }
        return result;
    }

    /**
     * Identifica il token
     * @return true se è un token false se è un delimitatore
     */
    public boolean isNextToken() {
        return delim == null && match != null;
    }

    /**
     * Non implementato
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
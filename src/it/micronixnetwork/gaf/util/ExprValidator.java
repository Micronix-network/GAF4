package it.micronixnetwork.gaf.util;

import java.sql.Time;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Collezzione di metodi statici per la validazione di campi stringa
 * @author a.riboldi
 */
public class ExprValidator {

    public static String EMAIL_VALIDATION = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-\\.])+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|(\\.arpa)|(\\.coop)|(\\.int)|(\\.jobs)|(\\.museum)|(\\.name)|(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,3})|(\\..{2,3}\\..{2,3}))$)\\b";
    public static String YEAR_VALIDATION = "^[1-9][0-9][0-9][0-9]$";
    public static String PWD_VALIDATION = "^[A-z,0-9]{4,11}$";
    public static String PHONE_VALIDATION = "^[0-9,\\-,/]{6,20}$";
    public static String INTEGER_VALIDATION = "([+-][0-9]+)|([0-9]+)";
    public static String TEXT_VALIDATION = "([A-Za-z,\\s]+)";
    
    //Ora implementate via algoritmo
    //public static String CODFISCALE_VALIDATION = "[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]";
    //public static String PARTITAIVA_VALIDATION = "[0-9]{11}";


    /**
     * Valida il toString di un oggetto controllando il match con l'espressione reogolare passata
     * @param value l'oggetto da validare
     * @param regexp l'espressione regolare per il match
     * @return true se il match è positivo false altrimenti
     */
    public static boolean valid(Object value, String regexp) {
        if (value == null || value.toString().trim().equals("")) {
            return true;
        }
        String s_value = value.toString();
        return s_value.matches(regexp);
    }


    /**
     * Valida la correttezza sintattica di un email
     * @param email l'email
     * @return true se corretta false altrimenti
     */
    public static boolean validEmail(String email) {
        if (validVoid(email)) {
            return true;
        }
        return email.matches(EMAIL_VALIDATION);
    }

    /**
     * Controlla se un anno è valido 1000>=year<=9999
     * @param year l'anno da valutare
     * @return
     */
    public static boolean validYear(String year) {
        if (validVoid(year)) {
            return true;
        }
        return year.matches(YEAR_VALIDATION);
    }

    /**
     * Controlla la validità sintattica di un numero telefonico minimo 6 caratteri max 20 composto
     * da solo numeri o i caratteri /-, senza spazi
     * @param phone
     * @return
     */
    public static boolean validPhone(String phone) {
        if (validVoid(phone)) {
            return true;
        }
        return phone.matches(PHONE_VALIDATION);
    }

    public static boolean validInteger(String test) {
        if (validVoid(test)) {
            return true;
        }
        try{
            Integer.parseInt(test);
        }catch (Exception e) {
	   return false;
	}
        return true;
    }
    
    public static boolean validFloat(String test) {
        if (validVoid(test)) {
            return true;
        }
        
        try{
            Float.parseFloat(test);
        }catch (Exception e) {
	   return false;
	}
        return true;
    }
    
    public static boolean validDouble(String test) {
        if (validVoid(test)) {
            return true;
        }
        
        try{
            Double.parseDouble(test);
        }catch (Exception e) {
	   return false;
	}
        return true;
    }
    
    
    public static boolean validLong(String test) {
        if (validVoid(test)) {
            return true;
        }
        try{
            Long.parseLong(test);
        }catch (Exception e) {
	   return false;
	}
        return true;
    } 

    public static boolean validText(String test) {
        if (validVoid(test)) {
            return true;
        }
        return test.matches(TEXT_VALIDATION);
    }
    
    public static boolean validDate(String test,String pattern) {
        if (validVoid(test)) {
            return true;
        }
        
        try {
	    DateUtil.parseDate(test, pattern);
	} catch (ParseException e) {
	   return false;
	}
        return true;
    }
    
    public static boolean validTime(String test,String pattern) {
        if (validVoid(test)) {
            return true;
        }
        try {
	    DateUtil.parseDate(test, pattern);
	} catch (ParseException e) {
	   return false;
	}
        return true;
    }
    

    public static boolean validVoid(Object obj) {
        if (obj == null || obj.toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean validRetryPwd(String pwd, String retryPwd) {
        if (pwd.equals(retryPwd)) {
            return true;
        }
        return false;
    }

    public static boolean validLength(String test, int length) {
        if (validVoid(test)) {
            return true;
        }
        if (test.length() <= length) {
            return true;
        }
        return false;
    }

    public static boolean validCodFiscale(String cf) {
        if (validVoid(cf)) {
            return true;
        }
        int i, s, c;
        String cf2;
        int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
            11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
        if (cf.length() == 0) {
            return false;
        }
        if (cf.length() != 16) {
            return false;
        }
        cf2 = cf.toUpperCase();
        for (i = 0; i < 16; i++) {
            c = cf2.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
                return false;
            }
        }
        s = 0;
        for (i = 1; i <= 13; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                s = s + c - '0';
            } else {
                s = s + c - 'A';
            }
        }
        for (i = 0; i <= 14; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                c = c - '0' + 'A';
            }
            s = s + setdisp[c - 'A'];
        }
        if (s % 26 + 'A' != cf2.charAt(15)) {
            return false;
        }
        return true;
    }

    public static boolean validPartitaIva(String pi) {
        if (validVoid(pi)) {
            return true;
        }
        int i, c, s;
        if (pi.length() != 11) {
            return false;
        }
        for (i = 0; i < 11; i++) {
            if (pi.charAt(i) < '0' || pi.charAt(i) > '9') {
                return false;
            }
        }
        s = 0;
        for (i = 0; i <= 9; i += 2) {
            s += pi.charAt(i) - '0';
        }
        for (i = 1; i <= 9; i += 2) {
            c = 2 * (pi.charAt(i) - '0');
            if (c > 9) {
                c = c - 9;
            }
            s += c;
        }
        if ((10 - s % 10) % 10 != pi.charAt(10) - '0') {
            return false;
        }
        return true;
    }
    
    
    
}

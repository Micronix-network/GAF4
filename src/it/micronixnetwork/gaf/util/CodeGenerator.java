/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.util;

import java.util.Random;

/**
 * 
 * @author kobo
 */
public class CodeGenerator {

    private static String salt = "micronixnetwork";

    /**
     * Genera un codice univoco testuale
     * 
     * @return il codice
     */
    public static String createCode() {
	Random random = new Random();
	long r = Math.abs(random.nextLong());
	String code1 = (Long.toHexString(r)).toUpperCase();
	r = Math.abs(random.nextLong());
	String code2 = (Long.toHexString(r)).toUpperCase();
	code1 = code1.length() < 16 ? "0" + code1 : code1;
	code2 = code2.length() < 16 ? "0" + code2 : code2;
	return code1 + code2;
    }
    
    public static String createMediumCode() {
	Random random = new Random();
	int r = Math.abs(random.nextInt());
	String code1 = (Integer.toHexString(r)).toUpperCase();
	r = Math.abs(random.nextInt());
	String code2 = (Integer.toHexString(r)).toUpperCase();
	code1 = code1.length() < 8 ? "0" + code1 : code1;
	code2 = code2.length() < 8 ? "0" + code2 : code2;
	return code1 + code2;
    }
    
    public static String createSmallCode() {
	Random random = new Random();
	int r = Math.abs(random.nextInt());
	String code1 = (Integer.toHexString(r)).toUpperCase();
	code1 = code1.length() < 8 ? "0" + code1 : code1;
	return code1;
    }

    public static String crypt(String value) {
	return Jcrypt.crypt(salt, value);
    }

    public static void main(String[] args) {
	for (int i = 0; i < 10; i++) {
	    System.out.println(createSmallCode());
	}
    }

}

package it.micronixnetwork.gaf.util;

import java.util.Calendar;
import java.util.Date;

public class Aziend {
    
    /**
     * Ritorna la data odierna in formato Aziend
     * @return
     */
    public static Integer getToDay(){
	Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        int y=cal.get(Calendar.YEAR);
        int m=cal.get(Calendar.MONTH)+1;
        int d=cal.get(Calendar.DATE);
        return ((y-1950)*10000)+(m*100)+d;
    }
    
    
    /**
     * Ritorna una java.util.date da una data Aziend
     */
    public static Date convertToDate(Integer azdate){
	Calendar cal=Calendar.getInstance();
	if(azdate==null) return null;
	String s_azdate=azdate.toString();
	String y=s_azdate.substring(0,2);
	String m=s_azdate.substring(2,4);
	String d=s_azdate.substring(4);
	cal.set(Calendar.YEAR, Integer.parseInt(y)+1950);
	cal.set(Calendar.MONTH,Integer.parseInt(m)-1);
	cal.set(Calendar.DATE,Integer.parseInt(d));
	return cal.getTime();
    }
    
    public static void main(String[] args) {
	System.out.println(getToDay());
	
	System.out.println(convertToDate(610123));
    }

}

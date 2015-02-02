/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.micronixnetwork.gaf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author a.riboldi
 */
public class DateUtil {

    public static Date getDayDifference(Date date,int day){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    
    public static Date getMonthDifference(Date date,int month){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date parseDate(String dateStr, String datePattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        Date d = sdf.parse(dateStr);
        return d;
    }
    
    public static String formatDate(Date date, String datePattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        String dataStr = sdf.format(date);
        return dataStr;
    }

    public static int getDay() {
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DATE);
    }

    public static int getMonth() {
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.MONTH)+1;
    }

    public static int getYear() {
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.YEAR);
    }


    public static ArrayList<Date> getIntervalDays(Date start,Date end){
    	ArrayList<Date> result=new ArrayList<Date>();
    	result.add(start);
    	Date tempDate=start;
    	while(tempDate.before(end)){
    		tempDate=getDayDifference(tempDate, 1);
    		result.add(tempDate);
    	}
    	return result;
    }
    
    public static List<Date> getIntervalDays(Date day1,Date day2,Integer weekDay){
		ArrayList<Date> result=new ArrayList<Date>();
		Calendar cal=Calendar.getInstance();
		cal.setTime(day1);
		while (cal.getTime().compareTo(day2)<=0) {
			if(cal.get(Calendar.DAY_OF_WEEK)==weekDay){
				result.add(cal.getTime());
				}
				cal.add(Calendar.DATE, 1);
			}	
		return result;
	}
    
    public static List<Integer> getMonthWeeks(Integer month,Integer year){
		ArrayList<Integer> result=new ArrayList<Integer>();
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.MONTH,month-1);
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int lastDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar cal2=Calendar.getInstance();
		cal2.set(Calendar.MONTH,month-1);
		cal2.set(Calendar.YEAR,year);
		cal2.set(Calendar.DAY_OF_MONTH,lastDay);
		
		while (cal.getTime().compareTo(cal2.getTime())<=0) {
			result.add(cal.get(Calendar.WEEK_OF_YEAR));
			cal.add(Calendar.DATE, 7);
		}
		return result;
	}
    
    
    public static String getStringHour(Integer h){
    	if(h<0 || h>23) return null;
    	if (h < 10) {
			return "0" + h;
		} else {
			return ""+h;
		}
    }
    
    public static void main(String[] args) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	try {
			getMonthWeeks(4,2011);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
	}
}

package it.micronixnetwork.gaf.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class NumberUtil {
	
	public static String formatReal(Object num, String pattern,char dec_sep, char digit_sep){
		DecimalFormat formatter = new DecimalFormat(pattern);
		DecimalFormatSymbols symb=formatter.getDecimalFormatSymbols();
		symb.setDecimalSeparator(dec_sep);
		symb.setGroupingSeparator(digit_sep);
		formatter.setDecimalFormatSymbols(symb);
		return formatter.format(num);
	};
	
	public static void main(String[] args) {
		System.out.println(123.3458);
	 System.out.println(formatReal(123455678123.3458, "###,###.#####",',','.'));
	}

}

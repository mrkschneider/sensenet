package com.tcb.sensenet.internal.util;

public class DoubleUtil {
	public static double replaceNaN(double x, double replacement){
		if(Double.isNaN(x)) return replacement;
		else return x;
	}
}

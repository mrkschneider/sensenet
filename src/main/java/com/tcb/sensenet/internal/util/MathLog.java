package com.tcb.sensenet.internal.util;

public class MathLog {

	private static final double log2 = Math.log(2);
	
	public static double log(double x) {
		return Math.log(x);
	}
	
	public static double log2(double x) {
		return Math.log(x) / log2;
	}
}

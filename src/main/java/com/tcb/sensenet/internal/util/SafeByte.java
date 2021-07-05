package com.tcb.sensenet.internal.util;

public class SafeByte {
		
	public static Byte byteValue(int a){
		checkBoundaries(a);
		return (byte) a;
	}
	
	private static void checkBoundaries(int a){
		if(a < Byte.MIN_VALUE || a > Byte.MAX_VALUE)
			throw new RuntimeException("Byte array overflow");
	}
	
	
}

package com.tcb.sensenet.internal.util;

public class ArrayUtils {
	public static int countValues(int value, int[] arr){
		int count = 0;
		for(int i=0;i<arr.length;i++){
			if(value==arr[i]){
				count++;
			}
		}
		return count;
	}
	
	public static double[] toDoubles(float[] arr){
		final int size = arr.length;
		double[] result = new double[size];
		for(int i=0;i<size;i++){
			result[i] = (double)arr[i];
		}
		return result;
	}
}

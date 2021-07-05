package com.tcb.sensenet.internal.util;

import java.util.Random;

public class ArrayShuffler {
	
	private Random rand;
	
	public ArrayShuffler(Random rand){
		this.rand = rand;
	}
	
	public void shuffle(int[] arr){
		/* Fisher-Yates shuffle implementation (Wikipedia)
			-- To shuffle an array a of n elements (indices 0..n-1):
		for i from n−1 downto 1 do
     		j ← random integer such that 0 ≤ j ≤ i
     		exchange a[j] and a[i]
		 */
		final int n = arr.length;
		for(int i=n-1;i>=1;i--){
			int j = rand.nextInt(i+1);
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}
	}
}

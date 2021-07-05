package util;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.ArrayShuffler;

import cern.colt.Arrays;

public class ArrayShufflerTest {

	private ArrayShuffler shuffler;
	private int[] arr;

	@Before
	public void setUp() throws Exception {
		this.shuffler = new ArrayShuffler(new Random(12345));
		this.arr = new int[]{1,2,3,4,5};
	}

	@Test
	public void testShuffle() {
		
		int[][] ref = new int[][]{
			{4, 5, 1, 3, 2},
			{1, 3, 5, 2, 4},
			{2, 4, 5, 1, 3},
			{4, 3, 5, 1, 2},
			{3, 4, 2, 1, 5}
		};
		for(int i=0;i<5;i++){
			shuffler.shuffle(arr);
			assertArrayEquals(ref[i],arr);
		}
	}

}

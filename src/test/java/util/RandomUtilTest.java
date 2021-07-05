package util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.RandomUtil;

public class RandomUtilTest {

	private List<Integer> lst;
	private RandomUtil rnd;



	@Before
	public void setUp() throws Exception {
		this.lst = Arrays.asList(0,1,2,3,4);
		this.rnd = new RandomUtil();
		rnd.setSeed(512l);
	}

	@Test
	public void testPickRandom() {
		int[] results = new int[5];
		int[] ref = new int[]{9903,10116,9861,10020,10100};
		for(int i=0;i<50000;i++){
			Integer test = rnd.pickRandom(lst);
			results[test]+=1;
		}
		
		for(int i=0;i<ref.length;i++){
			assertEquals(ref[i],results[i]);
		}
	}
	
	@Test
	public void testPickRandomWeighted() {
		int[] results = new int[5];
		int[] ref = new int[]{9031, 18086, 9152, 4546, 9185};
		for(int i=0;i<50000;i++){
			Integer test = rnd.pickRandom(lst, Arrays.asList(1.,2.,1.,.5,1.));
			results[test]+=1;
		}

		for(int i=0;i<ref.length;i++){
			assertEquals(ref[i],results[i]);
		}
	}

}

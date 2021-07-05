package util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.util.SieveUtil;

public class SieveUtilTest {

	private List<Double> lst;

	@Before
	public void setUp() throws Exception {
		this.lst = Arrays.asList(
				1.,2.,3.,4.,5.,6.,7.,8.,9.,10.);
	}

	@Test
	public void testSieveStep1() {
		List<Double> sieved = SieveUtil.sieve(lst, 1);
		assertEquals(lst,sieved);
	}
	
	@Test
	public void testSieveStep2() {
		List<Double> ref = Arrays.asList(
				1.,3.,5.,7.,9.);
		List<Double> sieved = SieveUtil.sieve(lst, 2);
		assertEquals(ref,sieved);
	}
	
	@Test
	public void testSieveStep5() {
		List<Double> ref = Arrays.asList(
				1.,6.);
		List<Double> sieved = SieveUtil.sieve(lst, 5);
		assertEquals(ref,sieved);
	}
	
	@Test
	public void testSieveStepBiggerThanList() {
		List<Double> ref = Arrays.asList(
				1.);
		List<Double> sieved = SieveUtil.sieve(lst, 100);
		assertEquals(ref,sieved);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeSieveStepIllegal() {
		SieveUtil.sieve(lst, -1);
	}

	@Test
	public void testSieveMapStep1() {
		Map<Integer,Double> ref = createSievedRefMap(1);
		
		Map<Integer,Double> sieved = SieveUtil.sieveMap(lst, 1);
		assertEquals(ref,sieved);
	}
	
	private Map<Integer,Double> createSievedRefMap(int sieve){
		Map<Integer,Double> ref = new SafeMap<>();
		for(int i=0;i<lst.size();i+=sieve){
			ref.put(i, lst.get(i));
		}
		return ref;
	}
	
	@Test
	public void testSieveMapStep2() {
		Map<Integer,Double> ref = createSievedRefMap(2);
		
		Map<Integer,Double> sieved = SieveUtil.sieveMap(lst, 2);
		assertEquals(ref,sieved);
	}
	
	@Test
	public void testSieveMapStep5() {
		Map<Integer,Double> ref = createSievedRefMap(5);
		
		Map<Integer,Double> sieved = SieveUtil.sieveMap(lst, 5);
		assertEquals(ref,sieved);
	}

}

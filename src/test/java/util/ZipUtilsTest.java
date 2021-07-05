package util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tcb.common.util.SafeMap;
import com.tcb.sensenet.internal.util.RangeUtil;
import com.tcb.sensenet.internal.util.SieveUtil;
import com.tcb.sensenet.internal.util.ZipUtil;

public class ZipUtilsTest {

	private List<Double> lst;

	@Before
	public void setUp() throws Exception {
		this.lst = Arrays.asList(
				1.,2.,3.,4.,5.,6.,7.,8.,9.,10.);
	}

	@Test
	public void testZipMap() {
		Map<Integer,Double> ref = createRefMap(2);
		
		List<Integer> indices = RangeUtil.intRange(0, lst.size(), 2);
		List<Double> shortLst = SieveUtil.sieve(lst, 2);
		
		assertEquals(ref, ZipUtil.zipMap(indices,shortLst));
	}
	
	private Map<Integer,Double> createRefMap(int indexStep){
		Map<Integer,Double> ref = new SafeMap<>();
		for(int i=0;i<lst.size();i+=indexStep){
			ref.put(i, lst.get(i));
		}
		return ref;
	}

	@Test
	public void testZipMapIndex() {
		Map<Integer,Double> ref = createRefMap(1);
		
		assertEquals(ref, ZipUtil.zipMapIndex(lst));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testZipMapFailsWhenListLengthsDiffer(){
		List<Double> shortLst = Arrays.asList(1.,2.);
		ZipUtil.zipMap(shortLst, lst);
	}

}

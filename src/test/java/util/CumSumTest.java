package util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.CumSum;

public class CumSumTest {

	private List<Double> lst;
	private List<Double> ref;

	@Before
	public void setUp() throws Exception {
		this.lst = Arrays.asList(0.1,0.4,-0.3,0.6,2.2,3.5);
		this.ref = Arrays.asList(0.1,0.5,0.2,0.8,3.0,6.5);
	}

	@Test
	public void testGetCumSums() {
		assertEquals(ref,CumSum.getCumSums(lst));
	}
	
	@Test
	public void testGetCumSumsEmptyList() {
		assertEquals(Arrays.asList(),CumSum.getCumSums(Arrays.asList()));
	}
	
	@Test
	public void testGetCumSumsSingleEle() {
		assertEquals(Arrays.asList(3.),CumSum.getCumSums(Arrays.asList(3.)));
	}
	
	@Test
	public void testGetCumSumsTwoEle() {
		assertEquals(Arrays.asList(3.,7.),CumSum.getCumSums(Arrays.asList(3.,4.)));
	}

}

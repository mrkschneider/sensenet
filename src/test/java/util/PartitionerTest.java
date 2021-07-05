package util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.util.Partitioner;

public class PartitionerTest {

	private List<Integer> lst;

	@Before
	public void setUp() throws Exception {
		this.lst = Arrays.asList(1,2,3,4,5,6);
	}
	
	@Test
	public void testPartition1(){
		List<List<Integer>> ref = Arrays.asList(lst);
		
		List<List<Integer>> test = Partitioner.partition(lst, 1);
		
		assertEquals(ref,test);
	}

	@Test
	public void testPartition2() {
		List<List<Integer>> ref = Arrays.asList(Arrays.asList(1,2,3),Arrays.asList(4,5,6));
		
		List<List<Integer>> test = Partitioner.partition(lst, 2);
		
		assertEquals(ref,test);
	}
	
	@Test
	public void testPartition3(){
		List<List<Integer>> ref = Arrays.asList(Arrays.asList(1,2),Arrays.asList(3,4),Arrays.asList(5,6));
		
		List<List<Integer>> test = Partitioner.partition(lst, 3);
		
		assertEquals(ref,test);
	}
		
	@Test(expected=IllegalArgumentException.class)
	public void testPartition12(){
		Partitioner.partition(lst, 12);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPartition4(){
		Partitioner.partition(lst, 4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPartition0(){	
		Partitioner.partition(lst, 0);
	}

}

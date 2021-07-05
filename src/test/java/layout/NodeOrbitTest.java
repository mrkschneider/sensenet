package layout;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.common.util.Tuple;
import com.tcb.sensenet.internal.layout.nodePlacement.NodeOrbitIterator;

public class NodeOrbitTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testHasNext() {
		NodeOrbitIterator testIterator = new NodeOrbitIterator(0d,0d,0d,1);
		for(int i=0;i<100;i++){
			assertEquals(true,testIterator.hasNext());
			testIterator.next();
		}
		
	}

	@Test
	public void testNext() {
		Double centreX=0d;
		Double centreY=0d;
		Double radius=1d;
		Integer cycleLength=4;
		NodeOrbitIterator testIterator = new NodeOrbitIterator(centreX,centreY,radius,cycleLength);
		List<Tuple<Double,Double>> refTuples = Arrays.asList(
				new Tuple<Double,Double>(1d,0d),
				new Tuple<Double,Double>(0d,1d),
				new Tuple<Double,Double>(-1d,0d),
				new Tuple<Double,Double>(0d,-1d)
				);
		
		assertTuplesAreEqual(refTuples,testIterator);
		
	}
	
	private void assertTuplesAreEqual(List<Tuple<Double,Double>> refTuples, NodeOrbitIterator testIterator){
		for(int i=0;i<testIterator.getCycleLength();i++){
			Tuple<Double,Double> testTuple = testIterator.next();
			assertEquals(refTuples.get(i).one(),testTuple.one(),1E-02);
			assertEquals(refTuples.get(i).two(),testTuple.two(),1E-02);
		}
				
	}
	
	@Test
	public void testNextWithMovedCentre() {
		Double centreX=10d;
		Double centreY=10d;
		Double radius=1d;
		Integer cycleLength=4;
		NodeOrbitIterator testIterator = new NodeOrbitIterator(centreX,centreY,radius,cycleLength);
		List<Tuple<Double,Double>> refTuples = Arrays.asList(
				new Tuple<Double,Double>(11d,10d),
				new Tuple<Double,Double>(10d,11d),
				new Tuple<Double,Double>(9d,10d),
				new Tuple<Double,Double>(10d,9d)
				);
		assertTuplesAreEqual(refTuples,testIterator);
		
	}
	
	@Test
	public void testNextWithRadius2() {
		Double centreX=0d;
		Double centreY=0d;
		Double radius=2d;
		Integer cycleLength=4;
		NodeOrbitIterator testIterator = new NodeOrbitIterator(centreX,centreY,radius,cycleLength);
		List<Tuple<Double,Double>> refTuples = Arrays.asList(
				new Tuple<Double,Double>(2d,0d),
				new Tuple<Double,Double>(0d,2d),
				new Tuple<Double,Double>(-2d,0d),
				new Tuple<Double,Double>(0d,-2d)
				);
		assertTuplesAreEqual(refTuples,testIterator);
		
	}

}

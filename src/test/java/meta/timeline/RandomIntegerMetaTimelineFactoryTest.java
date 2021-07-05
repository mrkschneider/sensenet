package meta.timeline;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.RandomIntegerMetaTimelineFactory;
import com.tcb.matrix.TriangularMatrix;

public class RandomIntegerMetaTimelineFactoryTest {

	private TriangularMatrix transitions;
	private RandomIntegerMetaTimelineFactory fac;
		
	@Before
	public void setUp() throws Exception {
		transitions = new TriangularMatrix(3);
		transitions.set(0, 0, 0.95);
		transitions.set(0, 1, 0.05);
		transitions.set(0, 2, 0.00);
		transitions.set(1, 1, 0.8);
		transitions.set(1, 2, 0.15);
		transitions.set(2, 2, 0.85);
		
		this.fac = new RandomIntegerMetaTimelineFactory(2, 100, transitions);
	}

	@Test
	public void testCreate() {
		MetaTimeline timeline = fac.create();
		System.out.println(Arrays.toString(timeline.getData()));
	}

}

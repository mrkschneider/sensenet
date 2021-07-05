package meta.timeline;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaOccurenceDifferenceTimelineFactory;

public class MetaOccurenceDifferenceTimelineFactoryTest {

	private double[] timeline1 = {-1f,1f,-1f,0f,1f};
	private double[] timeline2 = {-1f,0f,0f,0f,0f};
	private double[] timeline3 = {0f,1f,0f,0f,-1f};
	private double[][] timelines = {timeline1,timeline2,timeline3};
	
	private MetaTimeline metaTimeline;
	private MetaOccurenceDifferenceTimelineFactory factory;

	@Before
	public void setUp() throws Exception {
		this.factory = new MetaOccurenceDifferenceTimelineFactory();
		this.metaTimeline = new MetaOccurenceDifferenceTimelineFactory().create(timelines);
	}

	@Test
	public void testGetLength() {
		assertEquals((Integer) 5,metaTimeline.getLength());
	}

	@Test
	public void testAsDoubles() {
		assertEquals(Arrays.asList(-1.,1.,-1.,0.,0.),metaTimeline.asDoubles());
	}
	
	@Test
	public void testFromMetaTimelines(){
		MetaTimeline timeline = factory.create(Arrays.asList(metaTimeline,metaTimeline));
		
		assertEquals(Arrays.asList(-1.,1.,-1.,0.,0.), timeline.asDoubles());
		
		MetaTimeline additionalTimeline = factory.create(new double[][]{{1f,1f,0f,0f,0f}});
		
		timeline = factory.create(Arrays.asList(metaTimeline,additionalTimeline));
		
		assertEquals(Arrays.asList(0.,1.,-1.,0.,0.), timeline.asDoubles());
	}
	
}

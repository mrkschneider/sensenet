package meta.timeline;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaSumTimelineFactory;

public class MetaSumTimelineFactoryTest {

	private double[] timeline1  = {1f,1f,0f,1f,1f};
	private double[] timeline2 = {1f,0f,0f,0f,1f};
	private double[] timeline3 = {1f,1f,0f,1f,1f};
	private double[][] timelines = {timeline1,timeline2,timeline3};
	
	private MetaTimeline metaTimeline;
	private MetaSumTimelineFactory factory;

	@Before
	public void setUp() throws Exception {
		this.factory = new MetaSumTimelineFactory();
		this.metaTimeline = factory.create(
				timelines);
	}
	

	@Test
	public void testGetLength() {
		assertEquals((Integer) 5,metaTimeline.getLength());
	}

	@Test
	public void testAsDoubles() {
		assertEquals(Arrays.asList(3.,2.,0.,2.,3.),metaTimeline.asDoubles());
	}
	
	@Test
	public void testFromMetaTimelines(){
		MetaTimeline timeline = factory.create(Arrays.asList(metaTimeline,metaTimeline));
		
		assertEquals(Arrays.asList(6.,4.,0.,4.,6.), timeline.asDoubles());
	}
	
}

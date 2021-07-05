package meta.timeline;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaOccurenceTimelineFactory;

public class MetaOccurenceTimelineFactoryTest {

	
	private double[] refTimeline1 = {1f,0f,1f,1f,0f,0f};
	private double[] refTimeline2 = {0f,0f,0f,1f,1f,0f};
	
	private double[][] refTimelines = {refTimeline1,refTimeline2};
	
	// Metatimeline is true at position i when at least one of the sub-timelines is 1 at i.
	private double[] refMetaTimeline = {1d,0d,1d,1d,1d,0d};
	private Integer refLength = 6;
	private MetaTimeline metaTimeline;
	private MetaOccurenceTimelineFactory factory;

	@Before
	public void setUp() throws Exception {
		this.factory = new MetaOccurenceTimelineFactory();
		this.metaTimeline = factory.create(refTimelines);
	}

	@Test
	public void testGet() {
		List<Double> testMetaTimelineFrames = metaTimeline.asDoubles();
		
		for(int i=0;i<refMetaTimeline.length;i++){
			assertEquals((Double)refMetaTimeline[i],testMetaTimelineFrames.get(i));
		}
	}

	@Test
	public void testGetLength() {
		assertEquals(refLength,metaTimeline.getLength());
	}
	
	@Test
	public void testFromMetaTimelines(){
		MetaTimeline timeline = factory.create(Arrays.asList(metaTimeline,metaTimeline));
		
		assertEquals(Arrays.asList(1.,0.,1.,1.,1.,0.), timeline.asDoubles());
		
		MetaTimeline additionalTimeline = factory.create(new double[][]{{1f,1f,0f,0f,0f,0f}});
		
		timeline = factory.create(Arrays.asList(metaTimeline,additionalTimeline));
		
		assertEquals(Arrays.asList(1.,1.,1.,1.,1.,0.), timeline.asDoubles());
		
	}


	
}

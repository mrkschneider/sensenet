package meta.timeline;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaSubTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaSumTimelineFactory;

public class MetaSubTimelineFactoryTest {

	private double[] timeline1  = {1f,1f,0f,1f,1f};
	private double[] timeline2 = {1f,0f,0f,0f,1f};
	private double[] timeline3 = {1f,1f,0f,1f,1f};
	private double[][] timelines = {timeline1,timeline2,timeline3};
	
	private MetaTimeline parent;
	private List<Integer> selectedFrames;
	private MetaSubTimelineFactory fac;
	private MetaTimeline metaTimeline;

	

	@Before
	public void setUp() throws Exception {
		this.parent = new MetaSumTimelineFactory().create(timelines);
		this.selectedFrames = Arrays.asList(0,2,4);
		this.fac = new MetaSubTimelineFactory(selectedFrames);
		this.metaTimeline = fac.create(parent);
	}
	

	@Test
	public void testGetLength() {
		assertEquals((Integer) 3,metaTimeline.getLength());
	}

	@Test
	public void testAsDoubles() {
		assertEquals(Arrays.asList(3.,0.,3.),metaTimeline.asDoubles());
	}
	
	
	
}

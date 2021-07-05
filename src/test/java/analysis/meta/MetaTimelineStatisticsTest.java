package analysis.meta;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.meta.MetaTimelineStatistics;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaSumTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactory;

public class MetaTimelineStatisticsTest {

	private MetaTimelineFactory fac = new MetaSumTimelineFactory();
	
	private MetaTimeline timelineA = fac.createFromStrings(
			Arrays.asList(
					"110111011101111001"
					));
	private MetaTimeline timelineB = fac.createFromStrings(
			Arrays.asList(
					"110101011101011001"
					));
	
	
	@Before
	public void setUp() throws Exception {

	}
	
	@Test
	public void testGetAverage() {
		Double average = MetaTimelineStatistics.getAverage(timelineA);
		assertEquals(0.72,average,0.01);
	}
	
	@Test
	public void testGetStandardDeviation() {
		Double std = MetaTimelineStatistics.getStandardDeviation(timelineA);
		assertEquals(0.46,std,0.01);
	}

	@Test
	public void testGetCovariance() {
		Double cov = MetaTimelineStatistics.getCovariance(timelineA, timelineB);
		assertEquals(0.18,cov,0.01);
	}

	@Test
	public void testGetCorrelation() {
		Double corr = MetaTimelineStatistics.getCorrelation(timelineA, timelineB);
		assertEquals(0.78,corr,0.01);
	}

}

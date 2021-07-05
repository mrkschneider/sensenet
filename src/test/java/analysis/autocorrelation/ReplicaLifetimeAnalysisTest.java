package analysis.autocorrelation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaLifetimeAnalysis;
import com.tcb.sensenet.internal.util.ObjMap;

public class ReplicaLifetimeAnalysisTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetLifetimeConstant() {
		List<Double> timeline = Arrays.asList(
				1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,
				1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d
				);
			
		ObjMap analysis = new ReplicaLifetimeAnalysis(2,0.1).getLifetime(timeline);
		
		Double lifetime = analysis.get("lifetime",Double.class);
		
		assertEquals(23.50,lifetime,0.01);
	}
	
	@Test
	public void testGetLifetimeOneReplicaZero() {
		List<Double> timeline = Arrays.asList(
				1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,1d,
				0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d
				);
			
		ObjMap analysis = new ReplicaLifetimeAnalysis(2,0.1).getLifetime(timeline);
		
		Double lifetime = analysis.get("lifetime",Double.class);
		
		assertEquals(23.50,lifetime,0.01);
	}
	
	@Test
	public void testGetLifetimeAllZero() {
		List<Double> timeline = Arrays.asList(
				0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d,0d
				);
			
		ObjMap analysis = new ReplicaLifetimeAnalysis(2,0.1).getLifetime(timeline);
		
		Double lifetime = analysis.get("lifetime",Double.class);
		
		assertEquals(0d,lifetime,0.01);
	}
	

}

package analysis.cluster;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tcb.cluster.Cluster;
import com.tcb.cluster.ClusterImpl;
import com.tcb.sensenet.internal.analysis.cluster.ClusterReplicaInformation;

public class ClusterReplicaInformationTest {

	private List<Cluster> correlatedClusters = Arrays.asList(
			new ClusterImpl(Arrays.asList("4","5","6","7"),"4",null),
			new ClusterImpl(Arrays.asList("0","1","2","3"),"1",null)
			);
	private List<Cluster> uncorrelatedClusters = Arrays.asList(
			new ClusterImpl(Arrays.asList("0","5","2","7"),"4",null),
			new ClusterImpl(Arrays.asList("4","1","6","3"),"1",null)
			);
	private ClusterReplicaInformation testInfo;
	
	@Before
	public void setUp() throws Exception {
		this.testInfo = new ClusterReplicaInformation(2);
	}

	@Test
	public void testCalculateCorrelated() {
		double test = testInfo.calculate(correlatedClusters);
		assertEquals(1.,test,0.01);
	}
	
	@Test
	public void testCalculateUncorrelated() {
		double test = testInfo.calculate(uncorrelatedClusters);
		assertEquals(0.0,test,0.01);
	}

}

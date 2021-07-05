package analysis.diffusion;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.diffusion.DefaultWalkStrategy;
import com.tcb.sensenet.internal.analysis.diffusion.WalkStrategy;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

import network.WeightedTestNetwork;
import network.WeightedTestNetworkFactory;

public class DefaultWalkStrategyTest {

	private Integer[][] matrix = {
			{0,0,2,0,0,1,1,0,0,1},
			{0,0,0,0,4,0,0,0,0,1},
			{0,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,1,1,0,1,0,1},
			{0,0,0,0,0,0,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,0,0,3},
			{0,0,0,0,0,0,0,0,0,0}};
	private Double[] weights = {
			0.6,0.4,
			1.0,
			2.0,
			0.2,
			2.0,1.0,1.0,2.0,
			2.0,
			1.0,
			0.5,
			0.1,
			0.01,
			3.0,
			1.0,
			2.0,
			3.0,
			4.0,
			1.0,
			0.7,
			2.0,
			1.5,1.4,0.2
	};
	
	protected WeightedTestNetwork testNetwork;
	protected CyNode source;
	private DefaultWalkStrategy strat;
	
	@Before
	public void setUp() throws Exception {
		this.testNetwork = new WeightedTestNetworkFactory().create(matrix, weights);
		this.source = testNetwork.getSortedNodes().get(0);
		this.strat = new DefaultWalkStrategy(source,10,null,0.0);
		strat.setSeed(3574);
	}

	@Test
	public void testCreateRun() {
		WalkStrategy.Run run = strat.createRun();
		CyNode node = source;
		for(int i=0;i<10;i++){
			node = run.next(testNetwork.getNetwork(), node);
		}
		
		/*CyNetworkAdapter network = testNetwork.getNetwork();
		// TODO Check values
		List<CyNode> ref = Arrays.asList(
				network.getNode(33l),
				network.getNode(34l),
				network.getNode(35l),
				network.getNode(28l),
				network.getNode(30l));
		assertEquals(new HashSet<>(ref),new HashSet<>(run.getVisited())); */
	}

}

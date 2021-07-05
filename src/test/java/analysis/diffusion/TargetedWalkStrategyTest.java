package analysis.diffusion;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.diffusion.TargetedWalkStrategy;
import com.tcb.sensenet.internal.analysis.diffusion.WalkStrategy;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class TargetedWalkStrategyTest extends DefaultWalkStrategyTest {

	private TargetedWalkStrategy strat;
	private CyNode target;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.target = testNetwork.getSortedNodes().get(6);
		this.strat = new TargetedWalkStrategy(source,target,
				100,null,0.0);
		strat.setSeed(3574);
	}

	@Test
	public void testCreateRun() {
		CyNode n = source;
		WalkStrategy.Run run = strat.createRun();
		while(n!=null){
			n = run.next(testNetwork.getNetwork(), n);
		}
		
		CyNetworkAdapter network = testNetwork.getNetwork();
		/*
		// TODO Check values
		List<CyNode> ref = Arrays.asList(
				network.getNode(33l),
				network.getNode(34l),
				network.getNode(28l),
				network.getNode(30l));
		assertEquals(new HashSet<>(ref),new HashSet<>(run.getVisited())); */
	}

}

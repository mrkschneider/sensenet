package analysis.diffusion;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.diffusion.SymmetricTargetedWalkStrategy;
import com.tcb.sensenet.internal.analysis.diffusion.TargetedWalkStrategy;
import com.tcb.sensenet.internal.analysis.diffusion.WalkStrategy;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class SymmetricTargetedWalkStrategyTest extends DefaultWalkStrategyTest {

	private SymmetricTargetedWalkStrategy strat;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.strat = new SymmetricTargetedWalkStrategy(source,testNetwork.getSortedNodes().get(6),
				100,null,0.0);
		strat.setSeed(3579);
	}

	@Test
	public void testCreateRun() {
		CyNode n = source;
		WalkStrategy.Run run = strat.createRun();
		while(n!=null){
			n = run.next(testNetwork.getNetwork(), n);
		}
		
		CyNetworkAdapter network = testNetwork.getNetwork();
		/*// TODO Check values
		List<CyNode> ref = Arrays.asList(
				network.getNode(34l),
				network.getNode(34l),
				network.getNode(28l),
				network.getNode(28l),
				network.getNode(30l))
				.stream()
				.sorted(Comparator.comparing(node -> node.getSUID()))
				.collect(Collectors.toList());
		List<CyNode> visited = run.getVisited().stream()
				.sorted(Comparator.comparing(node -> node.getSUID()))
				.collect(Collectors.toList());
		//assertEquals(ref,visited);		*/
	}

}

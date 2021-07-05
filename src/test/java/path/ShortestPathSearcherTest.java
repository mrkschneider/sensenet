package path;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.ShortestPathSearcher;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

import network.AdjacencyMatrixNetworkFactory;

public class ShortestPathSearcherTest {

	private Integer[][] matrix = 
		    {{0,0,1,0,0,1,1,0,0,1},
			{0,0,0,0,1,0,0,0,0,1},
			{1,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,1,1,0,1,0,1},
			{0,1,0,1,0,0,1,1,1,0},
			{1,0,0,1,0,0,0,0,0,1},
			{1,0,1,0,1,0,0,1,0,0},
			{0,0,1,1,1,0,1,0,0,1},
			{0,0,0,0,1,0,0,0,0,1},
			{1,1,0,1,0,1,0,1,1,0}};
	private CyNetworkAdapter network;
	private CyNode[] nodes;
	private ShortestPathSearcher pathSearcher;
			
	
	@Before
	public void setUp() throws Exception {
		this.network = new AdjacencyMatrixNetworkFactory().create(matrix);
		this.nodes = network.getNodeList().stream()
				.sorted(Comparator.comparing(n -> n.getSUID()))
				.toArray(CyNode[]::new);
		this.pathSearcher = 
				new ShortestPathSearcher(network);
	}

	@Test
	public void testGetPaths() {
		List<Path> paths = pathSearcher.getPaths(nodes[1], nodes[2]);
		int[] ref1 = {1,4,6,2};
		int[] ref2 = {1,4,7,2};
		int[] ref3 = {1,9,7,2};
		int[] ref4 = {1,9,0,2};
		assertEquals(4,paths.size());
		assertEqualPath(ref3,paths.get(0));
		assertEqualPath(ref4,paths.get(1));
		assertEqualPath(ref2,paths.get(2));
		assertEqualPath(ref1,paths.get(3));
	}
	
	private void assertEqualPath(int[] refIndices, Path path){
		List<CyNode> refNodes = new ArrayList<>();
		for(int i:refIndices){
			refNodes.add(nodes[i]);
		}
		assertEquals(refNodes,path.getNodes());
	}

}

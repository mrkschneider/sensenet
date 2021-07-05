package path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.FixedLengthPathSearcher;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.PathSearcher;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

import network.AdjacencyMatrixNetworkFactory;

public class FixedLengthPathSearcherTest {

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
	
	@Before
	public void setUp() throws Exception {
		this.network = new AdjacencyMatrixNetworkFactory().create(matrix);
		this.nodes = network.getNodeList().stream()
				.sorted(Comparator.comparing(n -> n.getSUID()))
				.toArray(CyNode[]::new);
	}

	@Test
	public void testGetPathsLength3() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,0,3);
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
	
	@Test
	public void testGetPathsLength2(){
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,0,2);
		List<Path> paths = pathSearcher.getPaths(nodes[1], nodes[2]);
		assertTrue(paths.isEmpty());
	}
	
	@Test
	public void testGetPathsLengthTo4() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,0,4);
		List<Path> paths = pathSearcher.getPaths(nodes[1], nodes[2]);
		assertEquals(12,paths.size());
	}
	
	@Test
	public void testGetPathsLength4Only() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,4,4);
		List<Path> paths = pathSearcher.getPaths(nodes[1], nodes[2]);
		assertEquals(8,paths.size());
	}
	
	private void assertEqualPath(int[] refIndices, Path path){
		List<CyNode> refNodes = new ArrayList<>();
		for(int i:refIndices){
			refNodes.add(nodes[i]);
		}
		List<CyNode> sortedNodes = new ArrayList<>(path.getNodes());
		assertEquals(refNodes,sortedNodes);
	}

}

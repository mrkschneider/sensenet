package path;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.FixedLengthPathSearcher;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.PathSearcher;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

import network.AdjacencyMatrixNetworkFactory;

public class FixedLengthPathSearcherTest2 {

	private Integer[][] matrix = 
		{
				{0,1,1,1},
				{0,0,0,1},
				{1,1,0,0},
				{0,0,0,0}
		};
	private CyNetworkAdapter network;
	private CyNode[] nodes;
	
	@Before
	public void setUp() throws Exception {
		this.network = new AdjacencyMatrixNetworkFactory().create(matrix);
		this.nodes = network.getNodeList().toArray(new CyNode[0]);
	}

	@Test
	public void testGetPaths() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,0,3);
		List<Path> paths = pathSearcher.getPaths(nodes[2], nodes[3]);
		
		int[] ref1 = {2,0,3};
		int[] ref2 = {2,1,3};
		int[] ref3 = {2,0,1,3};
		int[] ref4 = {2,1,0,3};
		assertEquals(4,paths.size());
		assertEqualPath(ref1,paths.get(1));
		assertEqualPath(ref2,paths.get(0));
		assertEqualPath(ref3,paths.get(3));
		assertEqualPath(ref4,paths.get(2));
	}
	
	@Test
	public void testGetPathsLength2() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,0,2);
		List<Path> paths = pathSearcher.getPaths(nodes[2], nodes[3]);
		
		assertEquals(2,paths.size());
	}
	
	@Test
	public void testGetPathsLength3Only() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,3,3);
		List<Path> paths = pathSearcher.getPaths(nodes[2], nodes[3]);
		
		assertEquals(2,paths.size());
	}
	
	@Test
	public void testGetPathsLength4() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,0,4);
		List<Path> paths = pathSearcher.getPaths(nodes[2], nodes[3]);
		
		assertEquals(4,paths.size());
	}
	
	@Test
	public void testGetPathsLength4Only() {
		PathSearcher pathSearcher = new FixedLengthPathSearcher(network,4,4);
		List<Path> paths = pathSearcher.getPaths(nodes[2], nodes[3]);
		
		assertEquals(0,paths.size());
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

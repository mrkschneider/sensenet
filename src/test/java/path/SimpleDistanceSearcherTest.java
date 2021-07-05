package path;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.DistanceSearcher;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.ShortestPathSearcher;
import com.tcb.sensenet.internal.path.SimpleDistanceSearcher;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

import network.AdjacencyMatrixNetworkFactory;

public class SimpleDistanceSearcherTest {

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
	private DistanceSearcher distanceSearcher;
			
	
	@Before
	public void setUp() throws Exception {
		this.network = new AdjacencyMatrixNetworkFactory().create(matrix);
		this.nodes = network.getNodeList().stream()
				.sorted(Comparator.comparing(n -> n.getSUID()))
				.toArray(CyNode[]::new);
		this.distanceSearcher = 
				new SimpleDistanceSearcher();
	}

	@Test
	public void testGetDistance() {
		double d1 = distanceSearcher.distance(network, nodes[1], nodes[2]);
		double d2 = distanceSearcher.distance(network, nodes[1], nodes[1]);
		double d3 = distanceSearcher.distance(network, nodes[3], nodes[7]);
		double d4 = distanceSearcher.distance(network, nodes[5], nodes[2]);
		double d5 = distanceSearcher.distance(network, nodes[0], null);
		double d6 = distanceSearcher.distance(network, nodes[0], nodes[1]);
		double ref1 = 3;
		double ref2 = 0;
		double ref3 = 1;
		double ref4 = 2;
		double ref5 = Double.POSITIVE_INFINITY;
		double ref6 = 2;
			
		assertEquals(ref1,d1,0.01);
		assertEquals(ref2,d2,0.01);
		assertEquals(ref3,d3,0.01);
		assertEquals(ref4,d4,0.01);
		assertEquals(ref5,d5,0.01);
		assertEquals(ref6,d6,0.01);
	}
	
	@Test
	public void testGetDistanceWithIgnored() {
		
		DistanceSearcher distanceSearcher = new SimpleDistanceSearcher(
				Arrays.asList(nodes[9],nodes[6],nodes[7]));
		
		double d1 = distanceSearcher.distance(network, nodes[1], nodes[2]);
		double d2 = distanceSearcher.distance(network, nodes[1], nodes[1]);
		double d3 = distanceSearcher.distance(network, nodes[3], nodes[7]);
		double d4 = distanceSearcher.distance(network, nodes[5], nodes[2]);
		double d5 = distanceSearcher.distance(network, nodes[0], null);
		double d6 = distanceSearcher.distance(network, nodes[0], nodes[1]);
		double ref1 = 5;
		double ref2 = 0;
		double ref3 = Double.POSITIVE_INFINITY;
		double ref4 = 2;
		double ref5 = Double.POSITIVE_INFINITY;
		double ref6 = 4;
			
		assertEquals(ref1,d1,0.01);
		assertEquals(ref2,d2,0.01);
		assertEquals(ref3,d3,0.01);
		assertEquals(ref4,d4,0.01);
		assertEquals(ref5,d5,0.01);
		assertEquals(ref6,d6,0.01);
	}
	
	

}

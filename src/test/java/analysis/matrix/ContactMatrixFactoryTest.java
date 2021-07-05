package analysis.matrix;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrixFactory;

import network.WeightedTestNetwork;
import network.WeightedTestNetworkFactory;

public class ContactMatrixFactoryTest {

	private Integer[][] matrix = {
			{0,0,1,0,0,1,1,0,0,1},
			{0,0,0,0,1,0,0,0,0,1},
			{0,0,0,0,0,0,1,1,0,0},
			{0,0,0,0,2,1,0,1,0,1},
			{0,0,0,0,0,0,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,0,0,0}};
	private Double[] weights = {
			1.0,1.0,2.0,0.2,6.0,2.0,1.0,0.5,0.1,0.01,
			3.0,1.0,2.0,2.0,3.0,4.0,1.0,0.7,2.0,3.1
	};
	private WeightedTestNetwork refNetwork;

	@Before
	public void setUp() throws Exception {
		this.refNetwork = new WeightedTestNetworkFactory().create(matrix,weights);
	}

	@Test
	public void testCreate() {
		
		ContactMatrixFactory fac = new ContactMatrixFactory();
		
		List<CyNode> nodes = refNetwork.getSortedNodes();
		
		ContactMatrix contactMatrix = fac.create(refNetwork.getNetwork());
		
		for(int i=0;i<matrix.length;i++){
			for(int j=i;j<matrix.length;j++){
				double ref = matrix[i][j];
				double test = contactMatrix.get(nodes.get(i), nodes.get(j));
				double test2 = contactMatrix.get(nodes.get(j), nodes.get(i));
				assertEquals(ref,test,0.01);
				assertEquals(ref,test2,0.01);
			}
		}

	}	

}

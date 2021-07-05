package analysis.matrix;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseRealMatrix;
import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrixImpl;
import com.tcb.sensenet.internal.util.IndexMap;
import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.LabeledSquareMatrixImpl;
import com.tcb.matrix.SparseMatrix;
import com.tcb.common.util.SafeMap;

public class ContactMatrixTest {

	private ContactMatrixImpl matrix;
	private double[] row1;
	private double[] row2;
	private double[] row3;
	private double[][] data;
	private List<CyNode> nodes;
	private LabeledMatrix<CyNode> labeledMatrix;

	@Before
	public void setUp() throws Exception {
		this.nodes = mockNodes();
		this.labeledMatrix = LabeledSquareMatrixImpl.create(nodes, createSparseMatrix());
		this.matrix = new ContactMatrixImpl(labeledMatrix);
		
	}
	
	private SparseMatrix createSparseMatrix(){
		this.row1 = new double[]{0d,-2d,1d};
		this.row2 = new double[]{-2d,0d,3d};
		this.row3 = new double[]{1d,3d,0d};
		this.data = new double[][]{row1,row2,row3};
		SparseMatrix m = new SparseMatrix(data);
		return m;
	}
	
	private List<CyNode> mockNodes(){
		List<CyNode> nodes = new ArrayList<>();
		for(int i=0;i<3;i++){
			CyNode node = mockNode(i+1);
			nodes.add(node);
		}
		return nodes;
	}
	
	private CyNode mockNode(long suid){
		CyNode node = Mockito.mock(CyNode.class);
		when(node.getSUID()).thenReturn(suid);
		return node;
	}
	
	@Test
	public void testGet() {
		assertEquals(-2d, matrix.get(nodes.get(0), nodes.get(1)), 0.01);
		assertEquals(-2d, matrix.get(nodes.get(1), nodes.get(0)), 0.01);
		
		assertEquals(3d, matrix.get(nodes.get(1), nodes.get(2)), 0.01);
		assertEquals(3d, matrix.get(nodes.get(2), nodes.get(1)), 0.01);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetFailsForUnknownNode() {
		CyNode node = mockNode(1000);
		matrix.get(node, nodes.get(0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetFailsForUnknownNode2() {
		CyNode node = mockNode(1000);
		matrix.get(nodes.get(0), node);
	}
	
	@Test
	public void testGetFrobeniusNorm(){
		assertEquals(5.29d, matrix.getMatrix().getFrobeniusNorm(), 0.01);
	}
		
	@Test
	public void testGetDataReordered(){
		Comparator<CyNode> comp = (n1,n2) -> n2.getSUID().compareTo(n1.getSUID());
		List<CyNode> sortedNodes = nodes.stream()
				.sorted(comp)
				.collect(Collectors.toList());
		
		double[][] d = matrix.getData(sortedNodes);
		for(int i=0;i<data.length;i++){
			double[] refRaw = data[data.length - 1 - i];
			double[] ref = Arrays.copyOf(refRaw, refRaw.length);
			ArrayUtils.reverse(ref);
			double[] test = d[i];
			assertTrue(Arrays.equals(ref, test));
		}
	}
	
}

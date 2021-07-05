package analysis.matrix;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.analysis.matrix.TimeFrameContactMatrixFactory;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineStore;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaSumDifferenceTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactoryImpl;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;



public class TimeFrameContactMatrixFactoryTest {

	private int[] edgeSources = {0, 0, 1, 0};
	private int[] edgeTargets = {1, 2, 2, 1};
	
 	private double[] edge1Timeline = {-1f,-1f,0f};
	private double[]  edge2Timeline = {1f,0f,0f};
	private double[]  edge3Timeline = {1f,2f,0f};
	private double[] edge4Timeline = {3f,1f,2f};
	//private double[]  edge4Timeline = {0d,1d,-1d};
	
	private double[][] refMatrix1 = {
			{0,2,1},
			{2,0,1},
			{1,1,0}};
	private double[][] refMatrix2 = {
			{0,0,0},
			{0,0,2},
			{0,2,0}};
	private double[][] refMatrix3 = {
			{0,2,0},
			{2,0,0},
			{0,0,0}};
	
	private double[][][] refMatrices = {refMatrix1, refMatrix2, refMatrix3};
	
	private List<CyEdge> edges;
	private MetaNetwork metaNetwork;
	private NetworkMetaTimelineFactory metaTimelineFactory;
	private TimeFrameContactMatrixFactory matrixFactory;
	private TimelineManager timelineManager;
	private List<CyNode> nodes;
	private CyNetworkAdapter network;
	
	
	@Before
	public void setUp() throws Exception {
		this.metaNetwork = mockMetaNetwork();
		this.nodes = mockNodes();
		this.edges = mockEdges();
		this.network = mockNetwork();
		this.timelineManager = createTimelineManager();
		this.metaTimelineFactory = createMetaTimelineFactory();
		this.matrixFactory = new TimeFrameContactMatrixFactory(1, metaNetwork, metaTimelineFactory);
	}
	
	private CyNetworkAdapter mockNetwork(){
		CyNetworkAdapter network = Mockito.mock(CyNetworkAdapter.class);
		when(network.getNodeList()).thenReturn(nodes);
		when(network.getEdgeList()).thenReturn(edges);
		return network;
	}
	
	private List<CyEdge> mockEdges(){
		List<CyEdge> edges = new ArrayList<CyEdge>();
		CyRowAdapter row = Mockito.mock(CyRowAdapter.class);
		
		for(int i=0;i<4;i++){
			CyEdge edge = Mockito.mock(CyEdge.class);
			CyNode source = nodes.get(edgeSources[i]);
			CyNode target = nodes.get(edgeTargets[i]);
			when(edge.getSource()).thenReturn(source);
			when(edge.getTarget()).thenReturn(target);
			when(edge.getSUID()).thenReturn(100l+i);
			when(metaNetwork.getHiddenRow(edge)).thenReturn(row);
			when(row.get(AppColumns.IS_METAEDGE, Boolean.class))
			.thenReturn(false);
			edges.add(edge);
		}
		return edges;
	}
	
	private List<CyNode> mockNodes(){
		List<CyNode> nodes = new ArrayList<>();
		for(int i=0;i<3;i++){
			CyNode node = Mockito.mock(CyNode.class);
			when(node.getSUID()).thenReturn((long) (i+1));
			nodes.add(node);
		}
		return nodes;
	}
	
	private MetaNetwork mockMetaNetwork(){
		MetaNetwork m = Mockito.mock(MetaNetwork.class);
		CyRowAdapter dataRow = Mockito.mock(CyRowAdapter.class);
		
		when(m.getHiddenDataRow()).thenReturn(dataRow);
		when(dataRow.get(AppColumns.TIMELINE_LENGTH, Integer.class)).thenReturn(3);
		when(m.getSUID()).thenReturn(1000l);
		//for(int i=0;i<3;i++){
		//	when(m.getSubedges(edges.get(i))).thenReturn(Arrays.asList(edges.get(i)));
		//}
		
		
		return m;
	}
	
	private TimelineManager createTimelineManager(){
		TimelineManager m = new TimelineManager();
		TimelineStore s = new TimelineStore();
		s.put(edges.get(0), MetaTimelineImpl.create(edge1Timeline));
		s.put(edges.get(1), MetaTimelineImpl.create(edge2Timeline));
		s.put(edges.get(2), MetaTimelineImpl.create(edge3Timeline));
		s.put(edges.get(3), MetaTimelineImpl.create(edge4Timeline));
		m.put(metaNetwork, s);
		return m;
	}
	
	private NetworkMetaTimelineFactory createMetaTimelineFactory(){
		MetaTimelineFactory m = new MetaSumDifferenceTimelineFactory();
		NetworkMetaTimelineFactory fac = new NetworkMetaTimelineFactoryImpl(m,timelineManager);
		return fac;
	}
	
	@Test
	public void testCreateTimelineMatrices() {
		
		List<ContactMatrix> matrices = 
				matrixFactory.createTimelineMatrices(network).entrySet()
				.stream()
				.sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
				.map(e -> e.getValue())
				.collect(Collectors.toList());
		for(int i=0;i<matrices.size();i++){
			double[][] matrix = matrices.get(i).getMatrix().getData();
			double[][] refMatrix = refMatrices[i];
			assertMatrixEquals(refMatrix,matrix, 0.01);
		}
	}
	
	@Test
	public void testCreateTimelineMatricesSieve2() {
		TimeFrameContactMatrixFactory matrixFactory = new TimeFrameContactMatrixFactory(2, metaNetwork, metaTimelineFactory);
		List<ContactMatrix> matrices = 
				matrixFactory.createTimelineMatrices(network).entrySet()
				.stream()
				.sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
				.map(e -> e.getValue())
				.collect(Collectors.toList());
		
		assertEquals(2, matrices.size());
		assertMatrixEquals(refMatrices[0],matrices.get(0).getMatrix().getData(),0.01);
		assertMatrixEquals(refMatrices[2],matrices.get(1).getMatrix().getData(),0.01);
	}

	private void assertMatrixEquals(double[][] refMatrix, double[][] matrix, double limit){
		for(int i=0;i<refMatrix.length;i++){
			double[] row = matrix[i];
			double[] refRow = refMatrix[i];
			for(int j=0;j<refRow.length;j++){
				double v = row[j];
				double refV = refRow[j];
				assertEquals(refV,v,limit);
			}
		}
	}

}

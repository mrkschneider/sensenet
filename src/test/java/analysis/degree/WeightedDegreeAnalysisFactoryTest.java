package analysis.degree;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tcb.sensenet.internal.analysis.degree.NodeAdjacentEdgeColumnWeightedDegreeStrategy;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeAnalysis;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeStrategy;
import com.tcb.sensenet.internal.analysis.normalization.MinMaxNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.ZScoreNormalizationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.AbsoluteValueNegativeValuesStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

import network.AdjacencyMatrixNetworkFactory;

public class WeightedDegreeAnalysisFactoryTest {

	private Integer[][] matrix = 
		{
				{0,1,1,1},
				{0,0,0,1},
				{0,0,0,0},
				{0,0,0,0}
		};
	
	private List<Double> weights = Arrays.asList(0.5,0.2,-1.2,-3.0);
		
	private CyNetworkAdapter network;
	private List<CyNode> nodes;

	private List<CyEdge> edges;

	private CyTableAdapter edgeTable;

	private CyTableAdapter nodeTable;

	private CyRootNetworkAdapter rootNetwork;
	
	@Before
	public void setUp() throws Exception {
		AdjacencyMatrixNetworkFactory fac = new AdjacencyMatrixNetworkFactory();
		CyNetworkTableManager tableManager = fac.getTestSupport().getNetworkTableManager();
		
		this.network = fac.create(matrix);
		this.rootNetwork = new CyRootNetworkAdapter(fac.getTestSupport().getRootNetworkFactory()
				.getRootNetwork(network.getAdaptedNetwork()));
		this.edgeTable = new CyTableAdapter(tableManager.getTable(rootNetwork.getAdaptedNetwork(),
						CyEdge.class,CyRootNetwork.SHARED_DEFAULT_ATTRS));
		this.nodeTable = new CyTableAdapter(tableManager.getTable(rootNetwork.getAdaptedNetwork(),
				CyNode.class, CyRootNetwork.SHARED_DEFAULT_ATTRS));
		edgeTable.createColumn(AppColumns.WEIGHT, Double.class, false);
		nodeTable.createColumn(AppColumns.DEGREE, Double.class, false);
				
		this.nodes = network.getNodeList().stream()
				.sorted(Comparator.comparing(n -> n.getSUID()))
				.collect(Collectors.toList());
		this.edges = network.getEdgeList().stream()
				.sorted(Comparator.comparing(n -> n.getSUID()))
				.collect(Collectors.toList());
		
		for(int i=0;i<edges.size();i++){
			CyEdge e = edges.get(i);
			network.getRow(edges.get(i)).set(AppColumns.WEIGHT, weights.get(i));
		}
	}

	@Test
	public void testCreate() {
		List<Double> ref = Arrays.asList(1.9,3.5,0.2,4.2);
		NormalizationStrategy normalizationStrategy =
				new NoNormalizationStrategy();
		NegativeValuesStrategy negativeValuesStrategy =
				new AbsoluteValueNegativeValuesStrategy();
		WeightedDegreeStrategy degreeStrategy = 
				new NodeAdjacentEdgeColumnWeightedDegreeStrategy(
						AppColumns.WEIGHT.toString(), negativeValuesStrategy);
		
		WeightedDegreeAnalysis fac = new WeightedDegreeAnalysis(degreeStrategy,
				normalizationStrategy);
		checkDegrees(ref,fac.analyse(network));
	}
	
	private void checkDegrees(List<Double> ref, ObjMap test){
		@SuppressWarnings("unchecked")
		Map<CyNode,Double> m = test.get("degrees",Map.class);
		for(int i=0;i<nodes.size();i++){
			CyNode n = nodes.get(i);
			assertEquals(ref.get(i),m.get(n),0.01);
		}
	}
	
	@Test
	public void testCreateWithMinMaxNormalization() {
		List<Double> ref = Arrays.asList(0.425,0.825,0.0,1.0);
		NormalizationStrategy normalizationStrategy =
				new MinMaxNormalizationStrategy();
		NegativeValuesStrategy negativeValuesStrategy =
				new AbsoluteValueNegativeValuesStrategy();
		WeightedDegreeStrategy degreeStrategy = 
				new NodeAdjacentEdgeColumnWeightedDegreeStrategy(
						AppColumns.WEIGHT.toString(), negativeValuesStrategy);
		
		WeightedDegreeAnalysis fac = new WeightedDegreeAnalysis(degreeStrategy,
				normalizationStrategy);
		checkDegrees(ref,fac.analyse(network));
	}
	
	@Test
	public void testCreateWithZScoreNormalization() {
		List<Double> ref = Arrays.asList(-0.31,0.59,-1.26,0.98);
		NormalizationStrategy normalizationStrategy =
				new ZScoreNormalizationStrategy();
		NegativeValuesStrategy negativeValuesStrategy =
				new AbsoluteValueNegativeValuesStrategy();
		WeightedDegreeStrategy degreeStrategy = 
				new NodeAdjacentEdgeColumnWeightedDegreeStrategy(
						AppColumns.WEIGHT.toString(), negativeValuesStrategy);
		
		WeightedDegreeAnalysis fac = new WeightedDegreeAnalysis(degreeStrategy,
				normalizationStrategy);
		checkDegrees(ref,fac.analyse(network));
	}

}

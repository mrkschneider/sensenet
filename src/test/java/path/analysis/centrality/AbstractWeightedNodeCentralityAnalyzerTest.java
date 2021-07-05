package path.analysis.centrality;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;
import org.junit.Before;
import org.junit.Test;

import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityAnalyzer;
import com.tcb.sensenet.internal.util.ObjMap;

import network.WeightedTestNetwork;
import network.WeightedTestNetworkFactory;

public abstract class AbstractWeightedNodeCentralityAnalyzerTest {
	
	protected abstract Integer[][] getAdjacencyMatrix();
	protected abstract Double[] getWeights();
	
	protected abstract List<Double> getRefUnweightedCentralities();
	protected abstract List<Double> getRefWeightedCentralities();
	
	protected abstract NodeCentralityAnalyzer getUnweightedAnalyzer();
	protected abstract NodeCentralityAnalyzer getWeightedAnalyzer();
	
	protected WeightedTestNetworkFactory fac = new WeightedTestNetworkFactory();
	private WeightedTestNetwork refNetwork;
	
	
	@Before
	public void setUp() throws Exception {
		this.refNetwork = fac.create(getAdjacencyMatrix(),getWeights());
	}
	
	protected WeightedTestNetwork getRefNetwork(){
		return refNetwork;
	}
	
	
	protected void testGetCentralities(List<Double> refCentralities, NodeCentralityAnalyzer analyzer){
		Map<CyNode,ObjMap> centralities = analyzer.analyze(
				refNetwork.getNetwork());
				
		List<Double> test = refNetwork.getSortedNodes().stream()
				.map(n -> centralities.get(n).get("centrality", Double.class))
				.collect(Collectors.toList());
		/*
		for(CyEdge e:refNetwork.getSortedEdges()){
			System.out.println(String.format("%d %d", e.getSource().getSUID(),e.getTarget().getSUID()));
		}
		System.out.println(refCentralities);
		System.out.println(test);*/
						
		for(int i=0;i<refCentralities.size();i++){
			assertEquals(refCentralities.get(i),test.get(i),0.01);
		}
	}
	
	@Test
	public void testGetUnweightedCentralities() {
		
		NodeCentralityAnalyzer analysis = getUnweightedAnalyzer();
		
		List<Double> ref = getRefUnweightedCentralities();
		
		testGetCentralities(ref,analysis);
	}
	
	@Test
	public void testGetWeightedCentralities() {
		
		NodeCentralityAnalyzer analysis = getWeightedAnalyzer();
				
		List<Double> ref = getRefWeightedCentralities();
		
		testGetCentralities(ref,analysis);
	}
	
}

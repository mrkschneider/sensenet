package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.path;

import java.util.Optional;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.NodeCentralityWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.AbstractIntegrationTestTask;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;
import com.tcb.sensenet.internal.task.path.centrality.factories.ActionWeightedNodeCentralityTaskFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;



public class NodeBetweennessCentralityAnalysisTest extends AbstractIntegrationTestTask {
	
	public NodeBetweennessCentralityAnalysisTest(Bundle bundle, AppGlobals appGlobals){
		super(bundle, appGlobals);
	}
	
	
	@Override
	protected void runTest(TaskMonitor taskMonitor) {
		TaskIterator tasks = new TaskIterator();
				
		tasks = new TaskIterator();
		
		String weightColumnName = AppColumns.WEIGHT.toString();
		WeightAccumulationMode nodeWeightMode = WeightAccumulationMode.EDGE_COUNT;
		EdgeDistanceMode distanceMode = EdgeDistanceMode.INVERSE;
		CentralityNormalizationMode normalizationMode = CentralityNormalizationMode.MAX_NODE_PAIRS;
		NodeCentralityType centralityType = NodeCentralityType.BETWEENNESS;		
		NegativeValuesMode negativeWeightMode = NegativeValuesMode.ABSOLUTE_VALUE;
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		RowWriter writer = new NodeCentralityWriter();
		WeightedNodeCentralityTaskConfig config = 
				WeightedNodeCentralityTaskConfig.create(
						network, writer,
						Optional.of(weightColumnName),
						centralityType,
						nodeWeightMode, distanceMode, 
						normalizationMode, negativeWeightMode);
		tasks.append(new ActionWeightedNodeCentralityTaskFactory(appGlobals)
				.createTaskIterator(config));

		appGlobals.synTaskManager.execute(tasks);
		
		checkSuccess();
	}
	
	private void checkSuccess() {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		
		checkNodeColumnData(AppColumns.CENTRALITY, rootNetwork, getReference());
	}


	@Override
	public TestReference getReference() {
		return TestReference.UNWEIGHTED_NODE_BETWEENNESS_CENTRALITY;
	}		
}

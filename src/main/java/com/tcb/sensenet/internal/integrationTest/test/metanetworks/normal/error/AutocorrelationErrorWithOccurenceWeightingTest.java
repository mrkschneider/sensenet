package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.error;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.AutocorrelationAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.AutocorrelationAnalysisWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationTimeWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.AutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.strategy.MaxAutocorrelationTimeWeightStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.AbstractIntegrationTestTask;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.aggregation.factories.MetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;




public class AutocorrelationErrorWithOccurenceWeightingTest extends AbstractIntegrationTestTask {
	
	public AutocorrelationErrorWithOccurenceWeightingTest(Bundle bundle, AppGlobals appGlobals){
		super(bundle, appGlobals);
	}
	
	
	@Override
	protected void runTest(TaskMonitor taskMonitor) {
		TaskIterator tasks = new TaskIterator();
		
		FrameWeightMethod method = FrameWeightMethod.OCCURRENCE;
		ReplicaAutocorrelationTimeWeightMethod timeMergeMethod = ReplicaAutocorrelationTimeWeightMethod.MAX;

		MetaTimelineAggregatorConfig aggregatorConfig = 
			new AutocorrelationAggregatorConfig(1, timeMergeMethod);
		RowWriter writer = new AutocorrelationAnalysisWriter();
		MetaTimelineAggregationTaskConfig config = 
				new MetaTimelineAggregationTaskConfig(aggregatorConfig,method,writer,TaskLogType.WEIGHT_ERRORS);
				
		tasks = new TaskIterator();
		ObjMap results = new ObjMap();
	
		tasks.append(
				new MetaTimelineAggregationTaskFactory(appGlobals)
				.createTaskIterator(results,config));
		appGlobals.synTaskManager.execute(tasks);
		
		checkSuccess();
	}
	
	private void checkSuccess() {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		
		checkEdgeColumnData(AppColumns.AUTOCORRELATION_SAMPLE_SIZE, rootNetwork, getReference());
		checkEdgeColumnData(AppColumns.AUTOCORRELATION_TIME, rootNetwork, getReference());
		checkEdgeColumnData(AppColumns.ERROR_ESTIMATE, rootNetwork, getReference());
	}


	@Override
	public TestReference getReference() {
		return TestReference.AUTOCORRELATION_ERROR_OCCURENCE_WEIGHTING;
	}		
}

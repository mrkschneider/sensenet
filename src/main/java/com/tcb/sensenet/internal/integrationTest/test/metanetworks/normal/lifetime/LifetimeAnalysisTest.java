package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.lifetime;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.LifetimeAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.LifetimeWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
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



public class LifetimeAnalysisTest extends AbstractIntegrationTestTask {
	
	public LifetimeAnalysisTest(Bundle bundle, AppGlobals appGlobals){
		super(bundle, appGlobals);
	}
	
	
	@Override
	protected void runTest(TaskMonitor taskMonitor) {
		TaskIterator tasks = new TaskIterator();
				
		tasks = new TaskIterator();
		

		MetaTimelineAggregatorConfig aggregatorConfig = new LifetimeAggregatorConfig(1);
		RowWriter writer = new LifetimeWriter();
		FrameWeightMethod weightMethod = FrameWeightMethod.OCCURRENCE;
		
		MetaTimelineAggregationTaskConfig config = new MetaTimelineAggregationTaskConfig(
				aggregatorConfig,weightMethod,writer,TaskLogType.LIFETIME);
		ObjMap results = new ObjMap();
		
		tasks.append(new MetaTimelineAggregationTaskFactory(appGlobals)
				.createTaskIterator(results,config));
		appGlobals.synTaskManager.execute(tasks);
		
		checkSuccess();
	}
	
	private void checkSuccess() {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		
		checkEdgeColumnData(AppColumns.LIFETIME, rootNetwork, getReference());
	}


	@Override
	public TestReference getReference() {
		return TestReference.LIFETIME;
	}		
}

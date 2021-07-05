package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.AbstractIntegrationTestTask;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateAverageFrameWeightingTaskFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;



public class ChangeWeightingToOccurenceTest extends AbstractIntegrationTestTask {
	
	public ChangeWeightingToOccurenceTest(Bundle bundle, AppGlobals appGlobals){
		super(bundle, appGlobals);
	}
	
	
	@Override
	protected void runTest(TaskMonitor taskMonitor) {
		TaskIterator tasks = new TaskIterator();
				
		tasks = new TaskIterator();
		
		tasks.append(
				new ActivateAverageFrameWeightingTaskFactory(appGlobals,
						FrameWeightMethod.OCCURRENCE)
				.createTaskIterator());
		appGlobals.synTaskManager.execute(tasks);
		
		checkWeightingHasSucceeded();
	}
	
	private void checkWeightingHasSucceeded() {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		
		checkEdgeColumnData(AppColumns.WEIGHT, rootNetwork, getReference());
		checkEdgeColumnData(AppColumns.OCCURRENCE, rootNetwork, getReference());
		checkEdgeColumnData(AppColumns.STANDARD_DEVIATION, rootNetwork, getReference());
		
	}


	@Override
	public TestReference getReference() {
		return TestReference.OCCURENCE_WEIGHTED_TIMELINE;
	}		
}

package com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.reference;

import java.util.Properties;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.ServiceProperties;
import org.cytoscape.work.TaskIterator;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.create.ImportDifferenceMetaNetworkTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceActivateTimepointWeightingInFrame10Test;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceChangeTimepointWeightingToOccurenceInFrame50Test;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceChangeTimepointWeightingToSumInFrame10Test;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceChangeWeightingToOccurenceTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceChangeWeightingToSumTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceSelectFrame50InTimepointWeightingTest;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.integrationTest.test.reference.WriteTestReferenceTask;

public class WriteDifferenceMetaNetworkTestRefsTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private Bundle bundle;

	public WriteDifferenceMetaNetworkTestRefsTaskFactory(Bundle bundle,
			AppGlobals appGlobals){
		this.bundle = bundle;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		tasks.append(createDifferenceMetanetworkTasks());
		return tasks;
	}
		
	private TaskIterator createDifferenceMetanetworkTasks(){
		TaskIterator tasks = new TaskIterator();
		tasks.append(new ImportDifferenceMetaNetworkTest(bundle,appGlobals));
				
		tasks.append(new DifferenceChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.OCCURENCE_WEIGHTED_TIMELINE_DIFFERENCE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
				
		tasks.append(new DifferenceChangeWeightingToSumTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.SUM_WEIGHTED_TIMELINE_DIFFERENCE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new DifferenceActivateTimepointWeightingInFrame10Test(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.OCCURENCE_WEIGHTED_TIMEPOINT_FRAME10_DIFFERENCE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new DifferenceChangeTimepointWeightingToSumInFrame10Test(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.SUM_WEIGHTED_TIMEPOINT_FRAME10_DIFFERENCE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new DifferenceSelectFrame50InTimepointWeightingTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.SUM_WEIGHTED_TIMEPOINT_FRAME50_DIFFERENCE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new DifferenceChangeTimepointWeightingToOccurenceInFrame50Test(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.OCCURENCE_WEIGHTED_TIMEPOINT_FRAME50_DIFFERENCE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		return tasks;
	}
	
	public static Properties getProperties(){
		Properties p = new Properties();
		p.setProperty(ServiceProperties.COMMAND_NAMESPACE, CyActivator.APP_TEST_NAMESPACE);
		p.setProperty(ServiceProperties.COMMAND,"writeDifferenceTestReferences");
		return p;
	}
	
}

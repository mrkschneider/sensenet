package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.reference;

import java.util.Properties;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.ServiceProperties;
import org.cytoscape.work.TaskIterator;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.correlation.CorrelationFactorsTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.correlation.DifferenceCorrelationFactorsTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.create.ImportMetaNetworkTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.create.ImportRefMetaNetworkTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.error.AutocorrelationErrorWithOccurenceWeightingTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.error.AutocorrelationErrorWithSumWeightingTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.lifetime.LifetimeAnalysisTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.path.NodeBetweennessCentralityAnalysisTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ActivateTimepointWeightingInFrame10Test;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ChangeTimepointWeightingToOccurenceInFrame50Test;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ChangeTimepointWeightingToSumInFrame10Test;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ChangeWeightingToOccurenceTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.ChangeWeightingToSumTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.DeactivateTimepointWeightingTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.weighting.SelectFrame50InTimepointWeightingTest;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.integrationTest.test.reference.WriteTestReferenceTask;

public class WriteNormalMetaNetworkTestRefsTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private Bundle bundle;

	public WriteNormalMetaNetworkTestRefsTaskFactory(Bundle bundle,
			AppGlobals appGlobals){
		this.bundle = bundle;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		tasks.append(createNormalMetanetworkTasks());
		return tasks;
	}
	
	private TaskIterator createNormalMetanetworkTasks(){
		TaskIterator tasks = new TaskIterator();
		tasks.append(new ImportRefMetaNetworkTest(bundle,appGlobals));
		tasks.append(new ImportMetaNetworkTest(bundle,appGlobals));
				
		tasks.append(new ChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.OCCURENCE_WEIGHTED_TIMELINE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new ChangeWeightingToSumTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.SUM_WEIGHTED_TIMELINE,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new ActivateTimepointWeightingInFrame10Test(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.OCCURENCE_WEIGHTED_TIMEPOINT_FRAME10,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new ChangeTimepointWeightingToSumInFrame10Test(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.SUM_WEIGHTED_TIMEPOINT_FRAME10,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new SelectFrame50InTimepointWeightingTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.SUM_WEIGHTED_TIMEPOINT_FRAME50,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new ChangeTimepointWeightingToOccurenceInFrame50Test(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.OCCURENCE_WEIGHTED_TIMEPOINT_FRAME50,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new DeactivateTimepointWeightingTest(bundle,appGlobals));
		
		tasks.append(new LifetimeAnalysisTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.LIFETIME,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new NodeBetweennessCentralityAnalysisTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.UNWEIGHTED_NODE_BETWEENNESS_CENTRALITY,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new ChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new AutocorrelationErrorWithOccurenceWeightingTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.AUTOCORRELATION_ERROR_OCCURENCE_WEIGHTING,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new ChangeWeightingToSumTest(bundle,appGlobals));
		tasks.append(new AutocorrelationErrorWithSumWeightingTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.AUTOCORRELATION_ERROR_SUM_WEIGHTING,appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new CorrelationFactorsTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.CORRELATION_FACTOR, appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		tasks.append(new DifferenceCorrelationFactorsTest(bundle,appGlobals));
		tasks.append(new WriteTestReferenceTask(TestReference.DIFFERENCE_CORRELATION_FACTOR, appGlobals.applicationManager, appGlobals.rootNetworkManager));
		
		return tasks;
	}
		
	public static Properties getProperties(){
		Properties p = new Properties();
		p.setProperty(ServiceProperties.COMMAND_NAMESPACE, CyActivator.APP_TEST_NAMESPACE);
		p.setProperty(ServiceProperties.COMMAND,"writeNormalTestReferences");
		return p;
	}
	
}

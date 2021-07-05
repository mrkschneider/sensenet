package com.tcb.sensenet.internal.integrationTest;

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
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceDeactivateTimepointWeightingTest;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.weighting.DifferenceSelectFrame50InTimepointWeightingTest;
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

public class IntegrationTestsTaskFactory extends AbstractTaskFactory {

	private AppGlobals appGlobals;
	private Bundle bundle;

	public IntegrationTestsTaskFactory(Bundle bundle, AppGlobals appGlobals){
		this.bundle = bundle;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		TaskIterator tasks = new TaskIterator();
		tasks.append(new ImportRefMetaNetworkTest(bundle,appGlobals));
		tasks.append(new ImportMetaNetworkTest(bundle, appGlobals));		
		tasks.append(createNormalMetanetworkAnalysisTasks());
		tasks.append(new ImportDifferenceMetaNetworkTest(bundle,appGlobals));
		tasks.append(createDifferenceMetanetworkAnalysisTasks());
		return tasks;
	}
	
	private TaskIterator createNormalMetanetworkAnalysisTasks(){
		TaskIterator tasks = new TaskIterator();
		tasks.append(new ChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new ChangeWeightingToSumTest(bundle,appGlobals));
		
		tasks.append(new ChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new ActivateTimepointWeightingInFrame10Test(bundle,appGlobals));
		tasks.append(new ChangeTimepointWeightingToSumInFrame10Test(bundle,appGlobals));
		tasks.append(new SelectFrame50InTimepointWeightingTest(bundle,appGlobals));
		tasks.append(new ChangeTimepointWeightingToOccurenceInFrame50Test(bundle,appGlobals));
		tasks.append(new DeactivateTimepointWeightingTest(bundle,appGlobals));
		
		tasks.append(new LifetimeAnalysisTest(bundle,appGlobals));
		tasks.append(new NodeBetweennessCentralityAnalysisTest(bundle,appGlobals));
		tasks.append(new ChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new AutocorrelationErrorWithOccurenceWeightingTest(bundle,appGlobals));
		tasks.append(new ChangeWeightingToSumTest(bundle,appGlobals));
		tasks.append(new AutocorrelationErrorWithSumWeightingTest(bundle,appGlobals));
		
		tasks.append(new CorrelationFactorsTest(bundle,appGlobals));
		tasks.append(new DifferenceCorrelationFactorsTest(bundle,appGlobals));
		return tasks;
	}
	
	private TaskIterator createDifferenceMetanetworkAnalysisTasks(){
		TaskIterator tasks = new TaskIterator();
		tasks.append(new DifferenceChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new DifferenceChangeWeightingToSumTest(bundle,appGlobals));
		tasks.append(new DifferenceChangeWeightingToOccurenceTest(bundle,appGlobals));
		tasks.append(new DifferenceActivateTimepointWeightingInFrame10Test(bundle,appGlobals));
		tasks.append(new DifferenceChangeTimepointWeightingToSumInFrame10Test(bundle,appGlobals));
		tasks.append(new DifferenceSelectFrame50InTimepointWeightingTest(bundle,appGlobals));
		tasks.append(new DifferenceChangeTimepointWeightingToOccurenceInFrame50Test(bundle,appGlobals));
		tasks.append(new DifferenceDeactivateTimepointWeightingTest(bundle,appGlobals));
		return tasks;
	}
	
	public static Properties getProperties(){
		Properties p = new Properties();
		p.setProperty(ServiceProperties.COMMAND_NAMESPACE, CyActivator.APP_TEST_NAMESPACE);
		p.setProperty(ServiceProperties.COMMAND,"runTests");
		return p;
	}
	
}

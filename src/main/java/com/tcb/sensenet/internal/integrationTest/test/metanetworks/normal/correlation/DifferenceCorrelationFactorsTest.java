package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.correlation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.groups.nodes.AminoAcidGroupDefinition;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.integrationTest.test.AbstractIntegrationTestTask;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ActionCorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.task.create.factories.ActionCreateMetaNetworkTaskFactory;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateSingleFrameWeightingTaskFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.OldMatImporter;
import com.tcb.common.util.ListFilter;



public class DifferenceCorrelationFactorsTest extends AbstractIntegrationTestTask {

		
	public DifferenceCorrelationFactorsTest(Bundle bundle, AppGlobals appGlobals){
		super(bundle, appGlobals);
	}
	
	
	@Override
	protected void runTest(TaskMonitor taskMonitor) throws IOException {
		TaskIterator tasks = new TaskIterator();
		
		MetaNetwork refMetaNetwork = ListFilter.singleton(
				appGlobals.state.metaNetworkManager.getData().values()
				.stream()
				.filter(m -> m.getSharedDataRow()
						.get(DefaultColumns.SHARED_NAME, String.class)
						.equals("#ref-test-network"))
				.collect(Collectors.toList())).get();
		EdgeMappingMethod mappingMethod = EdgeMappingMethod.NAME;
		RowWriter edgeTableWriter = new EdgeCorrelationFactorsWriter();
		
		CorrelationFactorsTaskConfig config = CorrelationFactorsTaskConfig.create(
				EdgeCorrelationMethod.DIFFERENCE_MUTUAL_INFORMATION,
				FrameWeightMethod.SUM,
				Optional.of(refMetaNetwork),
				Optional.of(mappingMethod),
				edgeTableWriter
				);
		
		tasks = new TaskIterator();
		
		tasks.append(new ActionCorrelationFactorsTaskFactory(appGlobals)
				.createTaskIterator(config));
		appGlobals.synTaskManager.execute(tasks);
		
		checkHasSucceeded();
	}
	
	private void checkHasSucceeded() {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		
		checkNodeColumnData(AppColumns.CORRELATION_FACTOR, rootNetwork, getReference());
		checkEdgeColumnData(AppColumns.CORRELATION_FACTOR, rootNetwork, getReference());
	}
	

	@Override
	public TestReference getReference() {
		return TestReference.DIFFERENCE_CORRELATION_FACTOR;
	}		
}

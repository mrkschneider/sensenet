package com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.create;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.osgi.framework.Bundle;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.groups.nodes.AminoAcidGroupDefinition;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.integrationTest.test.AbstractIntegrationTestTask;
import com.tcb.sensenet.internal.integrationTest.test.reference.TestReference;
import com.tcb.sensenet.internal.task.create.factories.ActionCreateMetaNetworkTaskFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.OldMatImporter;



public class ImportMetaNetworkTest extends AbstractIntegrationTestTask {

	private static final String matPathString = "test.mat";
	
		
	public ImportMetaNetworkTest(Bundle bundle, AppGlobals appGlobals){
		super(bundle,appGlobals);
	}
	
	protected String getNetworkName(){
		return "test-network";
	}
	
	@Override
	protected void runTest(TaskMonitor taskMonitor) throws Exception {
		ImportConfig config = getTestImportConfig();
		TaskIterator tasks = new TaskIterator();
		tasks.append(new ActionCreateMetaNetworkTaskFactory(appGlobals)
				.createTaskIterator(config));
		appGlobals.synTaskManager.execute(tasks);
		checkHasSucceeded();
	}
	
	private ImportConfig getTestImportConfig() throws IOException {
		Double cutoff = 0.1;
		String name = getNetworkName();
		Columns cutoffColumn = AppColumns.OCCURRENCE;
		NodeGroupDefinition groupDefinitions = new AminoAcidGroupDefinition();
		Boolean shouldCreateVisualStyle = true;
		ImportConfig c = ImportConfig.create(
				createInteractionImporter(),
				name,
				cutoff,
				cutoffColumn,
				groupDefinitions,
				shouldCreateVisualStyle
				);
		return c;
	}
	
	protected InteractionImporter createInteractionImporter(){
		Path matPath = Paths.get(getTestDirectory(),matPathString);
		InteractionImporter importer = new OldMatImporter(matPath);
		return importer;
	}

	private void checkHasSucceeded() throws Exception {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		
		checkEdgeColumnData(AppColumns.WEIGHT, rootNetwork, getReference());
		checkEdgeColumnData(AppColumns.AVERAGE_INTERACTIONS, rootNetwork, getReference());
		checkEdgeColumnData(AppColumns.STANDARD_DEVIATION, rootNetwork, getReference());
		
	}


	@Override
	public TestReference getReference() {
		return TestReference.SUM_WEIGHTED_TIMELINE;
	}		
}

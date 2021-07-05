package com.tcb.sensenet.internal.integrationTest.test.reference;

import java.nio.file.Paths;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkManagerAdapter;

public class WriteTestReferenceTask extends AbstractTask {


	private CyApplicationManagerAdapter applicationManager;
	private CyRootNetworkManagerAdapter rootNetworkManager;
	private TestReference testReference;

	public WriteTestReferenceTask(TestReference testReference,
			CyApplicationManagerAdapter applicationManager,
			CyRootNetworkManagerAdapter rootNetworkManager){
		this.testReference = testReference;
		this.applicationManager = applicationManager;
		this.rootNetworkManager = rootNetworkManager;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		CyNetworkAdapter network = applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = rootNetworkManager.getRootNetwork(network);
		testReference.writeAllData(rootNetwork);
		System.out.println(String.format(
				"Wrote TestReference %s to %s"
				, testReference.toString(),
				Paths.get(testReference.getBaseResourcePath()).toAbsolutePath().toString()));
	}

}

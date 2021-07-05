package com.tcb.sensenet.internal.task.protectNetwork;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.protectNetwork.ProtectNetworkUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkManagerAdapter;

public class ProtectNetworkTask extends AbstractTask {

	private CyRootNetworkManagerAdapter rootNetworkManager;
	private CyApplicationManagerAdapter applicationManager;

	public ProtectNetworkTask(CyApplicationManagerAdapter applicationManager,
			CyRootNetworkManagerAdapter rootNetworkManager){
		this.applicationManager = applicationManager;
		this.rootNetworkManager = rootNetworkManager;
	}
	
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		CyNetworkAdapter network = applicationManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = rootNetworkManager.getRootNetwork(network);
		List<CyNode> nodes = rootNetwork.getNodeList();
		List<CyEdge> edges = rootNetwork.getEdgeList();
		CyNetworkAdapter protectNetwork = rootNetwork.addSubNetwork(nodes, edges);
		
		ProtectNetworkUtil.setAsProtectNetwork(protectNetwork);
	}

}

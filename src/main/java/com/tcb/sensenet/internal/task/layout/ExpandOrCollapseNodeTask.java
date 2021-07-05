package com.tcb.sensenet.internal.task.layout;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.View;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.view.MetaNetworkView;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;

public class ExpandOrCollapseNodeTask extends AbstractTask {
	
	private MetaNetworkViewManager groupManager;
	private View<CyNode> nodeView;
	private CyNetworkViewAdapter networkView;
	private MetaNetworkManager metaNetworkManager;

	public ExpandOrCollapseNodeTask(
			View<CyNode> nodeView, 
			CyNetworkViewAdapter networkView,
			MetaNetworkManager metaNetworkManager,
			MetaNetworkViewManager groupManager){
		this.nodeView = nodeView;
		this.networkView = networkView;
		this.groupManager = groupManager;
		this.metaNetworkManager = metaNetworkManager;
	}

	public void run(TaskMonitor arg0) throws Exception {
		CyNetworkAdapter network = new CyNetworkAdapter(networkView
				.getAdaptedNetworkView().getModel());
		MetaNetwork metaNetwork = metaNetworkManager.get(network);
		MetaNetworkView metaNetworkView = groupManager.get(metaNetwork);
		
		CyNode clickedNode = nodeView.getModel();

		Long headSuid = metaNetwork.getHiddenRow(clickedNode)
				.getMaybe(AppColumns.METANODE_SUID, Long.class)
				.orElse(null);
		
		if(headSuid==null){
			metaNetworkView.expand(clickedNode,network);
		} else {
			CyNode headNode = metaNetwork.getNode(headSuid);
			metaNetworkView.collapse(headNode, network);
		}
	}
}


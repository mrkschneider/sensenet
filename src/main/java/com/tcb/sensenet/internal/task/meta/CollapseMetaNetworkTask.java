package com.tcb.sensenet.internal.task.meta;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.view.MetaNetworkView;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class CollapseMetaNetworkTask extends AbstractTask {

	
	private MetaNetworkManager metaNetworkManager;
	private MetaNetworkViewManager metaNetworkViewManager;

	public CollapseMetaNetworkTask(MetaNetworkManager metaNetworkManager,
			MetaNetworkViewManager metaNetworkViewManager){
		this.metaNetworkManager = metaNetworkManager;
		this.metaNetworkViewManager = metaNetworkViewManager;
	}

	@Override
	public void run(TaskMonitor arg0) throws Exception {
		CyNetworkAdapter network = metaNetworkManager.getCurrentNetwork();
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		MetaNetworkView metaNetworkView = metaNetworkViewManager.get(metaNetwork);
		metaNetworkView.collapseAll(network);
	}

}

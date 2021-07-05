package com.tcb.sensenet.internal.task.view;

import java.util.List;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class SetActiveInteractionTypesTask extends AbstractTask {

	private CyApplicationManagerAdapter applicationManager;
	private List<String> newSelectedInteractionTypes;
	private MetaNetworkManager metaNetworkManager;
	
	public SetActiveInteractionTypesTask(
			List<String> newSelectedInteractionTypes,
			CyApplicationManagerAdapter applicationManager,
			MetaNetworkManager metaNetworkManager){
		this.newSelectedInteractionTypes = newSelectedInteractionTypes;
		this.applicationManager = applicationManager;
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		CyNetworkAdapter network = applicationManager.getCurrentNetwork();
		MetaNetwork metaNetwork = metaNetworkManager.get(network);
		
		metaNetwork.getHiddenDataRow().set(AppColumns.SELECTED_INTERACTION_TYPES, newSelectedInteractionTypes);
	}

}

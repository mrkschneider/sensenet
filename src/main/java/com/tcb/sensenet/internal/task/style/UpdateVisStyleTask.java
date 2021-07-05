package com.tcb.sensenet.internal.task.style;

import java.util.Collection;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;

public class UpdateVisStyleTask extends AbstractTask {
	
	private VisualMappingManager visualMappingManager;
	private CyNetworkViewManagerAdapter networkViewManager;
	private MetaNetworkManager metaNetworkManager;

	public UpdateVisStyleTask(MetaNetworkManager metaNetworkManager,
			VisualMappingManager visualMappingManager,
			CyNetworkViewManagerAdapter networkViewManager){
		this.metaNetworkManager = metaNetworkManager;
		this.visualMappingManager = visualMappingManager;
		this.networkViewManager = networkViewManager;

	}
	
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		tskMon.setStatusMessage("Updating visual styles...");
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
	
		for(CyNetworkAdapter network:metaNetwork.getSubNetworks()){
			Collection<CyNetworkView> networkViews = 
					networkViewManager.getNetworkViews(network);
			networkViews.forEach(v -> updateVisualStyle(v));
		}
		
		 		
	}
	
	private void updateVisualStyle(CyNetworkView networkView){
		VisualStyle style = visualMappingManager.getVisualStyle(networkView);
		style.apply(networkView);
	}
		
}

package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.state;

import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.StructureViewerPanel;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;

public class ViewerPanelStateManager extends AbstractStateManager<StructureViewerPanel> {

	private StructureViewerManager viewerManager;
	private CyApplicationManagerAdapter applicationManager;
	private MetaNetworkManager metaNetworkManager;

	public ViewerPanelStateManager(MetaNetworkManager metaNetworkManager, StructureViewerManager viewerManager){
		this.viewerManager = viewerManager;
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		
		//StructureViewerPanel p = this.getRegisteredObject();
				
		if(viewerIsActive() && modelIsActive(metaNetwork)){
			// Do nothing, button is enabled by default
		} else {

		}
						
	}
	
	private Boolean viewerIsActive(){
		return viewerManager.hasViewer() && viewerManager.getViewer().isActive();
	}
	
	private Boolean modelIsActive(MetaNetwork metaNetwork){
		return viewerManager.getModels().containsKey(metaNetwork);		
	}

}

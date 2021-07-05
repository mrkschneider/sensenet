package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.state;

import java.util.Optional;

import javax.swing.JButton;

import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.ViewerStatusPanel;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.structureViewer.StructureModel;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;

public class ViewerStatusPanelStateManager extends AbstractStateManager<ViewerStatusPanel> {

	private StructureViewerManager viewerManager;
	private MetaNetworkManager metaNetworkManager;

	public ViewerStatusPanelStateManager(
			MetaNetworkManager metaNetworkManager,
			StructureViewerManager viewerManager){
		this.metaNetworkManager = metaNetworkManager;
		this.viewerManager = viewerManager;
	}
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
		
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		
		ViewerStatusPanel p = this.getRegisteredObject();
		
		JButton addModelButton = p.getAddModelButton();
		JButton removeModelButton = p.getRemoveModelButton();
		JButton syncColorsButton = p.getTransferColorsButton();
		JButton pauseLinkButton = p.getPauseLinkButton();
		
		if(viewerIsActive()){
			p.setViewerConnected(true);
		} else {
			p.setViewerConnected(false);
			addModelButton.setEnabled(false);
		}
			
		if(viewerIsActive() && modelIsActive(metaNetwork)){
			StructureModel model = viewerManager.getModels().get(metaNetwork);
			Optional<String> name = Optional.of(model.getName());
			p.setConnectedModelName(name);
		} else {
			p.setConnectedModelName(Optional.empty());
			removeModelButton.setEnabled(false);
			syncColorsButton.setEnabled(false);
			pauseLinkButton.setEnabled(false);
			return;
		}
		
		StructureModel model = viewerManager.getModels().get(metaNetwork);
		p.setPauseLinkButtonText(model.isPaused());
				
	}
	
	private Boolean viewerIsActive(){
		return viewerManager.hasViewer() && viewerManager.getViewer().isActive();
	}
	
	private Boolean modelIsActive(MetaNetwork metaNetwork){
		return viewerManager.getModels().containsKey(metaNetwork);		
	}

}

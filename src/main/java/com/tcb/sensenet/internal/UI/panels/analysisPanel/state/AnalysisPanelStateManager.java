package com.tcb.sensenet.internal.UI.panels.analysisPanel.state;

import java.util.Optional;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.AnalysisPanel;
import com.tcb.sensenet.internal.UI.util.ContainerUtil;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.cytoscape.cyLib.UI.state.AbstractStateManager;

public class AnalysisPanelStateManager extends AbstractStateManager<AnalysisPanel> {

	private MetaNetworkManager metaNetworkManager;

	public AnalysisPanelStateManager(
			MetaNetworkManager metaNetworkManager){
		this.metaNetworkManager = metaNetworkManager;
	}
	
	@Override
	public void updateState() {
		if(!metaNetworkManager.currentNetworkBelongsToMetaNetwork()) return;
	
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		
		AnalysisPanel p = this.getRegisteredObject();
		
		Optional<String> aggregationModeEntry = metaNetwork.getHiddenDataRow().getMaybe(
				AppColumns.AGGREGATION_MODE,String.class);
		if(!aggregationModeEntry.isPresent()) return;
		
		TimelineWeightMethod aggregationMode = TimelineWeightMethod.valueOf(aggregationModeEntry.get());
				
		/*if(!aggregationMode.equals(TimeMode.TIMELINE)){
			ContainerUtil.setAllEnabled(p.getShortestPathsPanel(),false);
			p.getClusterPanel().getRunSingleClusteringButton().setEnabled(false);
			p.getClusterPanel().getRunTreeClusteringButton().setEnabled(false);
		}*/
		
		
	}
	
}

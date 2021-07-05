package com.tcb.sensenet.internal.util;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.state.AnalysisPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.appPanel.state.AppPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.state.ShowInteractionsPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.state.ViewerPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.state.ViewerStatusPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.AggregationModeControlsPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.AggregationModeSelectionPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.SelectedClusterPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.SelectedFramePanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.WeightPanelStateManager;

public class UIStateManagers {
	public WeightPanelStateManager weightPanelStateManager;
	public AggregationModeSelectionPanelStateManager aggregationModeSelectionPanelStateManager;
	public ShowInteractionsPanelStateManager showInteractionsPanelStateManager;
	public SelectedFramePanelStateManager selectedFramePanelStateManager;
	public AggregationModeControlsPanelStateManager aggregationModeControlsPanelStateManager;
	public SelectedClusterPanelStateManager selectedClusterPanelStateManager;
	public ViewerStatusPanelStateManager viewerStatusPanelStateManager;
	public ViewerPanelStateManager viewerPanelStateManager;
	public AppPanelStateManager appPanelStateManager;
	public AnalysisPanelStateManager analysisPanelStateManager;
	
	public UIStateManagers(
			WeightPanelStateManager weightPanelStateManager,
			AggregationModeSelectionPanelStateManager aggregationModeSelectionPanelStateManager,
			ShowInteractionsPanelStateManager showInteractionsPanelStateManager,
			SelectedFramePanelStateManager selectedFramePanelStateManager,
			SelectedClusterPanelStateManager selectedClusterPanelStateManager,
			AggregationModeControlsPanelStateManager aggregationModeControlsPanelStateManager,
			ViewerPanelStateManager viewerPanelStateManager,
			ViewerStatusPanelStateManager structureViewerPanelStateManager,
			AppPanelStateManager appPanelStateManager,
			AnalysisPanelStateManager analysisPanelStateManager
			){
		this.weightPanelStateManager = weightPanelStateManager;
		this.aggregationModeSelectionPanelStateManager = aggregationModeSelectionPanelStateManager;
		this.showInteractionsPanelStateManager = showInteractionsPanelStateManager;
		this.selectedFramePanelStateManager = selectedFramePanelStateManager;
		this.selectedClusterPanelStateManager = selectedClusterPanelStateManager;
		this.aggregationModeControlsPanelStateManager = aggregationModeControlsPanelStateManager;
		this.viewerPanelStateManager = viewerPanelStateManager;
		this.viewerStatusPanelStateManager = structureViewerPanelStateManager;
		this.appPanelStateManager = appPanelStateManager;
		this.analysisPanelStateManager = analysisPanelStateManager;
	}
}

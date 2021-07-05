package com.tcb.sensenet.internal.task.plot;

import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.panels.resultPanel.state.ResultPanelManager;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.plot.LinePlot;
import com.tcb.sensenet.internal.plot.LinePlotType;
import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class PlotSelectedEdgesTask extends AbstractTask {
	
	private AppGlobals appGlobals;
	private PlotSelectedEdgesTaskConfig config;
		
	public PlotSelectedEdgesTask(
			AppGlobals appGlobals, PlotSelectedEdgesTaskConfig config){
		this.appGlobals = appGlobals;
		this.config = config;
	}

	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		List<LinePlot> plots = createPlots();
		ResultPanel panel = appGlobals.resultPanelManager.getResultPanel();
		panel.clear();
		for(Plot p:plots){
			panel.showPlot(p,p.getPlotSubTitle());
		}
	}
		
	private List<LinePlot> createPlots(){
		List<CyEdge> selectedEdges = getSelectedEdges();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager
				.getCurrentMetaNetwork();
		FrameWeightMethod weightMethod = config.getWeightMethod();
		Integer blocks = config.getBlocks();
		LinePlotType type = config.getPlotType();
		
		List<LinePlot> plots = selectedEdges.stream()
				.map(e -> type.createPlot(e,metaNetwork,weightMethod,blocks, appGlobals))
				.collect(Collectors.toList());
		return plots;
	}
	
	private List<CyEdge> getSelectedEdges() {
		List<CyEdge> selectedEdges = config.getEdgeSelection();
		if(selectedEdges.isEmpty()){
			throw new IllegalArgumentException("Nothing to analyse. Please select one or more edges.");
		}
		return selectedEdges;
	}

}

package com.tcb.sensenet.internal.task.plot;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;
import com.tcb.cluster.ClusterTree;
import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.UI.util.TextPanel;
import com.tcb.sensenet.internal.analysis.cluster.ClusterAnalysis;
import com.tcb.sensenet.internal.analysis.cluster.ClusterReplicaInformation;
import com.tcb.sensenet.internal.analysis.cluster.NestedListSerializer;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.plot.ClusterTimelinePlot;
import com.tcb.sensenet.internal.plot.ClusterTreeErrorPlot;
import com.tcb.sensenet.internal.plot.ClusterTreePlot;
import com.tcb.sensenet.internal.plot.LinePlot;
import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.sensenet.internal.plot.histogram.ClusteringPlot;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.cytoscape.cyLib.log.LogBuilder;


public class ShowClusterTreeAnalysisTask extends AbstractTask {
	
	private AppGlobals appGlobals;
		
	public ShowClusterTreeAnalysisTask(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		ResultPanel panel = appGlobals.resultPanelManager.getResultPanel();
		
		List<ClusterAnalysis> analyses = getAnalyses(metaNetwork);
		
		ClusterAnalysis selectedClustering = new ClusterAnalysis(
				appGlobals.state.clusteringStoreManager.get(metaNetwork));
		
		panel.clear();
		panel.showPlot(createCompensatedFluxPlot(analyses,selectedClustering), "Cluster tree analysis");
		panel.showPlot(createSumOfSquaredErrorsPlot(analyses,selectedClustering), "Cluster error analysis");
					
		ClusterAnalysisPlotter plotter = new ClusterAnalysisPlotter(appGlobals);
		
		plotter.plot(panel, selectedClustering, metaNetwork);
	}
	
	private List<ClusterAnalysis> getAnalyses(MetaNetwork metaNetwork){
		List<List<Cluster>> clusters = appGlobals.state.treeClusteringStoreManager.get(metaNetwork);
		return clusters.stream()
				.map(l -> new ClusterAnalysis(l))
				.collect(ImmutableList.toImmutableList());
	}
		
	private Plot createCompensatedFluxPlot(List<ClusterAnalysis> analyses, ClusterAnalysis selectedClustering) {
		List<Double> flux = analyses.stream()
				.map(a -> a.getCompensatedFlux())
				.collect(ImmutableList.toImmutableList());
		LinePlot plot = new ClusterTreePlot(flux, "Compensated flux", "Flux");
		int selectedClusterCount = selectedClustering.getClusterCount();
		plot.addVline((double)selectedClusterCount,Color.RED,plot.getDashedStroke());
		return plot;
	}
	
	private Plot createSumOfSquaredErrorsPlot(List<ClusterAnalysis> analyses, ClusterAnalysis selectedClustering) {
		List<Double> sse = analyses.stream()
				.map(a -> a.getSumOfSquaredErrors())
				.collect(ImmutableList.toImmutableList());
		LinePlot plot = new ClusterTreeErrorPlot(sse, "Sum of squared errors", "SSE");
		int selectedClusterCount = selectedClustering.getClusterCount();
		plot.addVline((double)selectedClusterCount,Color.RED,plot.getDashedStroke());
		return plot;
	}
	
	
	
}

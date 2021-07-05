package com.tcb.sensenet.internal.task.plot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.UI.util.TextPanel;
import com.tcb.sensenet.internal.analysis.cluster.ClusterAnalysis;
import com.tcb.sensenet.internal.analysis.cluster.ClusterReplicaInformation;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.plot.ClusterTimelinePlot;
import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.sensenet.internal.plot.histogram.ClusteringPlot;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class ClusterAnalysisPlotter {
	
	private AppGlobals appGlobals;

	public ClusterAnalysisPlotter(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public void plot(
			ResultPanel panel,
			ClusterAnalysis analysis,
			MetaNetwork metaNetwork) throws Exception {
		ClusteringPlot plot = createSummaryPlot(analysis);
		TableView table = createTable(analysis);
								
		panel.showPlot(plot, "Cluster summary");
		panel.showPlot(createTimelinePlot(analysis), "Cluster timeline");
		panel.showTable(table, "Cluster table");
	}
			
	private ClusteringPlot createSummaryPlot(ClusterAnalysis analysis){		
		ClusteringPlot plot = new ClusteringPlot(analysis);
		return plot;
	}
	
	private Plot createTimelinePlot(ClusterAnalysis analysis){
		Map<Integer,Integer> timeline = analysis.getClusterTimeline();
		return new ClusterTimelinePlot(timeline,"Cluster timeline");		
	}
		
	private TableView createTable(ClusterAnalysis analysis){
		List<List<String>> clusterFrames = analysis.getClusters()
				.stream()
				.map(c -> c.getData())
				.collect(Collectors.toList());
		List<Integer> clusterCentroids = analysis.getCentroids();
		Integer clusterCount = analysis.getClusterCount();
		List<Integer> memberCount = analysis.getClusterSizes();
		String[] columnNames = {"cluster id","centroid frame","member count","member frames"};
		Object[][] data = new Object[clusterCount][columnNames.length];
		for(int i=0;i<clusterCount;i++){
			data[i][0] = i;
			data[i][1] = clusterCentroids.get(i);
			data[i][2] = memberCount.get(i);
			data[i][3] = clusterFrames.get(i).stream()
					.map(s -> Integer.valueOf(s))
					.collect(ImmutableList.toImmutableList());
		}
		TableView table = new TableView(columnNames,data, appGlobals.fileUtil);
		return table;
	}
}

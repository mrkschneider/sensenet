package com.tcb.sensenet.internal.task.correlation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.table.CyIdentifiableTableView;
import com.tcb.sensenet.internal.UI.table.DefaultTableViewFactory;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.TimelineCorrelationAnalysis;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.aifgen.importer.TimelineType;

public class ShowTimelineCorrelationTask extends AbstractTask {

	private TimelineCorrelationTaskConfig config;
	private AppGlobals appGlobals;

	public ShowTimelineCorrelationTask(
			TimelineCorrelationTaskConfig config, 
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		CyEdge selectedEdge = config.getEdgeSelection().getEdge();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();

		LogBuilder log = TaskLogUtil.createTaskLog(metaNetwork, config.getTaskLogType(),
				appGlobals.state.logManager);
		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config);
		
		ResultPanel panel = appGlobals.resultPanelManager.getResultPanel();
		panel.clear();
		
		List<CyEdge> edges = network.getEdgeList().stream()
				.filter(e -> !selectedEdge.getSUID().equals(e.getSUID()))
				.collect(Collectors.toList());
							
		TimelineCorrelationAnalysis analysis = calcCorrelations(
				selectedEdge, edges, metaNetwork, config.getWeightMethod());
		
		TaskLogUtil.finishTaskLog(log);
		
		TableView edgeTableView = createEdgeTableView(analysis,edges,metaNetwork,network);
				
		panel.addTopPanel(log);
		panel.showTable(edgeTableView, "Analysis results");
				
	}
	
	private CyIdentifiableTableView createEdgeTableView(
			TimelineCorrelationAnalysis analysis,
			List<CyEdge> edges,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network){
				
		List<String> columnNames = new ArrayList<>();
		List<List<?>> values = new ArrayList<>();
				
		columnNames.add("pearson coef.");
		values.add(analysis.getCorrelations());
		
		columnNames.add("mutual information");
		values.add(analysis.getMutualInformations());
		
		CyIdentifiableTableView edgeTable = new DefaultTableViewFactory().create(
				edges, columnNames, values, metaNetwork,
				network, appGlobals.fileUtil);
		return edgeTable;
	}
		
	private TimelineCorrelationAnalysis calcCorrelations(
			CyEdge selectedEdge,
			List<CyEdge> edges,
			MetaNetwork metaNetwork,
			FrameWeightMethod weightMethod){
		NetworkMetaTimelineFactory metaTimelineFactory = 
				getMetaTimelineFactory(metaNetwork, weightMethod);		
		MetaTimeline selectedTimeline = metaTimelineFactory
				.create(selectedEdge, metaNetwork);
		List<MetaTimeline> metaTimelines = edges.stream()
				.map(e -> metaTimelineFactory.create(e, metaNetwork))
				.collect(Collectors.toList());
		return TimelineCorrelationAnalysis.calculate(selectedTimeline, metaTimelines);		
	}
	
	private NetworkMetaTimelineFactory getMetaTimelineFactory(MetaNetwork metaNetwork, FrameWeightMethod weightMethod){
		TimelineType type = metaNetwork.getTimelineType();
		return appGlobals.metaTimelineFactoryManager.getFactory(weightMethod, type);
	}
	
}

package com.tcb.sensenet.internal.task.meta;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.table.CyIdentifiableTableView;
import com.tcb.sensenet.internal.UI.table.DefaultTableViewFactory;
import com.tcb.sensenet.internal.UI.table.TableView;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.aifgen.importer.TimelineType;

public class ShowMetaTimelinesTask extends AbstractTask {

	private ShowMetaTimelinesTaskConfig config;
	private AppGlobals appGlobals;

	public ShowMetaTimelinesTask(
			ShowMetaTimelinesTaskConfig config, 
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();

		LogBuilder log = TaskLogUtil.createTaskLog(metaNetwork, config.getTaskLogType(), appGlobals.state.logManager);
		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config);
							
		TimelineType timelineType = metaNetwork.getTimelineType();
		NetworkMetaTimelineFactory metaTimelineFactory = appGlobals.metaTimelineFactoryManager
				.getFactory(config.getWeightMethod(), timelineType);
		List<CyEdge> edges = metaNetwork.getEdges();
		
		List<MetaTimeline> metaTimelines = metaNetwork.getEdges()
				.stream().parallel()
				.map(e -> metaTimelineFactory.create(e, metaNetwork))
				.collect(ImmutableList.toImmutableList());
				
		TaskLogUtil.finishTaskLog(log);
		
		ResultPanel panel = appGlobals.resultPanelManager.getResultPanel();
		panel.clear();
		
		TableView edgeTableView = createEdgeTableView(metaTimelines,edges,metaNetwork,network);
				
		panel.addTopPanel(log);
		panel.showTable(edgeTableView, "Analysis results");
				
	}
	
	private CyIdentifiableTableView createEdgeTableView(
			List<MetaTimeline> metaTimelines,
			List<CyEdge> edges,
			MetaNetwork metaNetwork,
			CyNetworkAdapter network){
		
		List<List<Double>> timelines = metaTimelines.stream()
				.map(m -> m.asDoubles())
				.collect(ImmutableList.toImmutableList());
		
		List<String> columnNames = new ArrayList<>();
		List<List<?>> values = new ArrayList<>();
				
		columnNames.add("timeline");
		values.add(timelines);
				
		CyIdentifiableTableView edgeTable = new DefaultTableViewFactory().create(
				edges, columnNames, values,
				metaNetwork,
				network, appGlobals.fileUtil);
		return edgeTable;
	}
		
}

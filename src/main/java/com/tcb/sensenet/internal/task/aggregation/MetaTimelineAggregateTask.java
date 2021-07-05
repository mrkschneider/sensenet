package com.tcb.sensenet.internal.task.aggregation;

import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.MetaTimelineAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.TableWriter;
import com.tcb.sensenet.internal.log.TaskLogManager;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.task.AbstractResultTask;
import com.tcb.sensenet.internal.task.ProgressUpdater;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.aifgen.importer.TimelineType;



public class MetaTimelineAggregateTask extends AbstractResultTask {
	
	private MetaNetworkManager metaNetworkManager;
	private MetaTimelineFactoryManager metaTimelineFactoryManager;
	private MetaTimelineAggregationTaskConfig config;
	private TaskLogManager logManager;
	
	public MetaTimelineAggregateTask(
			ObjMap results,
			MetaNetworkManager metaNetworkManager,
			MetaTimelineFactoryManager metaTimelineFactoryManager,
			TaskLogManager logManager,
			MetaTimelineAggregationTaskConfig config){
		super(results);
		this.metaNetworkManager = metaNetworkManager;
		this.metaTimelineFactoryManager = metaTimelineFactoryManager;
		this.logManager = logManager;
		this.config = config;
	}
	
	@Override
	public ObjMap start(TaskMonitor tskMon) throws Exception {
		tskMon.setStatusMessage("Aggregating...");
		MetaNetwork metaNetwork = metaNetworkManager.getCurrentMetaNetwork();
		CyNetworkAdapter network = metaNetworkManager.getCurrentNetwork();
		
		LogBuilder log = TaskLogUtil.createTaskLog(metaNetwork, config.getTaskLogType(), logManager);
		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config);
				
		List<CyEdge> edges = metaNetwork.getEdges();
		
		ProgressUpdater progressUpdater = ProgressUpdater.create(edges.size());
		
		TimelineType timelineType = metaNetwork.getTimelineType();
		
		NetworkMetaTimelineFactory metaTimelineFactory = metaTimelineFactoryManager.getFactory(
				config.getFrameWeightMethod(), timelineType);
		
		List<ObjMap> aggregatedCol = edges.stream().parallel()
				.map(e -> metaTimelineFactory.create(e,metaNetwork))
				.map(t -> aggregateColumn(t,config.getAggregator(),tskMon,progressUpdater))
				.collect(Collectors.toList());
		
		results.putOrReplace("aggregationResults", aggregatedCol);
		results.putOrReplace("aggregationEdges", edges);
		results.putOrReplace("aggregationNetwork", network);
		results.putOrReplace("aggregationMetanetwork", metaNetwork);
		
		new TableWriter().writeRows(edges, aggregatedCol, config.getRowWriter(), metaNetwork);
			
		TaskLogUtil.finishTaskLog(log);
		return results;
	}
			
	private ObjMap aggregateColumn(MetaTimeline metaTimeline, MetaTimelineAggregator aggregator,
					TaskMonitor taskMonitor, ProgressUpdater ticker){
		ObjMap result = aggregator.aggregate(metaTimeline);
		ticker.incr();
		ticker.update(taskMonitor);
		return result;
	}
		
}
	 
	



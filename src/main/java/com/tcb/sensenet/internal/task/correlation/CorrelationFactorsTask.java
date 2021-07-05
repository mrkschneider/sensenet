package com.tcb.sensenet.internal.task.correlation;

import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.nodes.NodeAdjacentEdgeColumnAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.nodes.NodeAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.CorrelationFactorsAnalysis;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.EdgeTimelineCorrelationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.MutualInformationCorrelationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.PearsonCorrelationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.difference.DifferenceEdgeTimelineCorrelationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.difference.DifferenceInformationCorrelationStrategy;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.ExpectedPMI;
import com.tcb.sensenet.internal.analysis.correlation.mutualInformation.pointwise.PointwiseMutualInformationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.map.edge.EdgeLocationMapper;
import com.tcb.sensenet.internal.map.edge.EdgeMapper;
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.map.edge.EdgeNameMapper;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.factories.CachingNetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.util.CancellableRunner;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.aifgen.importer.TimelineType;

public class CorrelationFactorsTask extends AbstractTask {
	private AppGlobals appGlobals;
	private CorrelationFactorsTaskConfig config;

	public CorrelationFactorsTask(CorrelationFactorsTaskConfig config, AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
				
		LogBuilder log = TaskLogUtil.createTaskLog(metaNetwork, config.getTaskLogType(),
				appGlobals.state.logManager);
		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config);
		
		CorrelationFactorsAnalysis analysisFactory =
				new CorrelationFactorsAnalysis();
		
		EdgeCorrelationStrategy correlationStrategy = getEdgeCorrelationStrategy();
		
		ObjMap analysis =
				CancellableRunner.run(
						() -> analysisFactory.analyse(
								correlationStrategy,
								network
								),
						() -> cancelled == true,
						() -> analysisFactory.cancel());
		
		@SuppressWarnings("unchecked")
		Map<CyEdge,Double> correlationFactors = analysis.get("correlationFactors",Map.class);
		RowWriter edgeWriter = config.getEdgeTableWriter();
		List<CyEdge> edges = metaNetwork.getEdges();
		for(CyEdge edge:edges){
			CyRowAdapter row = metaNetwork.getRow(edge);
			ObjMap m = new ObjMap();
			m.put("correlationFactor", correlationFactors.getOrDefault(edge,null));
			edgeWriter.write(row, m);
		}
				
		TaskLogUtil.finishTaskLog(log);
	}
	
	private EdgeCorrelationStrategy getEdgeCorrelationStrategy(){
		EdgeCorrelationMethod method = config.getCorrelationMethod();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		NetworkMetaTimelineFactory metaTimelineFactory = appGlobals.metaTimelineFactoryManager
				.getFactory(config.getWeightMethod(), metaNetwork.getTimelineType());
		metaTimelineFactory = new CachingNetworkMetaTimelineFactory(metaTimelineFactory);
		PointwiseMutualInformationStrategy pmi = new ExpectedPMI();
		switch(method){
		case MUTUAL_INFORMATION: return new EdgeTimelineCorrelationStrategy(
				new MutualInformationCorrelationStrategy(),
				metaNetwork,
				metaTimelineFactory);
		case PEARSON: return new EdgeTimelineCorrelationStrategy(
				new PearsonCorrelationStrategy(),
				metaNetwork,
				metaTimelineFactory);
		case DIFFERENCE_MUTUAL_INFORMATION:
			MetaNetwork refMetaNetwork = config.getReferenceMetaNetwork().get();
			EdgeMapper edgeMapper = getEdgeMapper(
					config.getEdgeMappingMethod().get(),
					refMetaNetwork);
			return new DifferenceEdgeTimelineCorrelationStrategy(
					new DifferenceInformationCorrelationStrategy(pmi), metaNetwork,
					refMetaNetwork, metaTimelineFactory, edgeMapper);
		default: throw new UnsupportedOperationException();
		}
	}
	
	private EdgeMapper getEdgeMapper(EdgeMappingMethod method, MetaNetwork refMetaNetwork){
		switch(method){
		case NAME: return EdgeNameMapper.create(refMetaNetwork);
		case LOCATION: return EdgeLocationMapper.create(refMetaNetwork);
		default: throw new IllegalArgumentException();
		}
	}
	
}

package com.tcb.sensenet.internal.task.path.centrality;

import java.util.Map;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.analysis.normalization.MinMaxNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.ZScoreNormalizationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.network.PrivateSubnetworkFactory;
import com.tcb.sensenet.internal.network.PrivateSubnetworkFactoryImpl;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedNodeBetweennessCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedNodeStressCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedPathLengthCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.InverseWeightDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.NegativeExponentialWeightDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.PassthroughEdgeDistanceStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.MaxNodePairsNormalizationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnMaxWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnMinWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.ColumnSumWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.EdgeCountAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.UniformWeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.AbsoluteValueNegativeValuesStrategy;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesStrategy;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.aifgen.importer.TimelineType;

public class WeightedNodeCentralityTask extends AbstractTask {

	private AppGlobals appGlobals;
	private WeightedNodeCentralityTaskConfig config;
	
	
	public WeightedNodeCentralityTask(
			WeightedNodeCentralityTaskConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
		this.cancelled = false;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		CyNetworkAdapter network = config.getNetwork();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		verifyState(metaNetwork);
		
		LogBuilder log = TaskLogUtil.createTaskLog(metaNetwork, config.getTaskLogType(),
				appGlobals.state.logManager);
		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config);

		NodeCentralityAnalyzer analyzer = getAnalyzer(network);
			
		Map<CyNode,ObjMap> centralities = analyzer.analyze(network);
		
		RowWriter writer = config.getRowWriter();
		
		ObjMap defaultM = new ObjMap();
		defaultM.put("centrality", null);
		
		for(CyNode node:metaNetwork.getNodes()){
			CyRowAdapter row = metaNetwork.getRow(node);
			ObjMap m = centralities.getOrDefault(node,defaultM);
			writer.write(row, m);
		}
		
		TaskLogUtil.finishTaskLog(log);
	}
	
	private NodeCentralityAnalyzer getAnalyzer(CyNetworkAdapter network){
		WeightAccumulationStrategy weightStrategy = getNodeWeightStrategy(network);
		EdgeDistanceStrategy distanceStrategy = getEdgeDistanceStrategy();
		NormalizationStrategy normalizationStrategy = getNormalizationStrategy();
		NegativeValuesStrategy negativeWeightStrategy = getNegativeWeightStrategy();
		PrivateSubnetworkFactory networkFactory = new PrivateSubnetworkFactoryImpl(appGlobals);
		switch(config.getCentralityType()){
		case STRESS: return new WeightedNodeStressCentralityAnalyzer(
				weightStrategy, distanceStrategy, negativeWeightStrategy);
		case BETWEENNESS: return new WeightedNodeBetweennessCentralityAnalyzer(
				weightStrategy, distanceStrategy, normalizationStrategy, negativeWeightStrategy);
		case PATH_LENGTH: return new WeightedPathLengthCentralityAnalyzer(
				networkFactory, weightStrategy, distanceStrategy,
				normalizationStrategy, negativeWeightStrategy);
		default: throw new UnsupportedOperationException();
		}		
	}
	
	private WeightAccumulationStrategy getNodeWeightStrategy(CyNetworkAdapter network){
		switch(config.getMultiEdgeWeightMode()){
		case UNIFORM: return new UniformWeightAccumulationStrategy();
		case EDGE_COUNT: return new EdgeCountAccumulationStrategy();
		case SUM: return new ColumnSumWeightAccumulationStrategy(config.getWeightColumnName().get(),network);
		case MAX: return new ColumnMaxWeightAccumulationStrategy(config.getWeightColumnName().get(),network);
		case MIN: return new ColumnMinWeightAccumulationStrategy(config.getWeightColumnName().get(),network);
		default: throw new UnsupportedOperationException();
		}
	}
	
	private EdgeDistanceStrategy getEdgeDistanceStrategy(){
		switch(config.getDistanceMode()){
		case NONE: return new PassthroughEdgeDistanceStrategy();
		case INVERSE: return new InverseWeightDistanceStrategy();
		case NEGATIVE_EXPONENTIAL: return new NegativeExponentialWeightDistanceStrategy(); 
		default: throw new UnsupportedOperationException();
		}
	}
	
	private NormalizationStrategy getNormalizationStrategy(){
		switch(config.getNormalizationMode()){
		case NONE: return new NoNormalizationStrategy();
		case MIN_MAX: return new MinMaxNormalizationStrategy();
		case MAX_NODE_PAIRS: return new MaxNodePairsNormalizationStrategy();
		case Z_SCORE: return new ZScoreNormalizationStrategy();
		default: throw new UnsupportedOperationException();
		}
	}
	
	private NegativeValuesStrategy getNegativeWeightStrategy(){
		switch(config.getNegativeWeightMode()){
		case ABSOLUTE_VALUE: return new AbsoluteValueNegativeValuesStrategy();
		default: throw new UnsupportedOperationException();
		}
	}
		
	private void verifyState(MetaNetwork metaNetwork){

	}

	@Override
	public void cancel(){
		cancelled = true;
	}
	
	private boolean isCancelled(){
		return cancelled;
	}
	
	
	
	
	

}

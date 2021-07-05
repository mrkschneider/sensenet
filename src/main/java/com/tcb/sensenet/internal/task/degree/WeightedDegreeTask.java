package com.tcb.sensenet.internal.task.degree;

import java.util.Map;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.analysis.degree.NodeAdjacentEdgeColumnWeightedDegreeStrategy;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeAnalysis;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeStrategy;
import com.tcb.sensenet.internal.analysis.normalization.MinMaxNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NoNormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.NormalizationStrategy;
import com.tcb.sensenet.internal.analysis.normalization.ZScoreNormalizationStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedNodeBetweennessCentralityAnalyzer;
import com.tcb.sensenet.internal.path.analysis.centrality.WeightedNodeStressCentralityAnalyzer;
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

public class WeightedDegreeTask extends AbstractTask {

	private AppGlobals appGlobals;
	private WeightedDegreeTaskConfig config;
	
	
	public WeightedDegreeTask(
			WeightedDegreeTaskConfig config,
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

		ObjMap analysis = calculate(network);
		@SuppressWarnings("unchecked")
		Map<CyNode,Double> degrees = analysis.get("degrees",Map.class);		
		
		RowWriter writer = config.getRowWriter();
		
		for(CyNode node:metaNetwork.getNodes()){
			CyRowAdapter row = metaNetwork.getRow(node);
			ObjMap m = new ObjMap();
			m.put("degree",degrees.getOrDefault(node,null));
			writer.write(row, m);
		}
		
		TaskLogUtil.finishTaskLog(log);
	}
	
	private ObjMap calculate(CyNetworkAdapter network){
		NegativeValuesStrategy negativeWeightStrategy = getNegativeWeightStrategy();
		WeightedDegreeAnalysis fac = new WeightedDegreeAnalysis(
				getWeightedDegreeStrategy(negativeWeightStrategy),
				getNormalizationStrategy()
				);
		return fac.analyse(network);
	}
	
	
	
	private NegativeValuesStrategy getNegativeWeightStrategy(){
		switch(config.getNegativeWeightMode()){
		case ABSOLUTE_VALUE: return new AbsoluteValueNegativeValuesStrategy();
		default: throw new UnsupportedOperationException();
		}
	}
	
	private WeightedDegreeStrategy getWeightedDegreeStrategy(NegativeValuesStrategy negativeValuesStrategy){
		switch(config.getWeightedDegreeMode()){
		case ADJACENT_EDGE_WEIGHT_SUM: 
			return new NodeAdjacentEdgeColumnWeightedDegreeStrategy(
					config.getWeightColumnName(),
					negativeValuesStrategy
					);
		default: throw new UnsupportedOperationException();
		}
	}
	
	private NormalizationStrategy getNormalizationStrategy(){
		switch(config.getNormalizationMode()){
		case NONE: return new NoNormalizationStrategy();
		case MIN_MAX: return new MinMaxNormalizationStrategy();
		case Z_SCORE: return new ZScoreNormalizationStrategy();
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

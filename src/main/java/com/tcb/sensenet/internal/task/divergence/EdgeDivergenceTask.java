package com.tcb.sensenet.internal.task.divergence;

import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.analysis.divergence.JensenShannonDivergence;
import com.tcb.sensenet.internal.analysis.divergence.ReplicaDivergenceAnalysis;
import com.tcb.sensenet.internal.analysis.divergence.PopulationShiftDivergence;
import com.tcb.sensenet.internal.analysis.divergence.SymmetricKullbackLeiblerDivergence;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogManager;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.task.AbstractResultTask;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregateTask;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.util.ListUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.mdAnalysis.statistics.regression.Regression;

public class EdgeDivergenceTask extends AbstractResultTask {
	
	private AppGlobals appGlobals;
	private EdgeDivergenceTaskConfig config;

	public EdgeDivergenceTask(
			ObjMap results,
			AppGlobals appGlobals,
			EdgeDivergenceTaskConfig config) {
		super(results);
		this.appGlobals = appGlobals;
		this.config = config;
	}

	@Override
	public ObjMap start(TaskMonitor tskMon) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state
				.metaNetworkManager.getCurrentMetaNetwork();
		NetworkMetaTimelineFactory fac = 
				appGlobals.metaTimelineFactoryManager.getFactory(config.getWeightMethod(),
				metaNetwork.getTimelineType());

		CyEdge edge = config.getEdgeSelection().getEdge();
		DivergenceStrategy strategy = getStrategy();
		MetaTimeline metaTimeline = fac.create(edge, metaNetwork);
							
		ObjMap analysis = new ReplicaDivergenceAnalysis(strategy)
				.analyse(
						metaTimeline,
						config.getReplicaCount(),
						config.getConvergenceLimit());
		
		results.put("networkDivergences", analysis.getList("divergences",Double.class));
		results.put("networkDivergencesRegression",
				analysis.get("divergencesRegression",Regression.class));
		results.put("divergenceLabel",metaNetwork.getRow(edge)
				.get(AppColumns.LABEL, String.class));
		return results;
	}
	
	private DivergenceStrategy getStrategy() {
		switch(config.getDivergenceMethod()) {
		case JENSEN_SHANNON: return new JensenShannonDivergence();
		case SYMMETRICAL_KULLBACK_LEIBLER: return new SymmetricKullbackLeiblerDivergence();
		case POPULATION_SHIFT: return new PopulationShiftDivergence();
		default: throw new IllegalArgumentException("Unknown divergence method");
		}
	}
	
	

}

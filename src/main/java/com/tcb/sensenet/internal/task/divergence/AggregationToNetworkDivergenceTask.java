package com.tcb.sensenet.internal.task.divergence;

import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.log.TaskLogManager;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.task.AbstractResultTask;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregateTask;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.util.ListUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.mdAnalysis.statistics.regression.Regression;

public class AggregationToNetworkDivergenceTask extends AbstractResultTask {
	
	public AggregationToNetworkDivergenceTask(ObjMap results) {
		super(results);
	}

	@Override
	public ObjMap start(TaskMonitor tskMon) throws Exception {
		List<ObjMap> aggregationResults = results.getList("aggregationResults", ObjMap.class);
		List<List<Double>> divergencess = aggregationResults
			.stream()
			.map(o -> o.getList("divergences",Double.class))
			.collect(Collectors.toList());
		List<Regression> regressions = aggregationResults
				.stream()
				.map(o -> o.get("divergencesRegression",Regression.class))
				.collect(Collectors.toList());
		MetaNetwork metaNetwork = results.get("aggregationMetanetwork",MetaNetwork.class);

		List<CyEdge> edges = results.getList("aggregationEdges",CyEdge.class);
		int maxDivKey = ListUtil.maxKey(divergencess, l -> l.get(l.size()-1));
		results.put("networkDivergences", divergencess.get(maxDivKey));
		results.put("networkDivergencesRegression", regressions.get(maxDivKey));
		results.put("divergenceLabel",metaNetwork.getRow(edges.get(maxDivKey))
				.get(AppColumns.LABEL, String.class));
		return results;
	}
	
	

}

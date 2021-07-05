package com.tcb.sensenet.internal.task.cli.entropy;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.DivergenceAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.EntropyAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EntropyWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.ReplicaDivergenceAnalysisWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.analysis.divergence.JensenShannonDivergence;
import com.tcb.sensenet.internal.analysis.divergence.PopulationShiftDivergence;
import com.tcb.sensenet.internal.analysis.divergence.SymmetricKullbackLeiblerDivergence;
import com.tcb.sensenet.internal.analysis.entropy.EntropyMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.aggregation.factories.ActionMetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.task.aggregation.factories.MetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.divergence.DivergenceTaskConfig;
import com.tcb.sensenet.internal.task.divergence.factories.ActionNetworkDivergenceTaskFactory;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateAverageFrameWeightingTaskFactory;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.util.NullUtil;

public class EntropyTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="Frame weight")
	public String weightMethod;
		
	public EntropyTaskCLI(AppGlobals appGlobals){
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(weightMethod, "Weight method");
				
		FrameWeightMethod weightMethodV = EnumUtil.valueOfCLI
				(weightMethod,FrameWeightMethod.class);
		EntropyMethod method = EntropyMethod.SHANNON;
						
		MetaTimelineAggregatorConfig aggregatorConfig = 
				new EntropyAggregatorConfig(method);
		RowWriter writer = new EntropyWriter();
		
		MetaTimelineAggregationTaskConfig config =
			new MetaTimelineAggregationTaskConfig(
					aggregatorConfig, weightMethodV, writer, TaskLogType.ENTROPY);
		
		ObjMap results = new ObjMap();
		
		TaskIterator taskFac = 
				new MetaTimelineAggregationTaskFactory(appGlobals).createTaskIterator(
						results, config);
		
		return taskFac;
	}
		
}

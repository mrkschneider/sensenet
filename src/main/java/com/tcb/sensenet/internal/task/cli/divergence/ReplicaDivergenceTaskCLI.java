package com.tcb.sensenet.internal.task.cli.divergence;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.DivergenceAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.ReplicaDivergenceAnalysisWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.analysis.divergence.JensenShannonDivergence;
import com.tcb.sensenet.internal.analysis.divergence.PopulationShiftDivergence;
import com.tcb.sensenet.internal.analysis.divergence.SymmetricKullbackLeiblerDivergence;
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

public class ReplicaDivergenceTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="Frame weight")
	public String weightMethod;
	
	@Tunable(description="Divergence method")
	public String divergenceMethod;
	
	@Tunable(description="Convergence limit")
	public Double convergenceLimit;
	
	@Tunable(description="Blocks")
	public Integer blocks;
	
	public ReplicaDivergenceTaskCLI(AppGlobals appGlobals){
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(weightMethod, "Weight method");
		NullUtil.requireNonNull(divergenceMethod, "Divergence method");
		NullUtil.requireNonNull(convergenceLimit, "Convergence limit");
		NullUtil.requireNonNull(blocks, "Blocks");
		
		FrameWeightMethod weightMethodV = EnumUtil.valueOfCLI
				(weightMethod,FrameWeightMethod.class);
		DivergenceMethod divergenceMethodV = EnumUtil.valueOfCLI(divergenceMethod,
				DivergenceMethod.class);

		DivergenceStrategy divergenceStrategy = getDivergenceStrategy(divergenceMethodV);
				
		MetaTimelineAggregatorConfig aggregatorConfig = 
				new DivergenceAggregatorConfig(divergenceMethodV,divergenceStrategy,blocks,
						convergenceLimit);
		RowWriter writer = new ReplicaDivergenceAnalysisWriter();
		DivergenceTaskConfig config = 
				new DivergenceTaskConfig(
						aggregatorConfig,weightMethodV,writer,TaskLogType.DIVERGENCE);
		ObjMap results = new ObjMap();
		
		TaskIterator taskFac = 
				new ActionNetworkDivergenceTaskFactory(
						appGlobals).createTaskIterator(results,config);
		
		return taskFac;
	}
	
	private DivergenceStrategy getDivergenceStrategy(DivergenceMethod method){
		switch(method){
		case JENSEN_SHANNON: return new JensenShannonDivergence();
		case SYMMETRICAL_KULLBACK_LEIBLER: return new SymmetricKullbackLeiblerDivergence();
		case POPULATION_SHIFT: return new PopulationShiftDivergence();
		default: throw new IllegalArgumentException();	
		}
	}
	
}

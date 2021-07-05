package com.tcb.sensenet.internal.task.diffusion;

import java.util.Map;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.analysis.diffusion.DefaultWalkStrategy;
import com.tcb.sensenet.internal.analysis.diffusion.RandomWalk;
import com.tcb.sensenet.internal.analysis.diffusion.RandomWalkMode;
import com.tcb.sensenet.internal.analysis.diffusion.SymmetricTargetedWalkStrategy;
import com.tcb.sensenet.internal.analysis.diffusion.TargetedWalkStrategy;
import com.tcb.sensenet.internal.analysis.diffusion.WalkStrategy;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.util.CancellableRunner;
import com.tcb.sensenet.internal.util.Nullable;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;

public class RandomWalkTask extends AbstractTask {

	private AppGlobals appGlobals;
	private Config config;
	
	@AutoValue
	public static abstract class Config implements ParameterReporter {
		
		public static Config create(RandomWalkMode randomWalkMode,
				Long sourceSUID, Long targetSUID, Integer maxSteps,
				String weightColumn, Double restartProb, Integer numRuns,
				RowWriter rowWriter){
			return new AutoValue_RandomWalkTask_Config(
					randomWalkMode,sourceSUID,targetSUID,
					maxSteps,weightColumn,restartProb,numRuns,
					rowWriter);
		}
		
		public abstract RandomWalkMode getRandomWalkMode();
		public abstract Long getSourceSUID();
		@Nullable
		public abstract Long getTargetSUID();
		public abstract Integer getMaxSteps();
		public abstract String getWeightColumn();
		public abstract Double getRestartProbability();
		public abstract Integer getNumRuns();
		public abstract RowWriter getRowWriter();
		
		public TaskLogType getTaskLogType(){
			return TaskLogType.DIFFUSION;
		}
	}
	
	
	public RandomWalkTask(
			Config config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
		this.cancelled = false;
	}


	@Override
	public void run(TaskMonitor arg0) throws Exception {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		
		LogBuilder log = TaskLogUtil.createTaskLog(metaNetwork, config.getTaskLogType(),
				appGlobals.state.logManager);
		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config);
				
		CyNode source = network.getNode(config.getSourceSUID());
		WalkStrategy walkStrategy = getWalkStrategy(network,source);
		RandomWalk walkFac = new RandomWalk();
		
		ObjMap walk = CancellableRunner.run(() ->
			walkFac.perform(network, source, walkStrategy, config.getNumRuns()),
			() -> cancelled == true,
			() -> walkFac.cancelled = true);
		@SuppressWarnings("unchecked")
		Map<CyNode,Long> visited = walk.get("visited",Map.class);
		
		RowWriter rowWriter = config.getRowWriter();
		for(CyNode n:visited.keySet()){
			CyRowAdapter row = network.getRow(n);
			Long count = visited.get(n);
			ObjMap m = new ObjMap();
			m.put("visited", (double)count);
			rowWriter.write(row, m);
		}
		
		TaskLogUtil.finishTaskLog(log);
	}
	
	private WalkStrategy getWalkStrategy(CyNetworkAdapter network, CyNode source){
		CyNode target = null;
		switch(config.getRandomWalkMode()){
		case DEFAULT: return new DefaultWalkStrategy(source,config.getMaxSteps(),
				config.getWeightColumn(),config.getRestartProbability());
		case TARGETED: 
			target = network.getNode(config.getTargetSUID());
			return new TargetedWalkStrategy(source,target,
					config.getMaxSteps(),config.getWeightColumn(),config.getRestartProbability());
		case TARGETED_SYMMETRIC:
			target = network.getNode(config.getTargetSUID());
			return new SymmetricTargetedWalkStrategy(source,target,
					config.getMaxSteps(),config.getWeightColumn(),config.getRestartProbability());
		default: throw new IllegalArgumentException();
		}
	}

}

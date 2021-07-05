package com.tcb.sensenet.internal.task.path.search;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.analysis.path.PathWeightAnalysis;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.path.Path;
import com.tcb.sensenet.internal.path.PathSearcher;
import com.tcb.sensenet.internal.task.AbstractResultTask;
import com.tcb.sensenet.internal.util.CancellableRunner;
import com.tcb.sensenet.internal.util.ObjMap;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class SearchPathsTask extends AbstractResultTask {

	private AppGlobals appGlobals;
	private SearchPathsTaskConfig config;
	
	public SearchPathsTask(
			ObjMap results,
			SearchPathsTaskConfig config,
			AppGlobals appGlobals){
		super(results);
		this.config = config;
		this.appGlobals = appGlobals;
		this.cancelled = false;
	}
	
	@Override
	public ObjMap start(TaskMonitor taskMon) throws Exception {
		CyNetworkAdapter network = config.getNetwork();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		verifyState(metaNetwork);
		
		PathSearcher searcher = config.getPathSearcher();
		List<Path> paths = CancellableRunner.run(
				() -> searcher.getPaths(config.getSource(), config.getTarget()),
				() -> isCancelled(),
				() -> searcher.cancel());
		
		List<ObjMap> analyses = paths.stream()
				.map(p -> PathWeightAnalysis.calculate(p, metaNetwork, network,
						appGlobals.state.timelineManager))
				.collect(Collectors.toList());
		results.put("paths",paths);
		results.put("pathWeights",analyses);
		return results;
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

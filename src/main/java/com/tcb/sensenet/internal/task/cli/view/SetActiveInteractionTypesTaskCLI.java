package com.tcb.sensenet.internal.task.cli.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.cluster.Cluster;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.view.factories.SetActiveInteractionTypesTaskFactory;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateClusterWeightingTaskFactory;
import com.tcb.cytoscape.cyLib.util.NullUtil;

public class SetActiveInteractionTypesTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="Selected interaction types (',' delimited). Special keywords: none, all")
	public String types;

	public SetActiveInteractionTypesTaskCLI(AppGlobals appGlobals){
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(types, "types");
				
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		List<String> typesInput = getSelectedInteractionTypes(metaNetwork);
		
		return new SetActiveInteractionTypesTaskFactory(typesInput, appGlobals)
				.createTaskIterator();
	}
	
	private List<String> getSelectedInteractionTypes(MetaNetwork metaNetwork){
		String lowerCaseInput = types.toLowerCase();
		if(lowerCaseInput.equals("none")) return new ArrayList<>();
		else if(lowerCaseInput.equals("all")) return metaNetwork.getHiddenDataRow().getList(
				AppColumns.AVAILABLE_INTERACTION_TYPES, String.class);
		else return Stream.of(types.split(",")).collect(Collectors.toList());
	}
		

}

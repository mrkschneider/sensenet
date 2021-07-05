package com.tcb.sensenet.internal.init;

import java.util.List;

import com.google.auto.value.AutoValue;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.log.ParameterReporter;
import com.tcb.aifgen.importer.InteractionImporter;

@AutoValue
public abstract class ImportConfig implements ParameterReporter {
		
	public abstract InteractionImporter getInteractionImporter();
	public abstract String	getNetworkName();
	public abstract Double	getCutoffValue();
	public abstract Columns  getCutoffColumn();
	public abstract NodeGroupDefinition getNodeGroupDefinition();
	public abstract Boolean shouldCreateVisualStyle();
	
		
	public static ImportConfig create(
			InteractionImporter interactionImporter,
			String networkName,
			Double cutoffValue,
			Columns cutoffColumn,
			NodeGroupDefinition nodeGroupDefinition,
			Boolean shouldCreateVisualStyle) {
		return new AutoValue_ImportConfig(
				interactionImporter,
				networkName,
				cutoffValue,
				cutoffColumn,
				nodeGroupDefinition,
				shouldCreateVisualStyle);
	}
	
	public TaskLogType getTaskLogType(){
		return TaskLogType.IMPORT;
	}
	
}

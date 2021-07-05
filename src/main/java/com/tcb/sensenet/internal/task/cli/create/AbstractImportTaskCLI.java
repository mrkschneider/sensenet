package com.tcb.sensenet.internal.task.cli.create;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel.GroupInputMode;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.groups.nodes.AminoAcidGroupDefinition;
import com.tcb.sensenet.internal.init.groups.nodes.BackboneGroupDefinition;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.create.factories.ActionCreateMetaNetworkTaskFactory;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;


public abstract class AbstractImportTaskCLI extends AbstractWrappedTask {
	
	private static final String delimiter = ",";

	protected abstract InteractionImporter getInteractionImporter();
		
	public AbstractImportTaskCLI(AppGlobals appGlobals){
		super(appGlobals);
	}
	
	@Tunable(description="Network name")
	public String networkName;
	
	@Tunable(description="Cutoff column")
	public String cutoffColumn;
	
	@Tunable(description="Cutoff value")
	public Double cutoffValue;
	
	@Tunable(description="Metanode definition")
	public String metanodeDefinition;
	
	@Tunable(description="Backbone atom names (for backbone/sidechain metanode definition)")
	public String backboneAtomNames;
	
	@Tunable(description="Create visual style")
	public Boolean shouldCreateVisualStyle = true;
	
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(networkName, "Network name");
		NullUtil.requireNonNull(cutoffColumn, "Cutoff column");
		NullUtil.requireNonNull(cutoffValue, "Cutoff value");
		NullUtil.requireNonNull(metanodeDefinition, "Metanode definition");
		NullUtil.requireNonNull(shouldCreateVisualStyle, "Create visual style");
				
		InteractionImporter importer = getInteractionImporter();
		NodeGroupDefinition metanodeDefinition = getNodeGroupDefinition();
		Columns cutoffColumnV = getCutoffColumn(cutoffColumn);
		ImportConfig config = ImportConfig.create(
				importer,
				networkName,
				cutoffValue,
				cutoffColumnV,
				metanodeDefinition,
				shouldCreateVisualStyle
				);
		
		TaskIterator it = new TaskIterator();
		it.append(
				new ActionCreateMetaNetworkTaskFactory(appGlobals)
				.createTaskIterator(config));
		
		return it;
	}
		
	private NodeGroupDefinition getNodeGroupDefinition(){
		GroupInputMode inputMode = null;
		try{
			inputMode = GroupInputMode.valueOf(metanodeDefinition);
		} catch(IllegalArgumentException e){
			throw EnumUtil.usage(metanodeDefinition,GroupInputMode.class);
		}
		
		switch(inputMode){
		case AMINO_ACID: 
			return new AminoAcidGroupDefinition();
		case BACKBONE_SIDECHAIN:
			NullUtil.requireNonNull(backboneAtomNames, "Backbone atom names");
			Set<String> backboneAtomNamesSet = new HashSet<String>(
					splitInput(backboneAtomNames));
			return new BackboneGroupDefinition(backboneAtomNamesSet);
		default: throw new IllegalArgumentException();
		}
	}
	
	private Columns getCutoffColumn(String input){
		try{
			return AppColumns.valueOf(input);
		} catch (IllegalArgumentException e){
			throw EnumUtil.usage(input, AppColumns.class);
		}
	}
		
	private List<String> splitInput(String input){
		return Stream.of(input.split(delimiter))
				.collect(Collectors.toList());
	}

	
	
}

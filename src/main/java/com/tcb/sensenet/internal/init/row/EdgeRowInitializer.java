package com.tcb.sensenet.internal.init.row;

import java.util.List;
import java.util.stream.Collectors;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.interactions.Interaction;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.edge.EdgeType;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;



public class EdgeRowInitializer {
	private Interaction interaction;

	public EdgeRowInitializer(Interaction interaction){
		this.interaction = interaction;
	}
	
	public void init(CyRowAdapter edgeRow, CyRowAdapter sourceRow, CyRowAdapter targetRow){
		StandardizedEdgeNameGenerator nameGenerator = new StandardizedEdgeNameGenerator();
		String bridgeName = getAtomNames(interaction.getBridgingAtoms()).stream()
				.reduce((a,b) -> a + "-" + b)
				.orElse("");
		String interactionType = interaction.getType();
		String sourceName = sourceRow.get(DefaultColumns.SHARED_NAME, String.class);
		String targetName = targetRow.get(DefaultColumns.SHARED_NAME, String.class);
		String edgeName = nameGenerator.getName(sourceName,targetName,interactionType,bridgeName);
		edgeRow.set(DefaultColumns.SHARED_NAME, edgeName);
		edgeRow.set(DefaultColumns.NAME, edgeName);
		edgeRow.set(AppColumns.BRIDGE_NAME, getAtomNames(interaction.getBridgingAtoms()));
		edgeRow.set(DefaultColumns.TYPE, EdgeType.AtomToAtom.toString());
		edgeRow.set(AppColumns.SOURCE_NODE_NAME, sourceName);
		edgeRow.set(AppColumns.TARGET_NODE_NAME, targetName);
		edgeRow.set(AppColumns.SOURCE_CHAIN, sourceRow.get(AppColumns.CHAIN, String.class));
		edgeRow.set(AppColumns.TARGET_CHAIN, targetRow.get(AppColumns.CHAIN, String.class));
		edgeRow.set(DefaultColumns.SHARED_INTERACTION, interactionType);
		edgeRow.set(AppColumns.GROUP_TAG, 
				new EdgeGroupTagFactory().create(sourceRow, targetRow));
	}
	
	private List<String> getAtomNames(List<Atom> atoms){
		return atoms.stream().map(a -> a.getName()).collect(Collectors.toList());
	}
}

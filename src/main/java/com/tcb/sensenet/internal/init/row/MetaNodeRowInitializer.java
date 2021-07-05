package com.tcb.sensenet.internal.init.row;

import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.meta.node.NodeType;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.InteractionImportData;


public class MetaNodeRowInitializer {

	private InteractionImportData importer;
	private String groupTag;

	public MetaNodeRowInitializer(String groupTag, InteractionImportData importer){
		this.groupTag = groupTag;
		this.importer = importer;
	}
	
	public void init(CyRowAdapter row, Residue residue){
			MetanodeNameGenerator nameGenerator = new MetanodeNameGenerator(groupTag);
		
			Integer resId = residue.getIndex();
			String resName = residue.getName();
			String chain = residue.getChain();
			String mutatedName = nameGenerator.getMutationName(residue, importer);
			String groupName = nameGenerator.getName(residue, importer);
			String altLoc = residue.getAltLoc();
			String resInsert = residue.getResidueInsert();
			String secondaryStructure = importer.getSecondaryStructureMap().getOrDefault(residue,"");
			
			row.set(AppColumns.ATOM_NAME, "");
			row.set(DefaultColumns.SHARED_NAME, groupName);
			row.set(AppColumns.RESINDEX, resId);
			row.set(AppColumns.RESNAME, resName);
			row.set(AppColumns.MUTATED_RESNAME, mutatedName);
			row.set(AppColumns.CHAIN, chain);
			row.set(DefaultColumns.TYPE, NodeType.Metanode.toString());
			row.set(AppColumns.GROUP_TAG, groupTag);
			row.set(AppColumns.ALTLOC, altLoc);
			row.set(AppColumns.RESINSERT, resInsert);
			row.set(AppColumns.SECONDARY_STRUCTURE, secondaryStructure);
			
	}
	
}

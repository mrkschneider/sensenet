package com.tcb.sensenet.internal.init.row;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;
import com.tcb.sensenet.internal.meta.node.NodeType;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.InteractionImportData;


public class NodeRowInitializer {

	private InteractionImportData importer;
	private NodeGroupDefinition groupDefinition;

	public NodeRowInitializer(InteractionImportData importer, NodeGroupDefinition nodeGroupDefinition){
		this.importer = importer;
		this.groupDefinition = nodeGroupDefinition;
	}
	
	public void init(CyRowAdapter row, Atom atom){
		NodeNameGenerator nameGenerator = new NodeNameGenerator();
		
		Residue residue = atom.getResidue();
		String chain = residue.getChain();
		//String resName = NodeNameGenerator.getFullResName(residue, importer);
		String resName = residue.getName();
		Integer resId = residue.getIndex();
		String atomName = atom.getName();
		String altLoc = residue.getAltLoc();
		String resInsert = residue.getResidueInsert();
		String nodeName = nameGenerator.getName(atom, importer);
		String mutatedResName = nameGenerator.getMutationName(residue, importer);
		String secondaryStructure = 
				importer.getSecondaryStructureMap().getOrDefault(residue,"");
		
		
		row.set(DefaultColumns.SHARED_NAME, nodeName);
		row.set(DefaultColumns.NAME, nodeName);
		row.set(AppColumns.ATOM_NAME, atomName);
		row.set(AppColumns.RESNAME, resName);
		row.set(AppColumns.RESINDEX, resId);
		row.set(AppColumns.CHAIN, chain);
		row.set(DefaultColumns.TYPE, NodeType.Node.toString());
		row.set(AppColumns.MUTATED_RESNAME, mutatedResName);
		row.set(AppColumns.GROUP_TAG, groupDefinition.getGroupTag(atom));
		row.set(AppColumns.ALTLOC, altLoc);
		row.set(AppColumns.RESINSERT, resInsert);
		row.set(AppColumns.SECONDARY_STRUCTURE, secondaryStructure);
	}
	
	
		
	
}

package com.tcb.sensenet.internal.init.groups.nodes;

import java.util.List;
import java.util.Set;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.util.predicates.DiPredicate;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.aifgen.importer.InteractionImportData;


public interface NodeGroupDefinition {
	public String getGroupTag(Atom atom);
	public Set<String> getGroupTags();
}

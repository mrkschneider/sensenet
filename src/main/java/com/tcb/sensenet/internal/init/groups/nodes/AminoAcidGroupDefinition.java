package com.tcb.sensenet.internal.init.groups.nodes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.data.rows.NodeResidueFactory;
import com.tcb.sensenet.internal.init.row.NodeNameGenerator;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.aifgen.importer.InteractionImportData;



public class AminoAcidGroupDefinition implements NodeGroupDefinition {

	@Override
	public String getGroupTag(Atom atom) {
		return "";
	}

	@Override
	public Set<String> getGroupTags() {
		return new HashSet<>(Arrays.asList(""));
	}

}

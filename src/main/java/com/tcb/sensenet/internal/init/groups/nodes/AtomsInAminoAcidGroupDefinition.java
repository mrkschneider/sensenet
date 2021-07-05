package com.tcb.sensenet.internal.init.groups.nodes;

import java.util.Optional;
import java.util.Set;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.residues.Residue;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.aifgen.importer.InteractionImportData;


public class AtomsInAminoAcidGroupDefinition extends AminoAcidGroupDefinition {
		
	private Set<String> selectedAtomNames;
	private String selectedGroupTag;
	private String unselectedGroupTag;

	public AtomsInAminoAcidGroupDefinition(Set<String> selectedAtomNames,
			String selectedGroupTag,
			String unselectedGroupTag) {
		this.selectedAtomNames = selectedAtomNames;
		this.selectedGroupTag = selectedGroupTag;
		this.unselectedGroupTag = unselectedGroupTag;
	}
	
	protected boolean hasSelectedAtomName(Atom atom){
		return selectedAtomNames.contains(atom.getName());
	}
		
	@Override
	public String getGroupTag(Atom atom) {
		if(hasSelectedAtomName(atom)) return selectedGroupTag;
		else return unselectedGroupTag;
	}

}

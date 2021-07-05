package com.tcb.sensenet.internal.init.groups.nodes;

import java.util.Set;

public class BackboneGroupDefinition extends AtomsInAminoAcidGroupDefinition {

	private static final String selectionName = "bb";
	private static final String unselectedName = "sc";
	
	public BackboneGroupDefinition(Set<String> selectedAtomNames) {
		super(selectedAtomNames, selectionName, unselectedName);
	}

}

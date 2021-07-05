package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel;

import java.util.Arrays;
import java.util.List;

import com.tcb.sensenet.internal.init.groups.nodes.AminoAcidGroupDefinition;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;

public class AminoAcidGroupDefinitionInputPanel extends AbstractGroupDefinitionInputPanel {
	
	@Override
	public NodeGroupDefinition getGroupDefinition() {
		return new AminoAcidGroupDefinition();
	}

}

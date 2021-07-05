package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.groupDefinitionPanel;

import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.init.groups.nodes.NodeGroupDefinition;

public abstract class AbstractGroupDefinitionInputPanel extends JPanel {
	public abstract NodeGroupDefinition getGroupDefinition();
	
}

package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.tree;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.TreeClustererConfig;

public abstract class AbstractTreeClustererSettingsPanel extends JPanel {
	public abstract TreeClustererConfig getClustererConfig();
}

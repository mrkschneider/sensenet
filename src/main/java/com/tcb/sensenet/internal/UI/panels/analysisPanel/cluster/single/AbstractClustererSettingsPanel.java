package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.single;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;

public abstract class AbstractClustererSettingsPanel extends JPanel {
	public abstract ClustererConfig getClustererConfig();
}

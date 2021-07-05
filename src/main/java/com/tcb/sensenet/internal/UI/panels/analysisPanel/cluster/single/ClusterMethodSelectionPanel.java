package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.single;

import java.awt.Container;
import java.awt.event.ItemEvent;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.util.ComboBoxCardPanel;
import com.tcb.sensenet.internal.UI.util.ComboBoxPanel;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.analysis.cluster.ClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusterMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;

public class ClusterMethodSelectionPanel extends JPanel {

	private AppGlobals appGlobals;
	
	private JComboBox<ClusterMethod> clusterMethodBox;
	private ComboBoxCardPanel<ClusterMethod,AbstractClustererSettingsPanel> clusterMethodCards;
	
	private static final AppProperty defaultClusterMethodProperty = AppProperty.CLUSTER_DEFAULT_METHOD;

	public ClusterMethodSelectionPanel(AppGlobals appGlobals) {
		this.appGlobals = appGlobals;
				
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		addClusterSettingsPanel(this);
	}
	
	private void addClusterSettingsPanel(Container target){
		ClusterMethod defaultClusterMethod = appGlobals.appProperties.getEnumOrDefault(
				ClusterMethod.class, defaultClusterMethodProperty);
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		clusterMethodBox = p.addChoosableParameter("Cluster method", ClusterMethod.values(), defaultClusterMethod);
		clusterMethodCards = new ComboBoxCardPanel<>(clusterMethodBox);
		clusterMethodCards.addCard(ClusterMethod.AGGLOMERATIVE,
				new AgglomerativeClustererPanel(appGlobals.appProperties));
		p.add(clusterMethodCards);
		
		target.add(p);
	}
	
	public ClustererConfig getClustererConfig(){
		ClustererConfig config = clusterMethodCards.getActiveCard().getClustererConfig();
		appGlobals.appProperties.set(defaultClusterMethodProperty, config.getClusterMethod().name());
		return config;
	}
		
}

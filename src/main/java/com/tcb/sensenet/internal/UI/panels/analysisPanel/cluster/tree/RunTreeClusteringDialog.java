package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.tree;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.util.CardPanel;
import com.tcb.sensenet.internal.UI.util.ComboBoxCardPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusterMethod;
import com.tcb.sensenet.internal.analysis.cluster.TreeClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusteringSelectionMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.cluster.ClusteringConfigImpl;
import com.tcb.sensenet.internal.task.cluster.TreeClusteringConfig;
import com.tcb.sensenet.internal.task.cluster.factories.ClusterContactTimelinesTaskFactory;
import com.tcb.sensenet.internal.task.cluster.factories.ClusterTreeContactTimelinesTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;


public class RunTreeClusteringDialog extends DefaultDialog {

	private AppGlobals appGlobals;
	
	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;
	private JComboBox<TreeClusterMethod> clusterMethodBox;
	private ComboBoxCardPanel<TreeClusterMethod,AbstractTreeClustererSettingsPanel> clusterMethodSettingsPanel;
	private JTextField sieveSelection;
	private JComboBox<TreeClusteringSelectionMethod> clusterCountSelectionMethodBox;
	
	private AppProperties appProperties;
	
	private static final AppProperty defaultClusterMethodProperty = AppProperty.CLUSTER_TREE_DEFAULT_METHOD;
	private static final AppProperty defaultSieveProperty = AppProperty.CLUSTER_DEFAULT_SIEVE;
	private static final AppProperty defaultClusterWeightMethodProperty = 
			AppProperty.CLUSTER_DEFAULT_WEIGHT_METHOD;
	private static final AppProperty defaultClusterCountSelectionMethodProperty =
			AppProperty.CLUSTER_TREE_DEFAULT_CLUSTER_COUNT_SELECTION_METHOD;
	
	public RunTreeClusteringDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.appProperties = appGlobals.appProperties;
		
		this.setLayout(new GridBagLayout());
		
		addConfigPanel();
		
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Tree clustering settings");
		this.pack();
		this.setMinimumSize(this.getPreferredSize());
	}
		
	private void addConfigPanel(){
		
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		clusterMethodBox = p.addChoosableParameter("Cluster method", TreeClusterMethod.values(),
				TreeClusterMethod.AGGLOMERATIVE);
				
		clusterMethodSettingsPanel = new ComboBoxCardPanel<>(clusterMethodBox);
		clusterMethodSettingsPanel.addCard(TreeClusterMethod.AGGLOMERATIVE,
				new TreeAgglomerativeClustererPanel(appProperties));
		p.add(clusterMethodSettingsPanel);
		
		JPanelUtil.setBorders(p, "Clustering settings");
				
		LabeledParametersPanel p2 = new LabeledParametersPanel();
		JPanelUtil.setBorders(p2, "General settings");
		
		sieveSelection = p2.addTextParameter("Sieve", appProperties.getOrDefault(defaultSieveProperty));
		weightMethodSelectionBox = p2.addChoosableParameter("Frame weight", FrameWeightMethod.values(),
				appProperties.getEnumOrDefault(FrameWeightMethod.class, defaultClusterWeightMethodProperty));
		clusterCountSelectionMethodBox = p2.addChoosableParameter(
				"Show clustering", 
				TreeClusteringSelectionMethod.values(),
				appProperties.getEnumOrDefault(TreeClusteringSelectionMethod.class, 
						defaultClusterCountSelectionMethodProperty));
		
		this.add(p);
		this.add(p2);
	}
			
	private TreeClusteringConfig getConfig(){
		Integer sieve = Integer.valueOf(sieveSelection.getText());
		FrameWeightMethod weightMethod = (FrameWeightMethod) 
				weightMethodSelectionBox.getSelectedItem();
		TreeClusterMethod treeClusterMethod = (TreeClusterMethod) 
				clusterMethodBox.getSelectedItem();
		TreeClustererConfig clustererConfig = clusterMethodSettingsPanel.getActiveCard()
				.getClustererConfig();
		TreeClusteringSelectionMethod clusteringSelectionMethod = 
				(TreeClusteringSelectionMethod) clusterCountSelectionMethodBox
				.getSelectedItem();
		
		appProperties.set(defaultClusterMethodProperty, treeClusterMethod.name());
		appProperties.set(defaultSieveProperty, sieve.toString());
		appProperties.set(defaultClusterWeightMethodProperty, weightMethod.name());
		appProperties.set(defaultClusterCountSelectionMethodProperty, clusteringSelectionMethod.name());
						
		return new TreeClusteringConfig(
				clustererConfig, 
				sieve, weightMethod, clusteringSelectionMethod);
	}
			
	protected void confirm() {
		TreeClusteringConfig config = getConfig();
		appGlobals.taskManager.execute(
				new ClusterTreeContactTimelinesTaskFactory(config, appGlobals)
				.createTaskIterator());
		this.dispose();
	}


}

package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.single;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.ClusterLimitSelectionPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.cluster.ClusteringConfigImpl;
import com.tcb.sensenet.internal.task.cluster.factories.ClusterContactTimelinesTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;


public class RunClusteringDialog extends DefaultDialog {

	private AppGlobals appGlobals;
	
	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;
	private ClusterMethodSelectionPanel clusterMethodSelectionPanel;
	private ClusterLimitSelectionPanel clusterLimitSelectionPanel;
	
	private JTextField sieveSelection;
	private AppProperties appProperties;
	
	private static final AppProperty defaultSieveProperty = AppProperty.CLUSTER_DEFAULT_SIEVE;
	private static final AppProperty defaultClusterWeightMethodProperty = 
			AppProperty.CLUSTER_DEFAULT_WEIGHT_METHOD;
	
	public RunClusteringDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.appProperties = appGlobals.appProperties;
		
		this.setLayout(new GridBagLayout());
		
		addConfigPanel();
		
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Cluster settings");
		this.pack();
		this.setMinimumSize(this.getPreferredSize());
	}
	
	private GridBagConstraints getConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.weighty = 1.0;
		c.weightx = 1.0;
		return c;
	}
	
	private void addConfigPanel(){
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		
		addClusterMethodSelectionBox(p);
		addLimitSelectionPanel(p);
		addSieveSelectionField(p);
		addWeightMethodSelectionBox(p);
		
		this.add(p,getConstraints());
	}
	
	private ClusteringConfigImpl getConfig(){
		ClusterLimitConfig limitConfig = clusterLimitSelectionPanel.getClusterLimitConfig();
		Integer sieve = Integer.valueOf(sieveSelection.getText());
		FrameWeightMethod weightMethod = (FrameWeightMethod) 
				weightMethodSelectionBox.getSelectedItem();
		ClustererConfig clustererConfig = clusterMethodSelectionPanel.getClustererConfig();
		
		appProperties.set(defaultSieveProperty, sieve.toString());
		appProperties.set(defaultClusterWeightMethodProperty, weightMethod.name());
						
		return new ClusteringConfigImpl(
				clustererConfig, limitConfig,
				sieve, weightMethod);
	}
		
	private void addClusterMethodSelectionBox(JPanel p){
		clusterMethodSelectionPanel = new ClusterMethodSelectionPanel(appGlobals);
		JPanelUtil.setBorders(clusterMethodSelectionPanel, "Clustering method");
		p.add(clusterMethodSelectionPanel,getConstraints());
	}
		
	private void addSieveSelectionField(JPanel p){
		JPanel s = new JPanel();
		s.setLayout(new GridLayout(0,2));
		
		JLabel label = new JLabel("Sieve");

		sieveSelection = new JTextField(
				appProperties.getOrDefault(defaultSieveProperty));
		label.setHorizontalAlignment(JLabel.CENTER);
		s.add(label);
		s.add(sieveSelection);
		
		p.add(s);
	}
	
	private void addLimitSelectionPanel(JPanel p){
		clusterLimitSelectionPanel = new ClusterLimitSelectionPanel(appGlobals.appProperties);
		p.add(clusterLimitSelectionPanel);
	}
		
	private void addWeightMethodSelectionBox(JPanel p){
		JPanel s = new JPanel();
		s.setLayout(new GridLayout(0,2));
		
		JLabel label = new JLabel("Frame weight");
		weightMethodSelectionBox = new JComboBox<>(FrameWeightMethod.values());
		FrameWeightMethod defaultSelectedMethod = appProperties.getEnumOrDefault(
				FrameWeightMethod.class, defaultClusterWeightMethodProperty);
		weightMethodSelectionBox.setSelectedItem(defaultSelectedMethod);
		
		label.setHorizontalAlignment(JLabel.CENTER);
				
		s.add(label);
		s.add(weightMethodSelectionBox);
				
		p.add(s);
	}
		
	protected void confirm() {
		ClusteringConfigImpl config = getConfig();
		appGlobals.taskManager.execute(
				new ClusterContactTimelinesTaskFactory(config, appGlobals)
				.createTaskIterator());
		this.dispose();
	}


}

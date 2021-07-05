package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClusterLimitMethod;
import com.tcb.sensenet.internal.analysis.cluster.EpsilonClusterLimitConfig;
import com.tcb.sensenet.internal.analysis.cluster.TargetClusterLimitConfig;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;

public class ClusterLimitSelectionPanel extends JPanel {
	private JComboBox<ClusterLimitMethod> clusterLimitMethodSelectionBox;
	private JTextField clusterLimitTextField;
	private AppProperties appProperties;
	
	private static final AppProperty defaultClusterLimitProperty = 
			AppProperty.CLUSTER_DEFAULT_CLUSTER_LIMIT;
	private static final AppProperty defaultTargetClustersProperty = 
			AppProperty.CLUSTER_DEFAULT_TARGET_CLUSTERS;
	private static final AppProperty defaultEpsilonProperty = 
			AppProperty.CLUSTER_DEFAULT_EPSILON;
	
	public ClusterLimitSelectionPanel(AppProperties appProperties){
		this.appProperties = appProperties;
		this.setLayout(new GridLayout(0,2));
		addClusterLimitMethodSelectionBox();
		addClusterLimitTextField();
		updateDefaultValues();
	};
	
	private void addClusterLimitMethodSelectionBox(){
		JComboBox<ClusterLimitMethod> box = new JComboBox<>(ClusterLimitMethod.values());
		ClusterLimitMethod defaultMethod = appProperties.getEnumOrDefault(
				ClusterLimitMethod.class,defaultClusterLimitProperty);
		box.setSelectedIndex(defaultMethod.ordinal());
		ItemListener listener = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateDefaultValues();			
			}
		};
		box.addItemListener(listener);
		this.clusterLimitMethodSelectionBox = box;
		this.add(box);
	}
	
	private void addClusterLimitTextField(){
		this.clusterLimitTextField = new JTextField("");
		this.add(clusterLimitTextField);
	}
	
	public ClusterLimitConfig getClusterLimitConfig(){
		ClusterLimitMethod method = getClusterLimitMethod();
		switch(method){
		case TARGET_NUMBER:	return getTargetNumberClusterLimit();
		case EPSILON: return getEpsilonClusterLimit();
		default: throw new UnsupportedOperationException();
		}
	}
	
	private ClusterLimitMethod getClusterLimitMethod(){
		return (ClusterLimitMethod) clusterLimitMethodSelectionBox.getSelectedItem();
	}
	
	private void updateDefaultValues(){
		ClusterLimitMethod method = getClusterLimitMethod();
		appProperties.set(defaultClusterLimitProperty, method.name());
		switch(method){
		case TARGET_NUMBER:	clusterLimitTextField.setText(
				appProperties.getOrDefault(defaultTargetClustersProperty)); break;
		case EPSILON: clusterLimitTextField.setText(
				appProperties.getOrDefault(defaultEpsilonProperty)); break;
		default: throw new UnsupportedOperationException();
		}
	}
	
	private ClusterLimitConfig getTargetNumberClusterLimit(){
		Integer targetNumber = Integer.valueOf(
				clusterLimitTextField.getText());
		appProperties.set(defaultTargetClustersProperty, targetNumber.toString());
		return new TargetClusterLimitConfig(targetNumber);
	}
	
	private ClusterLimitConfig getEpsilonClusterLimit(){
		Double epsilon = Double.valueOf(
				clusterLimitTextField.getText());
		appProperties.set(defaultEpsilonProperty, epsilon.toString());

		return new EpsilonClusterLimitConfig(epsilon);
	}
	
	
}

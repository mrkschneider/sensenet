package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.tree;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tcb.cluster.linkage.LinkageStrategy;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.analysis.cluster.AgglomerativeClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.TreeAgglomerativeClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.TreeClustererConfig;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;


public class TreeAgglomerativeClustererPanel extends AbstractTreeClustererSettingsPanel {
	
	private JComboBox<LinkageStrategy> linkageSelection;

	private AppProperties appProperties;
	
	private static final AppProperty defaultLinkageProperty = 
			AppProperty.CLUSTER_AGGLOMERATIVE_DEFAULT_LINKAGE;
	
	public TreeAgglomerativeClustererPanel(AppProperties appProperties){
		this.appProperties = appProperties;
	
		this.setLayout(new GridLayout(0,1));
		
		addLinkageSelectionBox();
	}
	
	private void addLinkageSelectionBox(){
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		linkageSelection = p.addChoosableParameter("Linkage", LinkageStrategy.values(), appProperties.getEnumOrDefault(
				LinkageStrategy.class,
				defaultLinkageProperty));
		
		this.add(p);
	}
	
	private LinkageStrategy getLinkage(){
		LinkageStrategy strat = (LinkageStrategy) linkageSelection.getSelectedItem();
		appProperties.set(defaultLinkageProperty, strat.name());
		return strat;
	}

	@Override
	public TreeClustererConfig getClustererConfig() {
		return new TreeAgglomerativeClustererConfig(getLinkage());
	}
}

package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.single;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tcb.cluster.linkage.LinkageStrategy;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.analysis.cluster.AgglomerativeClustererConfig;
import com.tcb.sensenet.internal.analysis.cluster.ClustererConfig;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;


public class AgglomerativeClustererPanel extends AbstractClustererSettingsPanel {
	
	private JComboBox<LinkageStrategy> linkageSelection;

	private AppProperties appProperties;
	
	private static final AppProperty defaultLinkageProperty = 
			AppProperty.CLUSTER_AGGLOMERATIVE_DEFAULT_LINKAGE;
	
	public AgglomerativeClustererPanel(AppProperties appProperties){
		this.appProperties = appProperties;
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		addLinkageSelectionBox();
	}
	
	private void addLinkageSelectionBox(){
	
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		LinkageStrategy selectLinkage = appProperties.getEnumOrDefault(
				LinkageStrategy.class,defaultLinkageProperty);
		linkageSelection = p.addChoosableParameter("Linkage", LinkageStrategy.values(), selectLinkage);
				
		this.add(p);
	}
	
	private LinkageStrategy getLinkage(){
		LinkageStrategy strat = (LinkageStrategy) linkageSelection.getSelectedItem();
		appProperties.set(defaultLinkageProperty, strat.name());
		return strat;
	}

	@Override
	public ClustererConfig getClustererConfig() {
		return new AgglomerativeClustererConfig(getLinkage());
	}
}

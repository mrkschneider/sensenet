package com.tcb.sensenet.internal.UI.panels.importPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.Optional;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.SerializationUtils;
import org.cytoscape.util.swing.ColorButton;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettings;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class MetaNetworkSettingsDialog extends DefaultDialog {
	private AppGlobals appGlobals;
		
	public MetaNetworkSettingsDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		this.setLayout(new GridBagLayout());
		
		this.setTitle("Metanetwork settings");
		this.setMinimumSize(new Dimension(300,100));
		
		addGeneralPanel(this);
				
		this.add(
				DialogUtil.createActionPanel(this::confirm,this::dispose),
				getDefaultDialogConstraints());

		this.pack();
	}
	
	@Override
	protected GridBagConstraints getDefaultDialogConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	private void addGeneralPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		MetaNetworkSettings settings = getMetaNetworkSettings();
						
		JPanelUtil.setBorders(p, "General");
		target.add(p);
	}
	
	private MetaNetworkSettings getMetaNetworkSettings() {
		MetaNetworkSettings settings = appGlobals.state.networkSettingsManager
				.get(getMetaNetwork());
		return settings;
	}
	
	private MetaNetwork getMetaNetwork() {
		return appGlobals.state.metaNetworkManager
				.getCurrentMetaNetwork();
	}
	
	private void confirm() {
		MetaNetworkSettings settings = SerializationUtils.roundtrip(getMetaNetworkSettings());
		
		MetaNetwork metaNetwork = getMetaNetwork();
		appGlobals.state.networkSettingsManager.putOrReplace(metaNetwork, settings);
		
		this.dispose();
	}
	
	
	
}

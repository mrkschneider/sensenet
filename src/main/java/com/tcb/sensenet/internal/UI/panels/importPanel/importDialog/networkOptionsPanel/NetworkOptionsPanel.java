package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.networkOptionsPanel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.networkOptionsPanel.listeners.UpdateDefaultNetworkNameObserver;
import com.tcb.sensenet.internal.UI.panels.weightPanel.EdgeCutoffPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.cytoscape.cyLib.data.Columns;



public class NetworkOptionsPanel extends JPanel {

	private EdgeCutoffPanel timeFractionCutoffPanel;
	private ImportNetworkDialog dialog;
	
	private JTextField nameField;
	private JCheckBox createVisualStyleBox;
	
	private AppGlobals appGlobals;
	
	private static final String panelName = "Network options";
	
	public NetworkOptionsPanel(ImportNetworkDialog dialog,
			AppGlobals appGlobals){
		JPanelUtil.setBorders(this, panelName);
		this.dialog = dialog;
		this.appGlobals = appGlobals;
			
		this.setLayout(new GridBagLayout());

		addGeneralOptionsPanel();
	}
	
	private GridBagConstraints defaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	private void addTimeFractionCutoffPanel(Container target){
		EdgeCutoffPanel p =  new EdgeCutoffPanel(
				appGlobals);
				
		target.add(p, defaultConstraints());
		this.timeFractionCutoffPanel = p;
	}
		
	private void addGeneralOptionsPanel(){
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridBagLayout());
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		createVisualStyleBox =
				p.addBooleanParameter("Create visual style", 
						Boolean.valueOf(
								appGlobals.appProperties.getOrDefault(AppProperty.IMPORT_CREATE_VISUAL_STYLE)));
		nameField =
				p.addTextParameter("Network name", "");
							
		dialog.getState().addObserver(
				new UpdateDefaultNetworkNameObserver(dialog,nameField));
		
		optionsPanel.add(p, defaultConstraints());
		addTimeFractionCutoffPanel(optionsPanel);
				
		optionsPanel.setPreferredSize(new Dimension(350,optionsPanel.getPreferredSize().height));
		this.add(optionsPanel, defaultConstraints());
	}
	
	public Double getTimeFractionCutoff(){
		return timeFractionCutoffPanel.getTimeFractionCutoff();
	}
	
	public Columns getCutoffColumn(){
		return timeFractionCutoffPanel.getCutoffWeightColumn();
	}
	
	public String getNetworkName(){
		return nameField.getText();
	}
	
	public Boolean getShouldCreateVisualStyle(){
		Boolean result = createVisualStyleBox.isSelected();
		appGlobals.appProperties.set(AppProperty.IMPORT_CREATE_VISUAL_STYLE, result.toString());
		return result;
	}
		
		
}


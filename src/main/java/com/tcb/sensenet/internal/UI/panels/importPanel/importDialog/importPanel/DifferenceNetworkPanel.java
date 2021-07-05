package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel;

import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.swing.JCheckBox;
import javax.swing.JDialog;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.CheckBoxPanel;
import com.tcb.sensenet.internal.UI.util.ComboBoxCardPanel;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.aifgen.importer.InteractionImporter;




public class DifferenceNetworkPanel extends DefaultPanel {
			
	private ImportNetworkDialog dialog;
	private AppGlobals appGlobals;
	
	private Optional<ImportPanel> importPanelOpt;
	private JCheckBox createDifferenceNetworkBox;
	
	private final static String checkBoxName = "Create difference network";
	private final static String panelName = "Difference network";
	private final static String importSubPanelName = "Import reference network";

	public DifferenceNetworkPanel(ImportNetworkDialog dialog, 
			AppGlobals appGlobals) {
		this.appGlobals = appGlobals;
		this.dialog = dialog;
		this.importPanelOpt = Optional.empty();
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		createDifferenceNetworkBox = p.addBooleanParameter(checkBoxName, false);
		
		createDifferenceNetworkBox.addActionListener((e) -> updateImportPanel());
		
		
		this.add(p);
		JPanelUtil.setBorders(this, panelName);
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	private void updateImportPanel(){
		if(importPanelOpt.isPresent()){
			this.remove(importPanelOpt.get());
		}
		if(createDifferenceNetworkBox.isSelected()){
			ImportPanel importPanel = 
					new ImportPanel(importSubPanelName,dialog,appGlobals,appGlobals.appProperties);
			importPanelOpt = Optional.of(importPanel);
			this.add(importPanel);
		} else {
			importPanelOpt = Optional.empty();
		}
		dialog.pack();
	}
	
	public InteractionImporter getCombinedInteractionImporter() throws IOException {
		return importPanelOpt.get().getCombinedInteractionImporter();
	}
	
	public List<String> getMainFileNames() {
		return importPanelOpt.get().getMainFileNames();
	}
	
	public Boolean isChecked(){
		return createDifferenceNetworkBox.isSelected();
	}
		
}


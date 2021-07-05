package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.AbstractInteractionsImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.AifImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.CpptrajContactsImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.CpptrajHbondImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.DsspImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.PdbContactImporterPanel;
import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel.PdbHbondImporterPanel;
import com.tcb.sensenet.internal.UI.util.ComboBoxCardPanel;
import com.tcb.sensenet.internal.UI.util.ComboBoxPanel;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.aifgen.importer.InteractionImporter;




public class ImportComboBoxPanel extends JPanel {
	
	private JComboBox<ImportMode> importModeBox;
	private ComboBoxCardPanel<ImportMode,AbstractInteractionsImporterPanel> importModeCards;
	private AppGlobals appGlobals;
	private ImportNetworkDialog dialog;

	private static final AppProperty defaultImporterProperty = AppProperty.DEFAULT_INTERACTION_IMPORTER;
	
	public ImportComboBoxPanel(ImportNetworkDialog dialog, AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.dialog = dialog;
				
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		addImportModePanel(this);
			
	}
		
	
	private void addImportModePanel(Container target){
		ImportMode defaultImportMode = appGlobals.appProperties
				.getEnumOrDefault(ImportMode.class, defaultImporterProperty);
		
		FileUtil fileUtil = appGlobals.fileUtil;
		AppProperties appProperties = appGlobals.appProperties;
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		importModeBox = p.addChoosableParameter("Input type", ImportMode.values(), defaultImportMode);
		
		
		importModeCards = new ComboBoxCardPanel<>(importModeBox);
		JPanelUtil.setBorders(importModeCards, "");
		p.add(Box.createVerticalStrut(5));
		
		importModeCards.addCard(ImportMode.AIF, new AifImporterPanel(dialog, fileUtil,appProperties));
		importModeCards.addCard(ImportMode.CPPTRAJ_CONTACTS,
				new CpptrajContactsImporterPanel(dialog, fileUtil,appProperties));
		importModeCards.addCard(ImportMode.CPPTRAJ_HBOND,
				new CpptrajHbondImporterPanel(dialog, fileUtil,appProperties));
		importModeCards.addCard(ImportMode.PDB_HBOND, 
				new PdbHbondImporterPanel(dialog, fileUtil, appProperties)
				);
		importModeCards.addCard(ImportMode.PDB_CONTACTS, 
				new PdbContactImporterPanel(dialog, fileUtil, appProperties)
				);
		importModeCards.addCard(ImportMode.DSSP, 
				new DsspImporterPanel(dialog,fileUtil,appProperties)
				);
		p.add(importModeCards);
		target.add(p);
	}
	
	public InteractionImporter getInteractionImporter() throws IOException {
		updateDefaultAppProperties();
		return importModeCards.getActiveCard().getInteractionImporter();
	}
	
	public String getMainFileName(){
		return importModeCards.getActiveCard().getMainFileName();
	}
	
	public void updateDefaultAppProperties(){
		ImportMode importMode = importModeCards.getActiveSelection();
		appGlobals.appProperties.set(defaultImporterProperty, importMode.name());
	}
		
}


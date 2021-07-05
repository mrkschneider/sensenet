package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.combinedImporter.CombinedInteractionImporter;


public class ImportPanel extends JPanel implements ActionListener {
	
	private JButton addButton;
	private ImportNetworkDialog dialog;
	private ArrayList<ImportComboBoxPanel> importPanels;
	private JPanel contentPanel;
	private AppGlobals appGlobals;
	private AppProperties appProperties;
	private JButton removeButton;

	public ImportPanel(
			String panelName,
			ImportNetworkDialog dialog,
			AppGlobals appGlobals,
			AppProperties appProperties){
		this.dialog = dialog;
		this.appGlobals = appGlobals;
		this.appProperties = appProperties;
		this.importPanels = new ArrayList<ImportComboBoxPanel>();
				
		this.setLayout(new GridBagLayout());
				
		addContentPanel();
		addImportPanel();
		addButtonPanel();

		JPanelUtil.setBorders(this, panelName);
			
	}
	
	public List<String> getMainFileNames(){
		return importPanels.stream()
				.map(i -> i.getMainFileName())
				.collect(Collectors.toList());
	}
	
	private void addContentPanel(){
		this.contentPanel = new JPanel();
				
		contentPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
				
		this.add(contentPanel,c);
	}
	
	private void addButtonPanel(){
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LAST_LINE_END;
				
		addButtons(buttonPanel);
		this.add(buttonPanel, c);
		
		JPanel fillPanel = new JPanel();
		GridBagConstraints fillConstraints = new GridBagConstraints();
		fillConstraints.weightx = 1.0;
		this.add(fillPanel, fillConstraints);
		
	}
	
	private void addButtons(JPanel buttonPanel){
		this.addButton = new JButton("+");
		this.removeButton = new JButton("-");
		
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
				
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.fill = GridBagConstraints.HORIZONTAL;
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 1;
		c2.fill = GridBagConstraints.HORIZONTAL;
				
		buttonPanel.add(addButton, c1);
		buttonPanel.add(removeButton, c2);
	}
	
	public void addImportPanel(){
		ImportComboBoxPanel p = 
				new ImportComboBoxPanel(dialog,
						appGlobals);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		
		importPanels.add(p);
		contentPanel.add(p,c);
	}
	
	public void removeImportPanel(){
		int removeIndex = importPanels.size()-1;
		if(removeIndex==0){
			return;
		}
		importPanels.remove(removeIndex);
		contentPanel.remove(removeIndex);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==addButton){
			addImportPanel();
			dialog.pack();
		} 
		else if(e.getSource()==removeButton) {
			removeImportPanel();
			dialog.signalChanges();
			dialog.pack();
		}
	}

	public InteractionImporter getCombinedInteractionImporter() throws IOException {
		List<InteractionImporter> importers = new ArrayList<InteractionImporter>();
		for(ImportComboBoxPanel p:importPanels){
			importers.add(p.getInteractionImporter());
		}
		InteractionImporter importer = 
				new CombinedInteractionImporter(importers);
		return importer;
	}
	
	
}

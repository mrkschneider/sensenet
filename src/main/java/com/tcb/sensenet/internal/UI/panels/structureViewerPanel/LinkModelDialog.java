package com.tcb.sensenet.internal.UI.panels.structureViewerPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure.StructureImportPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.structureViewer.config.AddModelToStructureViewerTaskConfig;
import com.tcb.sensenet.internal.task.structureViewer.config.AddModelToStructureViewerTaskConfigImpl;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureLoader;
import com.tcb.sensenet.internal.task.structureViewer.factories.LinkModelToStructureViewerTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;

public class LinkModelDialog extends DefaultDialog {
			
	private AppGlobals appGlobals;
		
	private StructureImportPanel importPanel;

	public LinkModelDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		this.setLayout(new GridBagLayout());
		
		this.setTitle("Add model");
		
		addImportPanel();
		
		this.add(
				DialogUtil.createActionPanel(this::confirm, this::dispose),
				getDefaultConstraints());

		this.pack();
	}
	
	
	private void addImportPanel(){
		importPanel = createImportPanel();
		this.add(importPanel, getDefaultConstraints());
	}
	
	private StructureImportPanel createImportPanel(){
		StructureImportPanel p = new StructureImportPanel(this, appGlobals);
		return p;
	}
		
	private GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
		
	private AddModelToStructureViewerTaskConfig getLoadModelTaskConfig(){
		StructureLoader modelLoader = importPanel.getModelLoader();
		String modelName = importPanel.getModelName();
		return new AddModelToStructureViewerTaskConfigImpl(modelLoader,modelName);
	}
	
	private void confirm(){
		TaskIterator tasks = new TaskIterator();
		
		tasks.append(
					new LinkModelToStructureViewerTaskFactory(appGlobals)
					.createTaskIterator(
							getLoadModelTaskConfig()));
					
		appGlobals.taskManager.execute(tasks);
		this.dispose();
	}
			
}

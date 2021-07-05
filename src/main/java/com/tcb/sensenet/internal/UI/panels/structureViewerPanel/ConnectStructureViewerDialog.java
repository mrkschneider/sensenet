package com.tcb.sensenet.internal.UI.panels.structureViewerPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JOptionPane;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.viewer.SelectViewerPanel;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.sensenet.internal.task.structureViewer.config.ConnectStructureViewerTaskConfig;
import com.tcb.sensenet.internal.task.structureViewer.config.ConnectStructureViewerTaskConfigImpl;
import com.tcb.sensenet.internal.task.structureViewer.factories.ConnectStructureViewerTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.netmap.structureViewer.ViewerFactory;

public class ConnectStructureViewerDialog extends DefaultDialog {
			
	private AppGlobals appGlobals;
		
	private SelectViewerPanel loadViewerPanel;

	public ConnectStructureViewerDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		this.setLayout(new GridBagLayout());
		
		this.setTitle("Connect viewer");
		
		addLoadViewerPanel();
		
		this.add(
				DialogUtil.createActionPanel(this::confirm, this::dispose),
				getDefaultConstraints());
		
		this.pack();
	}
			
	private void addLoadViewerPanel(){
		loadViewerPanel = new SelectViewerPanel(this, appGlobals);
		this.add(loadViewerPanel, getDefaultConstraints());
	}
	
	private GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	private ConnectStructureViewerTaskConfig getConnectTaskConfig(){
		try {
			ViewerType viewerType = loadViewerPanel.getSelected();
			ViewerFactory viewerFactory = loadViewerPanel.getViewerFactory();
			ConnectStructureViewerTaskConfig config = new ConnectStructureViewerTaskConfigImpl(
					viewerFactory, viewerType);
			return config;
		} catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			throw(e);
		}
	}
		
	private void confirm(){
		TaskIterator tasks = new ConnectStructureViewerTaskFactory(appGlobals)
				.createTaskIterator(
							getConnectTaskConfig());
				
		appGlobals.taskManager.execute(tasks);
		this.dispose();
	}
		
}

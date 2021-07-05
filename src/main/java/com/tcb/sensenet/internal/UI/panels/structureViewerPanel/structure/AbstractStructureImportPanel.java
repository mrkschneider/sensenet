package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure;

import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JDialog;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureLoader;

public abstract class AbstractStructureImportPanel extends DefaultPanel {
	
	public abstract StructureLoader getModelLoader();
	public abstract String getModelName();
	
	
	protected StructureViewerManager viewerManager;
	protected JDialog dialog;
	
	public AbstractStructureImportPanel(
			JDialog dialog,
			StructureViewerManager viewerManager){
		this.dialog = dialog;
		this.viewerManager = viewerManager;
		
	}
	
	protected List<String> getCurrentLoadedModels(){
		try{
			return viewerManager.getViewer().getModels();
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	
	
	
	
}

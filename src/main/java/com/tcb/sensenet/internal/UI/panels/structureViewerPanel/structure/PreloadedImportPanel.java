package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.task.structureViewer.config.PreloadedStructureLoader;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureLoader;

public class PreloadedImportPanel extends AbstractStructureImportPanel {

	private JComboBox<String> modelNameBox;

	public PreloadedImportPanel(JDialog dialog,
			StructureViewerManager viewerManager) {
		super(dialog, viewerManager);
		
		addModelNamePanel();
	}
			
	protected void addModelNamePanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		String[] models = getCurrentLoadedModels().stream()
				.toArray(String[]::new);
		String defaultModel = "";
		if(models.length > 0){
			defaultModel = models[0];
		}

		modelNameBox = p.addChoosableParameter("Model name", models, defaultModel);
		this.add(p);
	}
	
	@Override
	public String getModelName(){
		String name = (String)modelNameBox.getSelectedItem();
		if(name==null || name.isEmpty()){
			throw new RuntimeException("No models loaded in viewer");
		}
		return name;
	}
	
	
	@Override
	public StructureLoader getModelLoader() {
		Set<String> currentLoadedModels = new HashSet<>(getCurrentLoadedModels());
				
		String modelName = getModelName();
		if(!currentLoadedModels.contains(modelName)){
			throw new IllegalArgumentException("Could not find model: " + modelName);
		}
		
		return new PreloadedStructureLoader();
	}

}

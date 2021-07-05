package com.tcb.sensenet.internal.task.style;

import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewAdapter;

public class CreateAutoStyleTask extends AbstractTask {
	private AppGlobals appGlobals;
	private VisualMappingFunction<?,?> mapping;
	
	private static final String suffix = "_auto";

	public CreateAutoStyleTask(
			VisualMappingFunction<?,?> mapping,
			AppGlobals appGlobals){
		this.mapping = mapping;
		this.appGlobals = appGlobals;
	}

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		CyNetworkViewAdapter view = appGlobals.applicationManager.getCurrentNetworkView();
		VisualStyle oldStyle = appGlobals.visualMappingManager.getVisualStyle(view.getAdaptedNetworkView());
		
		String baseStyleName = oldStyle.getTitle().replaceAll(suffix + ".*$", "");
		String styleName = baseStyleName + suffix;
		
		VisualStyle newStyle = appGlobals.visualMappingManager.getAllVisualStyles()
				.stream()
				.filter(v -> v.getTitle().equals(styleName))
				.findFirst()
				.orElseGet(() -> createStyle(styleName,oldStyle));
				
		newStyle.addVisualMappingFunction(mapping);
		
		if(mapping.getVisualProperty().equals(BasicVisualLexicon.NODE_SIZE)){
			newStyle.getAllVisualPropertyDependencies().stream()
			.filter(d -> d.getIdString().equals("nodeSizeLocked"))
			.findFirst()
			.get()
			.setDependency(true);
		}
		
		appGlobals.visualMappingManager.setCurrentVisualStyle(newStyle);
	}
	
	private VisualStyle createStyle(String name, VisualStyle template){
		VisualStyle style = appGlobals.visualStyleFactory.createVisualStyle(template);
		style.setTitle(name);
		appGlobals.visualMappingManager.addVisualStyle(style);
		return style;
	}
	
	
}

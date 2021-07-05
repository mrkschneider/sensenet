package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure;

import java.awt.event.ItemEvent;

import javax.swing.JDialog;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.util.ComboBoxPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureLoader;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureType;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class StructureImportPanel extends ComboBoxPanel<StructureType,AbstractStructureImportPanel>{

	private AppGlobals appGlobals;
	private JDialog dialog;

	private static final AppProperty defaultModelTypeProperty = 
			AppProperty.STRUCTURE_VIEWER_DEFAULT_STRUCTURE_TYPE;

	public StructureImportPanel(
			JDialog dialog,
			AppGlobals appGlobals){
		super(
				createDefaultImporterPanel(
						dialog,
						appGlobals.structureViewerManager, 
						appGlobals.fileUtil, 
						appGlobals.appProperties),
				getDefaultSelectedIndex(appGlobals.appProperties));
		this.dialog = dialog;
		this.appGlobals = appGlobals;
				
		JPanelUtil.setBorders(this, "Select structure");
	}
	
	protected static AbstractStructureImportPanel createImporterPanel(
			StructureType type,
			JDialog dialog,
			StructureViewerManager viewerManager,
			FileUtil fileUtil){
		switch(type){
		case SINGLE_STRUCTURE: return new SingleStructureImportPanel(dialog, viewerManager, fileUtil);
		case PRELOADED: return new PreloadedImportPanel(dialog, viewerManager);
		case TRAJECTORY: return new TrajectoryImportPanel(dialog, viewerManager, fileUtil);
		default: throw new IllegalArgumentException();
		}
	}
	
	protected static AbstractStructureImportPanel createDefaultImporterPanel(
			JDialog dialog,
			StructureViewerManager viewerManager,
			FileUtil fileUtil, AppProperties appProperties){
		StructureType defaultType = StructureType.values()[getDefaultSelectedIndex(
				appProperties)];
		return createImporterPanel(defaultType,dialog,viewerManager,fileUtil);
	}
	
	protected static int getDefaultSelectedIndex(AppProperties appProperties){
			return appProperties
					.getEnumOrDefault(StructureType.class, defaultModelTypeProperty)
					.ordinal();
	}
	
	public StructureLoader getModelLoader(){
		appGlobals.appProperties.set(defaultModelTypeProperty, getSelected().name());
		return getContentPanel().getModelLoader();
	}
	
	public String getModelName(){
		return getContentPanel().getModelName();
	}

	@Override
	protected JDialog getParentDialog() {
		return dialog;
	}

	@Override
	protected AbstractStructureImportPanel updateContentPanel(ItemEvent e) {
		StructureType type = (StructureType)e.getItem();
		return createImporterPanel(
				type,
				dialog,
				appGlobals.structureViewerManager,
				appGlobals.fileUtil);
	}

	@Override
	protected StructureType[] getSelectableItems() {
		return StructureType.values();
	}
	
}

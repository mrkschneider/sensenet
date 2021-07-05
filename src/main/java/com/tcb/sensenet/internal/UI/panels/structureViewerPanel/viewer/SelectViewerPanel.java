package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.viewer;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.sensenet.internal.structureViewer.ViewerTypeProperties;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.netmap.structureViewer.ViewerFactory;
import com.tcb.netmap.structureViewer.chimera.ChimeraViewerFactory;
import com.tcb.netmap.structureViewer.pymol.PymolViewerFactory;
import com.tcb.netmap.structureViewer.vmd.VMDViewerFactory;

public class SelectViewerPanel extends DefaultPanel {
	private AppGlobals appGlobals;
		
	private JComboBox<ViewerType> viewerTypeBox;
	private FileButton loadSessionFileButton;

	private JDialog dialog;
	
	private static final AppProperty defaultViewerProperty = AppProperty.STRUCTURE_VIEWER_DEFAULT;
	
	private static final AppProperty maxShownResiduesProperty = 
			AppProperty.STRUCTURE_VIEWER_MAX_SHOWN_RESIDUES;
	private static final AppProperty maxShownInteractionsProperty = 
			AppProperty.STRUCTURE_VIEWER_MAX_SHOWN_INTERACTIONS;
	
	public SelectViewerPanel(
			JDialog dialog,
			AppGlobals appGlobals){
		this.dialog = dialog;
		this.appGlobals = appGlobals;
			
		addSelectViewerBox(this);
		addLoadSessionButton(this);
		
		JPanelUtil.setBorders(this, "Viewer settings");
		
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	private void addSelectViewerBox(Container target){
		ViewerType defaultViewer = 	appGlobals.appProperties.getEnumOrDefault(
				ViewerType.class, defaultViewerProperty);
		LabeledParametersPanel p = new LabeledParametersPanel();
		viewerTypeBox = p.addChoosableParameter("Viewer", ViewerType.values(), defaultViewer);
		target.add(p);
	}
	
	private void addLoadSessionButton(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		loadSessionFileButton = p.addShortFileParameter(
				"Load session (optional)",
				null,
				"None",
				"Session file",
				new String[]{},
				"Load session file",
				appGlobals.fileUtil);
		target.add(p);		
	}
	
	public ViewerType getSelected(){
		return (ViewerType)viewerTypeBox.getSelectedItem();
	}
			
	public ViewerFactory getViewerFactory(){
		AppProperties appProperties = appGlobals.appProperties;
		ViewerType selected = getSelected();
		AppProperty defaultViewerCommandProperty = ViewerTypeProperties.getProperty(selected);
		String bin = appProperties.getOrDefault(defaultViewerCommandProperty);
		List<String> args = Arrays.asList(bin);
		Optional<Path> sessionPath = getMaybeSessionPath();
				
		Integer maxShownResidues = Integer.valueOf(
				appProperties.getOrDefault(maxShownResiduesProperty));
		Integer maxShownInteractions = Integer.valueOf(
				appProperties.getOrDefault(maxShownInteractionsProperty));
		appProperties.set(defaultViewerProperty, selected.name());
		
		
		switch(getSelected()){
		case PYMOL: return new PymolViewerFactory(args,maxShownResidues,maxShownInteractions,sessionPath);
		case VMD: return new VMDViewerFactory(args,maxShownResidues,maxShownInteractions,sessionPath);
		case CHIMERA: return new ChimeraViewerFactory(args,maxShownResidues,maxShownInteractions,sessionPath);
		default: throw new IllegalArgumentException();
		}		
	}
	
	
	private Optional<Path> getMaybeSessionPath(){
		Optional<File> file = loadSessionFileButton.getMaybeFile();
		if(file.isPresent()){
			return Optional.of(file.get().toPath());
		} else {
			return Optional.empty();
		}
	}
	
				
}

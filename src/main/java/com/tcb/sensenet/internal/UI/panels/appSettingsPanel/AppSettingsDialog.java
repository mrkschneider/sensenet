package com.tcb.sensenet.internal.UI.panels.appSettingsPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.Optional;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cytoscape.util.swing.ColorButton;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.JPanelUtil;

public class AppSettingsDialog extends DefaultDialog {
	private AppGlobals appGlobals;
	
	private JCheckBox autoZoomBox;
	private JTextField maxShownResiduesField;
	private JTextField maxShownInteractionsField;
	private AppProperties appProperties;
	private FileButton pymolViewerPathButton;
	private FileButton vmdViewerPathButton;
	private FileButton chimeraViewerPathButton;
	private ColorButton viewerInteractionColorButton;
	private ColorButton viewerSelectionColorButton;
	
	private static final AppProperty autoZoomDefaultProperty = 
			AppProperty.STRUCTURE_VIEWER_DEFAULT_ZOOM;
	
	public AppSettingsDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.appProperties = appGlobals.appProperties;
		
		this.setLayout(new GridBagLayout());
		
		this.setTitle(CyActivator.APP_NAME_SHORT + " settings");
		
		addStructureViewerPanel(this);
		
		
		this.add(
				DialogUtil.createActionPanel(this::confirm,this::dispose),
				getDefaultConstraints());

		this.pack();
	}
	
	private GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	private void addStructureViewerPanel(Container target){
		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		JPanelUtil.setBorders(p, "Structure viewer");
		
		addViewerPathPanels(p);
		addMaxShownFieldsPanel(p);
		addViewerColorsPanel(p);
		addAutoZoomBox(p);
		
		target.add(p, getDefaultConstraints());
	}
	
	private void addAutoZoomBox(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		Boolean defaultValue = Boolean.valueOf(
				appProperties.getOrDefault(autoZoomDefaultProperty));

		autoZoomBox = p.addBooleanParameter("Zoom to selection", defaultValue);

		target.add(p, getDefaultConstraints());
	}
	
	private void addViewerPathPanels(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		String pymolViewerDefaultCmd = appProperties.getOrNull(
				AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_PYMOL);
		String vmdViewerDefaultCmd = appProperties.getOrNull(
				AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_VMD);
		String chimeraViewerDefaultCmd = appProperties.getOrNull(
				AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_CHIMERA);
		
		pymolViewerPathButton = p.addFileParameter(
				ViewerType.PYMOL.toString() + " location",
				pymolViewerDefaultCmd,
				AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_PYMOL.getDefaultValue(),				
				"viewer",
				new String[]{},
				"Select structure viewer location",
				appGlobals.fileUtil);
		
		vmdViewerPathButton = p.addFileParameter(
				ViewerType.VMD.toString() + " location", 
				vmdViewerDefaultCmd,
				AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_VMD.getDefaultValue(),
				"viewer",
				new String[]{},
				"Select structure viewer location",
				appGlobals.fileUtil);
		
		chimeraViewerPathButton = p.addFileParameter(
				ViewerType.CHIMERA.toString() + " location",
				chimeraViewerDefaultCmd,
				AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_CHIMERA.getDefaultValue(),
				"viewer",
				new String[]{},
				"select structure viewer location",
				appGlobals.fileUtil);
				
		
		
		Dimension maxSize = new Dimension(200,25);
		//pymolViewerPathButton.setMaximumSize(maxSize);
		//vmdViewerPathButton.setMaximumSize(maxSize);
		
		pymolViewerPathButton.setPreferredSize(maxSize);
		vmdViewerPathButton.setPreferredSize(maxSize);
		chimeraViewerPathButton.setPreferredSize(maxSize);
		
		target.add(p, getDefaultConstraints());
	}
	
	private void addMaxShownFieldsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();

		
		String defaultMaxShownResidues = appGlobals.appProperties.getOrDefault(
				AppProperty.STRUCTURE_VIEWER_MAX_SHOWN_RESIDUES);
		String defaultMaxShownInteractions = appGlobals.appProperties.getOrDefault(
				AppProperty.STRUCTURE_VIEWER_MAX_SHOWN_INTERACTIONS);
		
		maxShownResiduesField = 
				p.addTextParameter("Max shown residues", defaultMaxShownResidues);
		maxShownInteractionsField = 
				p.addTextParameter("Max shown interactions", defaultMaxShownInteractions);
				
		target.add(p, getDefaultConstraints());
	}
	
	private void addViewerColorsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		AppProperties appProperties = appGlobals.appProperties;
		Color defaultInteractionColor = new Color(
				Integer.valueOf(
						appProperties.getOrDefault(
				AppProperty.STRUCTURE_VIEWER_DEFAULT_INTERACTION_COLOR)));
		Color defaultSelectedColor = new Color(
				Integer.valueOf(
						appProperties.getOrDefault(
				AppProperty.STRUCTURE_VIEWER_DEFAULT_SELECTED_COLOR)));
		
		viewerInteractionColorButton = p.addColorParameter("Selected interaction color", defaultInteractionColor);
		viewerSelectionColorButton = p.addColorParameter("Selected residue color", defaultSelectedColor);
		
		target.add(p, getDefaultConstraints());
		
	}
	
	private void confirm(){
		try{
			updateProperties();
		} catch(Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		dispose();
	}
	
	private void updateProperties() throws Exception {
		Boolean zoom = autoZoomBox.isSelected();
		
		Integer maxShownResidues = Integer.parseInt(maxShownResiduesField.getText());
		Integer maxShownInteractions = Integer.parseInt(maxShownInteractionsField.getText());
		
		Optional<File> pymolViewerPathFile = pymolViewerPathButton.getMaybeFile();
		Optional<File> vmdViewerPathFile = vmdViewerPathButton.getMaybeFile();
		Optional<File> chimeraViewerPathFile = chimeraViewerPathButton.getMaybeFile();
		
		String pymolViewerPath = getViewerPath(pymolViewerPathFile);
		String vmdViewerPath = getViewerPath(vmdViewerPathFile);
		String chimeraViewerPath = getViewerPath(chimeraViewerPathFile);
		
		String viewerDefaultInteractionColor = Integer.toString(viewerInteractionColorButton.getColor().getRGB());
		String viewerDefaultSelectedColor = Integer.toString(viewerSelectionColorButton.getColor().getRGB());
				
		appProperties.set(autoZoomDefaultProperty, zoom.toString());
		appProperties.set(AppProperty.STRUCTURE_VIEWER_MAX_SHOWN_RESIDUES, maxShownResidues.toString());
		appProperties.set(AppProperty.STRUCTURE_VIEWER_MAX_SHOWN_INTERACTIONS, maxShownInteractions.toString());
		appProperties.set(AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_PYMOL, pymolViewerPath);
		appProperties.set(AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_VMD, vmdViewerPath);
		appProperties.set(AppProperty.STRUCTURE_VIEWER_DEFAULT_COMMAND_CHIMERA, chimeraViewerPath);
		appProperties.set(AppProperty.STRUCTURE_VIEWER_DEFAULT_INTERACTION_COLOR, viewerDefaultInteractionColor);
		appProperties.set(AppProperty.STRUCTURE_VIEWER_DEFAULT_SELECTED_COLOR, viewerDefaultSelectedColor);
	}
	
	private String getViewerPath(Optional<File> viewerPathFile){
		if(viewerPathFile.isPresent()){
			return viewerPathFile.get().getAbsolutePath();
		} else {
			return null;
		}
	}
	
	
	
}

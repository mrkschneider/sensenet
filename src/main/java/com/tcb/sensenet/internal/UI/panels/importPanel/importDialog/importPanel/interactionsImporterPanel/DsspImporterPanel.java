package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.io.File;
import java.io.IOException;

import javax.swing.JTextField;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.dsspImporter.DsspImporter;



public class DsspImporterPanel extends AbstractInteractionsImporterPanel {
	
	private FileButton dsspFileButton;
	private JTextField interactionTypeField;
	
	private static final AppProperty defaultInteractionTypeProperty = AppProperty.IMPORT_DSSP_DEFAULT_INTERACTION_TYPE;

	
	public DsspImporterPanel(ImportNetworkDialog parentDialog, FileUtil fileUtil, AppProperties appProperties){
		super(parentDialog,appProperties);
		
		LabeledParametersPanel p = new LabeledParametersPanel();
						
		dsspFileButton = p.addShortFileParameter(
				"DSSP file",null,
				fileNotLoadedLabel,
				".dssp file",new String[]{"dssp"}, "open DSSP file", fileUtil);
		
		interactionTypeField = addInteractionTypeField(p, defaultInteractionTypeProperty);
				
		this.add(p);
		registerUpdateDialogListener();
	}
	
	@Override
	protected InteractionImporter createInteractionImporter() throws IOException,IllegalArgumentException {
		File dsspFile = dsspFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No DSSP file chosen"));
		String interactionType = getAndUpdateInteractionType(
				interactionTypeField,defaultInteractionTypeProperty);
		return new DsspImporter(
				dsspFile.toPath(),interactionType);
	}

	@Override
	protected FileButton getMainFileButton() {
		return dsspFileButton;
	}
	
	
	
	
}

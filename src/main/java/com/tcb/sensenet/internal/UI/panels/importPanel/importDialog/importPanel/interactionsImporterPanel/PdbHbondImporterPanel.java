package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextField;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.properties.AppPropertyUpdater;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.pdbImporter.PdbHbondImporter;


public class PdbHbondImporterPanel extends AbstractPdbImporterPanel {
	
	private static final AppProperty defaultDistanceCutoffPropertyName = AppProperty.IMPORT_PDB_HBOND_DEFAULT_DISTANCE_CUTOFF;
	private static final AppProperty defaultAngleCutoffPropertyName = AppProperty.IMPORT_PDB_HBOND_DEFAULT_ANGLE_CUTOFF;
	private static final AppProperty defaultDonorMaskPropertyName = AppProperty.IMPORT_PDB_HBOND_DEFAULT_DONOR_MASK;
	private static final AppProperty defaultAcceptorMaskPropertyName = AppProperty.IMPORT_PDB_HBOND_DEFAULT_ACCEPTOR_MASK;
	private static final AppProperty defaultInteractionTypeProperty = AppProperty.IMPORT_PDB_HBOND_DEFAULT_INTERACTION_TYPE;
	
	private FileUtil fileUtil;
	private FileButton pdbFileButton;
	private JTextField distanceCutoffField;
	private JTextField angleCutoffField;
	private JTextField donorMaskField;
	private JTextField acceptorMaskField;
	private AppProperties appProperties;
	private JTextField interactionTypeField;
		
	
	public PdbHbondImporterPanel(
			ImportNetworkDialog parentDialog,
			FileUtil fileUtil,
			AppProperties appProperties){
		super(parentDialog,appProperties);
		this.appProperties = appProperties;
		this.fileUtil = fileUtil;
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		String defaultDistanceCutoff = appProperties.getOrDefault(defaultDistanceCutoffPropertyName);
		String defaultAngleCutoff = appProperties.getOrDefault(defaultAngleCutoffPropertyName);
		String defaultDonorMask = appProperties.getOrDefault(defaultDonorMaskPropertyName);
		String defaultAcceptorMask = appProperties.getOrDefault(defaultAcceptorMaskPropertyName);
		
		pdbFileButton = p.addShortFileParameter("PDB file", null,
				fileNotLoadedLabel, ".pdb file", new String[]{"pdb"}, "open .pdb file", fileUtil);
		
		distanceCutoffField = p.addTextParameter("Distance cut-off", defaultDistanceCutoff);
		angleCutoffField = p.addTextParameter("Angle cut-off", defaultAngleCutoff);
		donorMaskField = p.addTextParameter("Donor mask", defaultDonorMask);
		acceptorMaskField = p.addTextParameter("Acceptor mask", defaultAcceptorMask);
		interactionTypeField = addInteractionTypeField(p,defaultInteractionTypeProperty);
		
		this.add(p);
		registerUpdateDialogListener();
	}
	
	@Override
	protected InteractionImporter createInteractionImporter() throws IOException,IllegalArgumentException {
		File file = pdbFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No pdb file chosen"));
		
		AppPropertyUpdater updater = new AppPropertyUpdater(appProperties);
		
		Double distanceCutoff = updater.update(
				getDistanceCutoff(distanceCutoffField),
				defaultDistanceCutoffPropertyName);
		Double angleCutoff = updater.update(
				getAngleCutoff(angleCutoffField),
				defaultAngleCutoffPropertyName);
		String donorMask = updater.update(
				donorMaskField.getText(),
				defaultDonorMaskPropertyName);
		String acceptorMask = updater.update(
				acceptorMaskField.getText(),
				defaultAcceptorMaskPropertyName);
		String interactionType = getAndUpdateInteractionType(interactionTypeField,defaultInteractionTypeProperty);
			
		Set<String> ignoreAtomNames = new HashSet<>();
		return new PdbHbondImporter(
				file.toPath(),distanceCutoff,
				angleCutoff,donorMask,
				acceptorMask,interactionType, ignoreAtomNames);
	}
	
	@Override
	protected FileButton getMainFileButton() {
		return pdbFileButton;
	}
		
}

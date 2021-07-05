package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.properties.AppPropertyUpdater;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.pdbImporter.PdbContactImporter;


public class PdbContactImporterPanel extends AbstractPdbImporterPanel {
	
	private static final AppProperty defaultCutoffPropertyName = AppProperty.IMPORT_PDB_CONTACT_DEFAULT_CUTOFF;
	private static final AppProperty defaultMaskPropertyName = AppProperty.IMPORT_PDB_CONTACT_DEFAULT_MASK;
	private static final AppProperty ignoreBackboneProperty = AppProperty.IMPORT_PDB_CONTACT_IGNORE_BB;
	private static final AppProperty ignoreIntraResidueContactsProperty = AppProperty.IMPORT_PDB_CONTACT_IGNORE_INTRA_RESIDUE_CONTACTS;
	private static final AppProperty defaultInteractionTypeProperty = AppProperty.IMPORT_PDB_CONTACT_DEFAULT_INTERACTION_TYPE;
	
	private AppProperties appProperties;
	
	private FileUtil fileUtil;
	private FileButton pdbFileButton;
	private JTextField distanceCutoffField;
	private JTextField atomMaskField;
	private JCheckBox ignoreBackboneBox;
	private JCheckBox ignoreIntraResidueContactsBox;
	private JTextField interactionTypeField;
	
	public PdbContactImporterPanel(
			ImportNetworkDialog parentDialog,
			FileUtil fileUtil,
			AppProperties appProperties){
		super(parentDialog,appProperties);
		this.appProperties = appProperties;
		this.fileUtil = fileUtil;
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		String defaultCutoff = appProperties.getOrDefault(defaultCutoffPropertyName);
		String defaultMask = appProperties.getOrDefault(defaultMaskPropertyName);
		Boolean defaultIgnoreBackbone = Boolean.valueOf(
				appProperties.getOrDefault(ignoreBackboneProperty));
		Boolean defaultIgnoreIntraResidueContacts = Boolean.valueOf(
				appProperties.getOrDefault(ignoreIntraResidueContactsProperty));
		
		pdbFileButton = p.addShortFileParameter("PDB file", null,
				fileNotLoadedLabel, ".pdb file", new String[]{"pdb"}, "open .pdb file", fileUtil);
		
		distanceCutoffField = p.addTextParameter("Distance cut-off", defaultCutoff);
		atomMaskField = p.addTextParameter("Atom mask", defaultMask);
		interactionTypeField = addInteractionTypeField(p,defaultInteractionTypeProperty);
		ignoreBackboneBox = p.addBooleanParameter("Ignore backbone", defaultIgnoreBackbone);
		ignoreIntraResidueContactsBox = p.addBooleanParameter("Ignore intra-residue", defaultIgnoreIntraResidueContacts);
		
			
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
				defaultCutoffPropertyName);
		String atomMask = updater.update(
				atomMaskField.getText(),
				defaultMaskPropertyName);
		Boolean ignoreBackbone = updater.update(
				ignoreBackboneBox.isSelected(),
				ignoreBackboneProperty);
		Boolean ignoreIntraResidueContacts = updater.update(
				ignoreIntraResidueContactsBox.isSelected(),
				ignoreIntraResidueContactsProperty);
		
		String interactionType = getAndUpdateInteractionType(interactionTypeField,
				defaultInteractionTypeProperty);
		Set<String> ignoreAtomNames = getIgnoreAtomNames(ignoreBackbone);
				
		return new PdbContactImporter(
				file.toPath(),
				distanceCutoff,
				atomMask,
				interactionType,
				ignoreAtomNames,
				ignoreIntraResidueContacts);
	}
	
	
	@Override
	protected FileButton getMainFileButton() {
		return pdbFileButton;
	}
}

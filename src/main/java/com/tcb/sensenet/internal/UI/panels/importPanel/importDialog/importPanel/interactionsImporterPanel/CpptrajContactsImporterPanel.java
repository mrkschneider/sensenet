package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.ColumnsShortStringRenderer;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.UI.util.TextComboBox;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.properties.AppPropertyUpdater;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.amberImporter.AmberNativeContactsImporter;



public class CpptrajContactsImporterPanel extends AbstractInteractionsImporterPanel {
	
	private FileButton contactsFileButton, timelineFileButton, contactsPdbFileButton;
	private FileButton nonnativeTimelineFileButton;
	
	private static final AppProperty ignoreBackboneProperty = AppProperty.IMPORT_AMBER_NATIVECONTACTS_IGNORE_BB;
	private static final AppProperty ignoreIntraResidueContactsProperty = AppProperty.IMPORT_AMBER_NATIVECONTACTS_IGNORE_INTRA_RESIDUE_CONTACTS;
	private static final AppProperty defaultInteractionTypeProperty = AppProperty.IMPORT_AMBER_NATIVECONTACTS_DEFAULT_INTERACTION_TYPE;
	private static final AppProperty defaultSieveFramesProperty = AppProperty.IMPORT_DEFAULT_SIEVE_FRAMES;
	private static final AppProperty defaultTimelineMinAvgProperty = AppProperty.IMPORT_DEFAULT_TIMELINE_MIN_AVG;
	
	
	private JCheckBox ignoreAtomNamesBox;
	private JCheckBox ignoreIntraResidueContactsBox;
	private AppProperties appProperties;
	private JTextField interactionTypeField;
	private JTextField  sieveField;
	private TextComboBox<Columns> minAvgField;

	
	
	public CpptrajContactsImporterPanel(
			ImportNetworkDialog parentDialog,
			FileUtil fileUtil,
			AppProperties appProperties){
		super(parentDialog,appProperties);
		
		this.appProperties = appProperties;
		
		Boolean defaultIgnoreBackbone = Boolean.valueOf(
				appProperties.getOrDefault(ignoreBackboneProperty));
		Boolean defaultIgnoreIntraResidueContacts = Boolean.valueOf(
				appProperties.getOrDefault(ignoreIntraResidueContactsProperty));
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		contactsFileButton = p.addShortFileParameter(
				"Contacts .out file", null,
				fileNotLoadedLabel,
				".out file", new String[]{"out"}, "Open CPPTRAJ contacts .out file",
				fileUtil);
		
		timelineFileButton = p.addShortFileParameter(
				"Native timeline file",null,
				fileNotLoadedLabel,
				".series file",new String[]{"series"},"Open CPPTRAJ contacts .series file",
				fileUtil);
		
		nonnativeTimelineFileButton = p.addShortFileParameter(
				"Nonnative timeline file",null,
				optionalFileLabel,
				".series file",new String[]{"series"},"Open CPPTRAJ contacts .series file",
				fileUtil);
		
		contactsPdbFileButton = p.addShortFileParameter(
				"Contacts .pdb file",null,
				fileNotLoadedLabel,
				".pdb file",new String[]{"pdb"},"Open CPPTRAJ contacts .pdb file",
				fileUtil);
				
		interactionTypeField = addInteractionTypeField(p, defaultInteractionTypeProperty);	
		ignoreAtomNamesBox = p.addBooleanParameter("ignore backbone", defaultIgnoreBackbone);
		ignoreIntraResidueContactsBox = p.addBooleanParameter("ignore intra-residue", defaultIgnoreIntraResidueContacts);
		
		sieveField = p.addTextParameter("Frame sieve", appProperties.getOrDefault(defaultSieveFramesProperty));
		minAvgField = p.addTextChoosableParameter(
				"Skip timelines <",
				new Columns[]{AppColumns.AVERAGE_INTERACTIONS},
				AppColumns.AVERAGE_INTERACTIONS,
				appProperties.getOrDefault(defaultTimelineMinAvgProperty));
		
		@SuppressWarnings("unchecked")
		ListCellRenderer<Columns> renderer = new ColumnsShortStringRenderer(minAvgField.getComboBox());
		minAvgField.getComboBox().setRenderer(renderer);
		
		
		this.add(p);
		registerUpdateDialogListener();
	}
	
	protected InteractionImporter createInteractionImporter() throws IOException,IllegalArgumentException{
		File hbondFile = contactsFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No contacts file chosen"));
		File pdbFile = contactsPdbFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No contacts pdb file chosen"));
		
		AppPropertyUpdater updater = new AppPropertyUpdater(appProperties);
				
		Boolean ignoreBackbone = 
				updater.update(
						ignoreAtomNamesBox.isSelected(),
						ignoreBackboneProperty);
		Boolean ignoreIntraResidueContacts = updater.update(
				ignoreIntraResidueContactsBox.isSelected(),
				ignoreIntraResidueContactsProperty);
		
		String interactionType = getAndUpdateInteractionType(
				interactionTypeField,defaultInteractionTypeProperty);
		
		Set<String> ignoreAtomNames = getIgnoreAtomNames(ignoreBackbone);
		List<Path> timelinePaths = getTimelinePaths();
		
		Integer skipFrames = updater.update(
				Integer.parseInt(sieveField.getText()),
				defaultSieveFramesProperty);
		Double minAvg = updater.update(
				Double.parseDouble(minAvgField.getField().getText()),
				defaultTimelineMinAvgProperty);
						
		return new AmberNativeContactsImporter(
				hbondFile.toPath(),
				timelinePaths,
				pdbFile.toPath(),
				interactionType,
				ignoreAtomNames,
				ignoreIntraResidueContacts,
				skipFrames,minAvg);
	}
	
	private List<Path> getTimelinePaths() throws IOException {
		File timelineFile = timelineFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No Contacts timeline file chosen"));
		List<Path> timelinePaths = new ArrayList<>();
		timelinePaths.add(timelineFile.toPath());
		Optional<File> maybeNonnativeTimelineFile = nonnativeTimelineFileButton.getMaybeFile();
		if(maybeNonnativeTimelineFile.isPresent()){
			File f = maybeNonnativeTimelineFile.get();
			timelinePaths.add(f.toPath());
		}
		return timelinePaths;
	}

	@Override
	protected FileButton getMainFileButton() {
		return contactsFileButton;
	}
	
}

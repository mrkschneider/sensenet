package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.io.File;
import java.io.IOException;
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
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.amberImporter.AmberHbondImporter;



public class CpptrajHbondImporterPanel extends AbstractInteractionsImporterPanel {
	
	private FileButton hBondFileButton, timelineFileButton;
	private JTextField interactionTypeField;
	private JCheckBox ignoreAtomNamesBox;
	private AppProperties appProperties;
	private JTextField  sieveField;
	private TextComboBox<Columns>  minAvgField;
		
	private static final AppProperty defaultInteractionTypeProperty = AppProperty.IMPORT_AMBER_HBOND_DEFAULT_INTERACTION_TYPE;
	private static final AppProperty ignoreBackboneProperty = AppProperty.IMPORT_AMBER_HBOND_IGNORE_BB;
	private static final AppProperty defaultSieveFramesProperty = AppProperty.IMPORT_DEFAULT_SIEVE_FRAMES;
	private static final AppProperty defaultTimelineMinAvgProperty = AppProperty.IMPORT_DEFAULT_TIMELINE_MIN_AVG;
	
	public CpptrajHbondImporterPanel(ImportNetworkDialog parentDialog, FileUtil fileUtil, AppProperties appProperties){
		super(parentDialog,appProperties);
		this.appProperties = appProperties;
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		Boolean defaultIgnoreBackbone = Boolean.valueOf(
				appProperties.getOrDefault(ignoreBackboneProperty));
		
		hBondFileButton = p.addShortFileParameter(
				"H-bond file", null,
				fileNotLoadedLabel, ".out file",
				new String[]{"out"}, "CPPTRAJ hbond .out file", fileUtil);

		timelineFileButton = p.addShortFileParameter(
				"Timeline file", null,
				fileNotLoadedLabel, ".series file",
				new String[]{"series"}, "CPPTRAJ hbond .series file", fileUtil);
		
		interactionTypeField = addInteractionTypeField(p, defaultInteractionTypeProperty);
		ignoreAtomNamesBox = p.addBooleanParameter("ignore backbone", defaultIgnoreBackbone);
		
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
	
	@Override
	protected InteractionImporter createInteractionImporter() throws IOException {
		File hbondFile = hBondFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No H-bond file chosen"));
		File timelineFile = timelineFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No H-bond timeline file chosen"));
		
		String interactionType = getAndUpdateInteractionType(
				interactionTypeField,defaultInteractionTypeProperty);
		
		Boolean ignoreBackbone = ignoreAtomNamesBox.isSelected();
		
		appProperties.set(ignoreBackboneProperty, ignoreBackbone.toString());
		
		Set<String> ignoreAtomNames = getIgnoreAtomNames(ignoreBackbone);
		
		Integer skipFrames = Integer.parseInt(sieveField.getText());
		Double minAvg = Double.parseDouble(minAvgField.getField().getText());
				
		appProperties.set(defaultSieveFramesProperty, skipFrames.toString());
		appProperties.set(defaultTimelineMinAvgProperty, minAvg.toString());
		
		return new AmberHbondImporter(
				hbondFile.toPath(),
				timelineFile.toPath(),
				interactionType, ignoreAtomNames,
				skipFrames,minAvg);
	}

	@Override
	protected FileButton getMainFileButton() {
		return hBondFileButton;
	}
	

	
	
}

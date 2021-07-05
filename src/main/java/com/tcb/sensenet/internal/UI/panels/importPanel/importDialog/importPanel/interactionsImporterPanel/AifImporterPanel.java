package com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.importPanel.interactionsImporterPanel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import org.apache.commons.io.FilenameUtils;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.importPanel.importDialog.ImportNetworkDialog;
import com.tcb.sensenet.internal.UI.util.ColumnsShortStringRenderer;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.UI.util.TextComboBox;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.importer.AutoExtensionAifImporterFactory;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;
import com.tcb.aifgen.importer.aifImporter.ZaifImporter;


public class AifImporterPanel extends AbstractInteractionsImporterPanel {
	
	protected FileButton openFileButton;
	protected JTextField sieveField;
	protected TextComboBox<Columns> minAvgField;
	private AppProperties appProperties;
		
	private static final AppProperty defaultSieveFramesProperty = AppProperty.IMPORT_DEFAULT_SIEVE_FRAMES;
	private static final AppProperty defaultTimelineMinAvgProperty = AppProperty.IMPORT_DEFAULT_TIMELINE_MIN_AVG;
	
		
	public AifImporterPanel(ImportNetworkDialog parentDialog, FileUtil fileUtil, AppProperties appProperties){
		super(parentDialog,appProperties);
		this.appProperties = appProperties;
				
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		openFileButton = p.addShortFileParameter(".aif / .zaif file", null,
				fileNotLoadedLabel, ".aif file", new String[]{"aif","zaif"}, "open .aif atom interaction file",
				fileUtil);
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
	protected InteractionImporter createInteractionImporter() throws IOException,IllegalArgumentException {
		File aifFile = openFileButton.getMaybeFile().orElseThrow(
				() -> new IOException("No AIF file chosen"));
		Integer skipFrames = Integer.parseInt(sieveField.getText());
		Double minAvg = Double.parseDouble(minAvgField.getField().getText());
		
		appProperties.set(defaultSieveFramesProperty, skipFrames.toString());
		appProperties.set(defaultTimelineMinAvgProperty, minAvg.toString());
				
		return new AutoExtensionAifImporterFactory().create(aifFile.toString(),skipFrames,minAvg);
	}

	@Override
	protected FileButton getMainFileButton() {
		return openFileButton;
	}
	

		
}

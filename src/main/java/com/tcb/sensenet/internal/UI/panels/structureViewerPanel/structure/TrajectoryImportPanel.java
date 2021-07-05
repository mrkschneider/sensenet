package com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure;

import java.awt.Container;
import java.nio.file.Path;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.structure.listeners.UpdateFileFormatListener;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.structureViewer.ViewerType;
import com.tcb.sensenet.internal.task.structureViewer.config.StructureLoader;
import com.tcb.sensenet.internal.task.structureViewer.config.TrajectoryLoader;
import com.tcb.netmap.fileFormat.FileFormat;
import com.tcb.netmap.fileFormat.FormatCollection;


public class TrajectoryImportPanel extends AbstractStructureImportPanel {

	private FileUtil fileUtil;
	
	private SingleStructureImportPanel singleStructurePanel;
	private FileButton trajFileButton;
	private JComboBox<FileFormat> trajFormatField;
	
	public TrajectoryImportPanel(JDialog dialog, StructureViewerManager viewerManager, FileUtil fileUtil) {
		super(dialog, viewerManager);
		this.fileUtil = fileUtil;
		
		addTopologyPanel(this);
		addLoadTrajectoryPanel(this);
	}
	
	private void addTopologyPanel(Container target){
		TopologyImportPanel p = new TopologyImportPanel(dialog, viewerManager, fileUtil);
		singleStructurePanel = p;
		target.add(p);
	}
	
	private void addLoadTrajectoryPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		trajFileButton = p.addFileParameter(
				"Trajectory file",
				null,
				"None",
				"Trajectory file",
				new String[]{},
				"",
				fileUtil);
		
		ViewerType type = viewerManager.getViewerType();
		FormatCollection trajFormatCollection = getFileFormatCollection(type);
		
		trajFormatField = p.addChoosableParameter(
				"Trajectory format",
				trajFormatCollection.getOptions().toArray(new FileFormat[0]),
				null);
				
		trajFileButton.addFileChosenListener(
				new UpdateFileFormatListener(trajFileButton,trajFormatField,trajFormatCollection));
		
		target.add(p);
	}
	
	protected FormatCollection getFileFormatCollection(ViewerType type){
		return type.getTrajectoryFormatCollection();
	}

	@Override
	public StructureLoader getModelLoader() {	
		Path trajPath = trajFileButton.getMaybeFile()
				.orElseThrow(() -> new RuntimeException("Trajectory file not found"))
				.toPath();
		StructureLoader singleStructureLoader = singleStructurePanel.getModelLoader();
				
		FileFormat trajFormat = getSelectedFormat().orElseThrow(
				() -> new RuntimeException("No trajectory format selected"));
						
		return new TrajectoryLoader(singleStructureLoader, trajPath, trajFormat.getStandardName());
	}
	
	public Optional<FileFormat> getSelectedFormat(){
		return Optional.ofNullable((FileFormat)trajFormatField.getSelectedItem());
	}

	@Override
	public String getModelName() {
		return singleStructurePanel.getModelName();
	}
	
	

}
